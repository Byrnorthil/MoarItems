package com.github.byrnorthil.moaritems;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.MaterialTags;
import com.github.byrnorthil.moaritems.commands.MoarItemsCommandExecutor;
import com.github.byrnorthil.moaritems.listeners.MoarItemsListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public final class MoarItems extends JavaPlugin {

    //Constants
    public static final String SONIC_CHARGE_NAME = "Sonic Charge";
    public static final String SONIC_CHARGE_DESC_1 = "Set off to give nearby players";
    public static final String SONIC_CHARGE_DESC_2 = "echolocation for a short time";

    public static final String RADAR_NAME = "Radar";
    public static final String RADAR_DESC = "Sneak to reveal nearby entities";

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new MoarItemsListener(), this);
        MoarItemsCommandExecutor commandExecutor = new MoarItemsCommandExecutor(this);
        getCommand("givesoniccharge").setExecutor(commandExecutor);
        getCommand("giveradar").setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static ItemStack makeSonicCharge() {
        ItemStack sonicCharge = new ItemStack(Material.FIREWORK_ROCKET);
        FireworkMeta chargeMeta = (FireworkMeta) sonicCharge.getItemMeta();
        chargeMeta.displayName(Component.text(SONIC_CHARGE_NAME));
        chargeMeta.lore(List.of(Component.text(SONIC_CHARGE_DESC_1), Component.text(SONIC_CHARGE_DESC_2)));
        chargeMeta.setPower(2);
        sonicCharge.setItemMeta(chargeMeta);
        return sonicCharge;
    }

    public static ItemStack makeRadar() {
        ItemStack radar = new ItemStack(Material.CLOCK);
        ItemMeta meta = radar.getItemMeta();
        meta.displayName(Component.text(RADAR_NAME));
        meta.lore(List.of(Component.text(RADAR_DESC)));
        radar.setItemMeta(meta);
        return radar;
    }

    public static boolean metaMatch(@Nullable ItemMeta meta1, @Nullable ItemMeta meta2) {
        if (meta1 == null && meta2 == null) return true;
        if ((meta1 == null) != (meta2 == null)) return false; //XOR
        return Objects.equals(meta1.lore(), meta2.lore()); //Match item metas by lore only, to be more adjustable
    }

    public static boolean isUsableItem(@Nullable ItemStack item) { //Doesn't include blocks
        if (item == null) return false;
        return item.getType().isEdible()
                || MaterialTags.BOWS.isTagged(item)
                || MaterialTags.THROWABLE_PROJECTILES.isTagged(item)
                || item.getType() == Material.FISHING_ROD
                || item.getType() == Material.SHIELD;
    }
}
