package com.hinaplugin.licenseCraft;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class LicenseCraft extends JavaPlugin {
    public static LicenseCraft plugin;
    public static FileConfiguration config;
    public static NamespacedKey key;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.loadConfiguration();
        key = new NamespacedKey(this, "license_key");

        this.getServer().getPluginManager().registerEvents(new ItemPrepareCraftListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemCraftListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
    }

    private void loadConfiguration(){
        final File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()){
            this.saveDefaultConfig();
        }

        config = this.getConfig();
    }
}
