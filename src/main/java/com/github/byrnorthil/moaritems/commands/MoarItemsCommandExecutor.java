package com.github.byrnorthil.moaritems.commands;

import com.github.byrnorthil.moaritems.MoarItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

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
            case "giveglitterbomb":
                if (commandSender instanceof InventoryHolder) {
                    ((InventoryHolder) commandSender).getInventory().addItem(makeGlitterBomb());
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
