package com.github.byrnorthil.moaritems.commands;

import com.github.byrnorthil.moaritems.MoarItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

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
                    ((InventoryHolder) commandSender).getInventory().addItem(makeSonicCharge());
                    return true;
                } else {
                    commandSender.sendMessage(INVALID_SENDER);
                }
                break;
            case "giveradar":
                if (commandSender instanceof InventoryHolder) {
                    ((InventoryHolder) commandSender).getInventory().addItem(makeRadar());
                    return true;
                } else {
                    commandSender.sendMessage(INVALID_SENDER);
                }
                break;
        }
        return false;
    }
}
