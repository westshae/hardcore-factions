package com.altoya.hardcorefactions;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("f").setExecutor(new Factions());
        this.getServer().getPluginManager().registerEvents(new ChunkData(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
