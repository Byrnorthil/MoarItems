package com.github.byrnorthil.moaritems;

import com.github.byrnorthil.moaritems.commands.MoarItemsCommandExecutor;
import com.github.byrnorthil.moaritems.listeners.MoarItemsListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoarItems extends JavaPlugin {

    //Constants
    public static final String SONIC_CHARGE_NAME = "Sonic Charge";
    public static final String SONIC_CHARGE_DESC_1 = "Set off to give nearby players";
    public static final String SONIC_CHARGE_DESC_2 = "echolocation for a short time";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MoarItemsListener(), this);
        getCommand("givesoniccharge").setExecutor(new MoarItemsCommandExecutor(this));
    }

    @Override
    public void onDisable() {

    }
}
