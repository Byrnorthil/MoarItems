package com.github.byrnorthil.moaritems.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

import static com.github.byrnorthil.moaritems.MoarItems.*;

public class MoarItemsListener implements Listener {
    @EventHandler
    public void onFireWorkExplode(FireworkExplodeEvent event) {
        Firework firework = event.getEntity();
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        if (Objects.equals(fireworkMeta.displayName(), Component.text(SONIC_CHARGE_NAME))
            && Objects.equals(fireworkMeta.lore(), List.of(Component.text(SONIC_CHARGE_DESC_1), Component.text(SONIC_CHARGE_DESC_2)))
            && fireworkMeta.getPower() == 2) {
            for (Entity entity : firework.getNearbyEntities(96, 144, 96)) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 160, 1));
                }
            }

            for (Entity entity : firework.getNearbyEntities(16, 64, 16)) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 1));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            && !player.hasCooldown(Material.CLOCK)
            && item.getType() == Material.CLOCK
            && Objects.equals(item.getItemMeta().displayName(), Component.text(RADAR_NAME))
            && Objects.equals(item.getItemMeta().lore(), List.of(Component.text(RADAR_DESC)))) {
                for (Entity entity: player.getNearbyEntities(48, 48, 48)) {
                    if (entity != player && entity instanceof LivingEntity) {
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
                                20, 1, false, false, true));
                    }
                }
                player.setCooldown(Material.CLOCK, 80);
        }
    }
}
