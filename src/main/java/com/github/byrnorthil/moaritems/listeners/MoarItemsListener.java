package com.github.byrnorthil.moaritems.listeners;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.github.byrnorthil.moaritems.MoarItems.*;

public class MoarItemsListener implements Listener {
    @EventHandler
    public void onFireWorkExplode(FireworkExplodeEvent event) {
        ItemStack sonicChargeTemplate = makeGlitterBomb();
        Firework firework = event.getEntity();
        if (metaMatch(firework.getFireworkMeta(), sonicChargeTemplate.getItemMeta())) {
            //nearby entities
            firework.getNearbyEntities(96, 96, 96).stream()
                    .filter(entity -> entity instanceof LivingEntity
                            && ((LivingEntity) entity).hasLineOfSight(firework))
                    .forEach(entity -> {
                ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 160, 0));
                //have to be standing underneath the firework to get nighvision
                if (firework.getNearbyEntities(16, 64, 16).contains(entity)) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 0));
                    if (firework.getNearbyEntities(4, 4, 4).contains(entity)) {
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 0));
                    }

                }
                if (entity instanceof Player) { //additional glitter spray effect
                    ((Player) entity).spawnParticle(Particle.FIREWORKS_SPARK, firework.getLocation(), 300);
                }
            });
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        ItemStack radarTemplate = makeRadar();
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK
                    && event.hasBlock()
                    && event.getClickedBlock().getType().isInteractable())
                || player.hasCooldown(radarTemplate.getType())
                || player.isHandRaised()) return;

        //If the item isn't a radar, we don't need to do anything else
        ItemStack item = event.getItem();
        if (item == null || !metaMatch(radarTemplate.getItemMeta(), item.getItemMeta())) return;

        if (event.getHand() == EquipmentSlot.HAND) {
            if (isUsableItem(player.getItemInOffHand())) return;
            player.swingMainHand();
        } else {
            player.swingOffHand();
        }

        //TODO: Find some way to override off-hand item if radar is used in main hand
        event.setUseItemInHand(Event.Result.ALLOW);

        //Give glowing and play sound
        player.getNearbyEntities(48, 48, 48).stream()
                .filter(entity -> entity instanceof LivingEntity).forEach(entity -> {
            ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
                    20, 0, false, false, true));
            if (entity instanceof Player) {
                ((Player) entity).playSound(player.getLocation(), "block.enchantment_table.use", SoundCategory.PLAYERS, 1f, 1.5f);
            }
        });
        player.setCooldown(radarTemplate.getType(), 80);
        //We need an extra call to playSound because getNearbyEntities doesn't include the calling player
        player.playSound(player.getLocation(), "block.enchantment_table.use", SoundCategory.PLAYERS, 1f, 1.5f);
        player.spawnParticle(Particle.SPELL_INSTANT, player.getLocation(), 25);
    }
    
    private boolean isUsableItem(ItemStack item) {
        return item.getType().isEdible()
                || MaterialTags.BOWS.isTagged(item)
                || MaterialTags.THROWABLE_PROJECTILES.isTagged(item)
                || item.getType() == Material.SHIELD
                || item.getType() == Material.FISHING_ROD;
    }
}
