package com.github.byrnorthil.moaritems;

import com.github.byrnorthil.moaritems.commands.MoarItemsCommandExecutor;
import com.github.byrnorthil.moaritems.listeners.MoarItemsListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public final class MoarItems extends JavaPlugin {

    //Constants
    public static final String GLITTER_BOMB_NAME = "Glitter Bomb";
    public static final List<Component> GLITTER_BOMB_DESC = List.of(
            Component.text("Illuminates everything within a large area"));

    public static final String RADAR_NAME = "Sonar";
    public static final List<Component> RADAR_DESC = List.of(
            Component.text("Right-click to reveal nearby entities"),
            Component.text("Consumes prismarine crystals"));

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new MoarItemsListener(), this);
        MoarItemsCommandExecutor commandExecutor = new MoarItemsCommandExecutor(this);
        getCommand("giveglitterbomb").setExecutor(commandExecutor);
        getCommand("givesonar").setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static ItemStack makeGlitterBomb() {
        ItemStack glitterBomb = new ItemStack(Material.FIREWORK_ROCKET);
        FireworkMeta chargeMeta = (FireworkMeta) glitterBomb.getItemMeta();
        chargeMeta.displayName(Component.text(GLITTER_BOMB_NAME));
        chargeMeta.lore(GLITTER_BOMB_DESC);
        chargeMeta.addEffects(FireworkEffect.builder().with(FireworkEffect.Type.STAR).withColor(DyeColor.WHITE.getFireworkColor()).withFlicker().build(),
                FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(DyeColor.GRAY.getFireworkColor()).withTrail().build());
        chargeMeta.setPower(2);
        glitterBomb.setItemMeta(chargeMeta);
        return glitterBomb;
    }

    public static ItemStack makeRadar() {
        ItemStack radar = new ItemStack(Material.CLOCK);
        ItemMeta meta = radar.getItemMeta();
        meta.displayName(Component.text(RADAR_NAME));
        meta.lore(RADAR_DESC);
        radar.setItemMeta(meta);
        return radar;
    }

    public static boolean metaMatch(@Nullable ItemMeta meta1, @Nullable ItemMeta meta2) {
        if (meta1 == null && meta2 == null) return true;
        if ((meta1 == null) != (meta2 == null)) return false; //XOR
        return Objects.equals(meta1.lore(), meta2.lore()); //Match item metas by lore only, to be more adjustable
    }
}
