package com.github.byrnorthil.moaritems.commands;

import com.github.byrnorthil.moaritems.MoarItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.awt.print.Paper;
import java.util.List;

import static com.github.byrnorthil.moaritems.MoarItems.*;

public class MoarItemsCommandExecutor implements CommandExecutor {

    public static final String INVALID_SENDER = "Must give to an inventory!";

    private final MoarItems plugin;

    public MoarItemsCommandExecutor(MoarItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        switch (command.getName()) {
            case "givesoniccharge":
                if (commandSender instanceof InventoryHolder) {
                    ItemStack charge = new ItemStack(Material.FIREWORK_ROCKET);
                    FireworkMeta chargeMeta = (FireworkMeta) charge.getItemMeta();
                    chargeMeta.displayName(Component.text(SONIC_CHARGE_NAME));
                    chargeMeta.lore(List.of(Component.text(SONIC_CHARGE_DESC_1), Component.text(SONIC_CHARGE_DESC_2)));
                    chargeMeta.setPower(2);
                    charge.setItemMeta(chargeMeta);
                    ((InventoryHolder) commandSender).getInventory().addItem(charge);
                    return true;
                } else {
                    commandSender.sendMessage(INVALID_SENDER);
                }
                break;
            case "giveradar":
                if (commandSender instanceof InventoryHolder) {
                    ItemStack radar = new ItemStack(Material.CLOCK);
                    ItemMeta meta = radar.getItemMeta();
                    meta.displayName(Component.text(RADAR_NAME));
                    meta.lore(List.of(Component.text(RADAR_DESC)));
                    radar.setItemMeta(meta);
                    ((InventoryHolder) commandSender).getInventory().addItem(radar);
                    return true;
                } else {
                    commandSender.sendMessage(INVALID_SENDER);
                }
                break;
        }
        return false;
    }
}
