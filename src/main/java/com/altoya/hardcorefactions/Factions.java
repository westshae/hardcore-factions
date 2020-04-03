package com.altoya.hardcorefactions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Factions implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(command.getName().equalsIgnoreCase("f")){
            if(args[0].equalsIgnoreCase("create")){
                if(args[1].isEmpty()){
                    return false;
                }
                if(!args[1].isEmpty()){
                    String factionName = args[1];
                    String playerName = sender.getName();
                    String ymlFileName = (factionName + ".yml");
                    File factionData = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + ymlFileName);
                    File file = new File(factionData, File.separator + factionName + ".yml");
                    FileConfiguration groupData = YamlConfiguration.loadConfiguration(file);

                    if(!file.exists()){
                        try{
                            groupData.createSection("faction");

                            List<Object> players = new ArrayList<>();
                            players.add(playerName);
                            groupData.set("faction.players", players);

                            groupData.set("faction.name", factionName);
                            groupData.set("faction.leader", playerName);
                            groupData.set("faction.balance", 0);
                            groupData.set("faction.raidable",false);
                            groupData.set("faction.allies", null);
                            groupData.set("faction.onlinePlayers", null);
                            groupData.set("faction.offlinePlayers", null);

                            groupData.save(file);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder() , File.separator + "PlayerDatabase");
                    File tempFile = new File(userdata, File.separator + playerName + ".yml");
                    FileConfiguration playerData = YamlConfiguration.loadConfiguration(tempFile);

                    playerData.set("faction.hasFaction", true);
                    playerData.set("faction.factionName", factionName);
                    playerData.set("faction.isLeader", true);
                    try {
                        playerData.save(tempFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(args[0].equalsIgnoreCase("claim")){
                Player player = (Player) sender;
                Chunk playerChunk = player.getLocation().getChunk();
                String playerChunkString = playerChunk.toString();
                sender.sendMessage(ChatColor.BLUE + playerChunkString);
                System.out.println(playerChunkString);

            }
            if(args[0].equalsIgnoreCase("disband")){
                String playerName = sender.getName();
                File userdata = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder() , File.separator + "PlayerDatabase");
                File tempFile = new File(userdata, File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(tempFile);

                String factionName = (String) playerData.get("faction.factionName");
                boolean factionLeader = (boolean) playerData.get("faction.isLeader");
                if(factionLeader == true){
                    String ymlFileName = factionName + ".yml";
                    File factionData = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + ymlFileName);
                    File file = new File(factionData, File.separator + factionName + ".yml");
                    FileConfiguration groupData = YamlConfiguration.loadConfiguration(file);
                    file.delete();
                }
            }
        }
        return true;
    }

}
