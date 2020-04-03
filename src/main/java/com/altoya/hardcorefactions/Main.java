package com.altoya.hardcorefactions;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("f").setExecutor(new Factions());

        if(!this.getDataFolder().exists()){
            try{
                this.getDataFolder().mkdir();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
