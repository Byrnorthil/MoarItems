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
        if (command.getName().equalsIgnoreCase("givesoniccharge")) {
            commandSender.sendMessage("Made it here!");
            if (commandSender instanceof InventoryHolder) {
                ItemStack charge = new ItemStack(Material.FIREWORK_ROCKET);
                FireworkMeta chargeMeta = (FireworkMeta)charge.getItemMeta();
                chargeMeta.displayName(Component.text(SONIC_CHARGE_NAME));
                chargeMeta.lore(List.of(Component.text(SONIC_CHARGE_DESC_1), Component.text(SONIC_CHARGE_DESC_2)));
                chargeMeta.setPower(2);
                charge.setItemMeta(chargeMeta);
                commandSender.sendMessage("Here is the item!\n" + charge.toString());
                ((InventoryHolder) commandSender).getInventory().addItem(charge);
            } else {
                commandSender.sendMessage(INVALID_SENDER);
            }
            return true;
        }
        commandSender.sendMessage("COMMAND INCORRRECTT");
        return false;
    }
}
