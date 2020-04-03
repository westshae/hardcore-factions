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
                            groupData.createSection("name");
                            groupData.set("name", factionName);

                            groupData.createSection("players");
                            groupData.set("players", playerName);

                            groupData.createSection("leader");
                            groupData.set("leader", playerName);

                            groupData.createSection("balance");
                            groupData.set("balance", 0);

                            groupData.createSection("raidable");
                            groupData.set("raidable",false);

                            groupData.createSection("allies");
                            groupData.set("allies", null);

                            groupData.createSection("onlinePlayers");
                            groupData.set("onlinePlayers", null);

                            groupData.createSection("offlinePlayers");
                            groupData.set("offlinePlayers", null);

                            groupData.save(file);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
        }
        return true;
    }

}
