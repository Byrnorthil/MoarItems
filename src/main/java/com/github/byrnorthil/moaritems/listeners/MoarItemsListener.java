package com.github.byrnorthil.moaritems.listeners;

import org.bukkit.Material;
import org.bukkit.Particle;
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
        ItemStack sonicChargeTemplate = makeSonicCharge();
        Firework firework = event.getEntity();
        if (metaMatch(firework.getFireworkMeta(), sonicChargeTemplate.getItemMeta())) {
            //give things glowing and play sounds for players
            firework.getNearbyEntities(96, 144, 96).stream()
                    .filter(entity -> entity instanceof LivingEntity)
                    .forEach(entity -> {
                ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 160, 1));
                if (entity instanceof Player) {
                    ((Player) entity).playSound(firework.getLocation(), "block.beacon.activate", 6f, 0.8f);
                }
            });

            //Give things night-vision
            firework.getNearbyEntities(16, 64, 16).stream()
                    .filter(entity -> entity instanceof LivingEntity)
                    .forEach(entity -> ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 1)));
            //TODO: Add particles
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        ItemStack radarTemplate = makeRadar();
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR
                || (event.getAction() == Action.RIGHT_CLICK_BLOCK
                    && event.getClickedBlock() != null
                    && event.getClickedBlock().getType().isInteractable()))
                || player.hasCooldown(radarTemplate.getType())
                || player.isHandRaised()) return;

        //If the item isn't a radar, we don't need to do anything else
        ItemStack item = event.getItem();
        if (item == null || !metaMatch(radarTemplate.getItemMeta(), item.getItemMeta())) return;

        //TODO: Find some way to override off-hand item if radar is used in main hand
        event.setUseItemInHand(Event.Result.ALLOW);

        //Give glowing and play sound
        player.getNearbyEntities(48, 48, 48).stream()
                .filter(entity -> entity instanceof LivingEntity).forEach(entity -> {
            ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
                    20, 1, false, false, true));
            if (entity instanceof Player) {
                ((Player) entity).playSound(player.getLocation(), "block.enchantment_table.use", 1f, 1.5f);
            }
        });
        player.setCooldown(radarTemplate.getType(), 80);
        //We need an extra call to playSound because getNearbyEntities doesn't include the calling player
        player.playSound(player.getLocation(), "block.enchantment_table.use", 1f, 1.5f);
        player.spawnParticle(Particle.SPELL_INSTANT, player.getLocation(), 25);
    }
}
