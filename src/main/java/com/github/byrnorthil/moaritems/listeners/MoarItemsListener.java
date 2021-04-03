package com.github.byrnorthil.moaritems.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static com.github.byrnorthil.moaritems.MoarItems.*;

public class MoarItemsListener implements Listener {
    @EventHandler
    public void onFireWorkExplode(FireworkExplodeEvent event) {
        Firework firework = event.getEntity();
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        if (fireworkMeta.displayName().equals(Component.text(SONIC_CHARGE_NAME))
            && fireworkMeta.lore().equals(List.of(Component.text(SONIC_CHARGE_DESC_1), Component.text(SONIC_CHARGE_DESC_2)))
            && fireworkMeta.getPower() == 2) {
            for (Entity entity : firework.getNearbyEntities(96, 144, 96)) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 160, 1));
                }
            }

            for (Entity entity : firework.getNearbyEntities(16, 24, 16)) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 1));
                }
            }
        }
    }
}
