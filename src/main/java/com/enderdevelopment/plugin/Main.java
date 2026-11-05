package com.enderdevelopment.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        DashMaceCommand command = new DashMaceCommand();
        if (getCommand("dashmace") != null) {
            getCommand("dashmace").setExecutor(command);
        }

        getServer().getPluginManager().registerEvents(new DashMaceListener(), this);

        getLogger().info("DashMace plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DashMace plugin disabled!");
        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }
}
