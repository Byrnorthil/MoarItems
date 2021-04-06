package com.github.byrnorthil.moaritems.commands;

import com.github.byrnorthil.moaritems.MoarItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static com.github.byrnorthil.moaritems.MoarItems.*;

public class MoarItemsCommandExecutor implements CommandExecutor {

    public static final String INVALID_TARGET = "Player does not exist; check to see if they are logged on.";

    private final MoarItems plugin;

    public MoarItemsCommandExecutor(MoarItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        switch (command.getName()) {
            case "giveglitterbomb":
                if (strings.length > 1) {
                    return false;
                }
                Player targetS = strings.length == 1 ? Bukkit.getPlayer(strings[0]) : (Player)commandSender;
                if (targetS != null) targetS.getInventory().addItem(makeGlitterBomb());
                else commandSender.sendMessage(INVALID_TARGET);
                return true;
            case "giveradar":
                if (strings.length > 1) {
                    return false;
                }
                Player targetr = strings.length == 1 ? Bukkit.getPlayer(strings[0]) : (Player)commandSender;
                if (targetr != null) targetr.getInventory().addItem(makeRadar());
                else commandSender.sendMessage(INVALID_TARGET);
                return true;
        }
        return false;
    }
}
