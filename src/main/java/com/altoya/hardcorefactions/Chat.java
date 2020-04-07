package com.altoya.hardcorefactions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        String playerName = event.getPlayer().getName();
        Player player = event.getPlayer();

        File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

        boolean hasFaction = (boolean) playerData.get("faction.hasFaction");

        if(hasFaction == true){
            try {
                String factionName = (String) playerData.get("faction.factionName");

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                List<String> onlinePlayers =  factionData.getStringList("faction.onlinePlayers");
                onlinePlayers.add(playerName);
                factionData.set("faction.onlinePlayers", onlinePlayers);

                List<String> offlinePlayers = factionData.getStringList("faction.offlinePlayers");
                offlinePlayers.remove(playerName);
                factionData.set("faction.offlinePlayers", offlinePlayers);

                factionData.save(fileFaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event){
        String playerName = event.getPlayer().getName();

        File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

        boolean hasFaction = (boolean) playerData.get("faction.hasFaction");

        if(hasFaction == true){
            try {
                String factionName = (String) playerData.get("faction.factionName");

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                List<String> onlinePlayers = factionData.getStringList("faction.onlinePlayers");
                onlinePlayers.remove(playerName);
                factionData.set("faction.onlinePlayers", onlinePlayers);

                List<String> offlinePlayers = factionData.getStringList("faction.offlinePlayers");
                offlinePlayers.add(playerName);
                factionData.set("faction.offlinePlayers", offlinePlayers);

                factionData.save(fileFaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @EventHandler
    public void playerLeaveEvent(PlayerKickEvent event){
        String playerName = event.getPlayer().getName();

        File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

        boolean hasFaction = (boolean) playerData.get("faction.hasFaction");

        if(hasFaction == true){
            try {
                String factionName = (String) playerData.get("faction.factionName");

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                List<String> onlinePlayers = (List<String>) factionData.get("faction.onlinePlayers");
                onlinePlayers.remove(playerName);
                factionData.set("faction.onlinePlayers", onlinePlayers);

                List<String> offlinePlayers = (List<String>) factionData.get("faction.offlinePlayers");
                offlinePlayers.add(playerName);
                factionData.set("faction.offlinePlayers", offlinePlayers);

                factionData.save(fileFaction);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent event){
        String playerName = event.getPlayer().getName();
        Player player = Bukkit.getPlayer(playerName);

        File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

        boolean hasFaction = (boolean) playerData.get("faction.hasFaction");
        boolean factionChatEnabled = (boolean) playerData.get("faction.chat");
        if(hasFaction == true){
            if(factionChatEnabled == true) {
                String factionName = (String) playerData.get("faction.factionName");

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                List<String> onlinePlayers = (List<String>) factionData.get("faction.onlinePlayers");
                List<String> offlinePlayers = (List<String>) factionData.get("faction.offlinePlayers");
                System.out.println(onlinePlayers);

                for (int i = 0; i < onlinePlayers.size(); i++) {
                    String currentPlayerString = onlinePlayers.get(i);
                    System.out.println(currentPlayerString);
                    Player currentPlayer = Bukkit.getServer().getPlayer(currentPlayerString);

                    String message = event.getMessage();
                    currentPlayer.sendMessage(ChatColor.GREEN + player.getDisplayName() + ":" + message);

                    event.setCancelled(true);
                }
            }
        }
    }
}
