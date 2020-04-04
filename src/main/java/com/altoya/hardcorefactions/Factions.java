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

import javax.print.DocFlavor;
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
                    //Initialize both variables
                    String factionName = args[1];
                    String playerName = sender.getName();

                    //Creates or loads the faction's
                    File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                    FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                    //Loads the player's data .yml file
                    File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                    FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);


                    boolean hasFaction = (boolean) playerData.get("faction.hasFaction");
                    if(hasFaction == true){
                        sender.sendMessage(ChatColor.RED + "You already have a faction. Disband or leave the faction to create a new one.");
                        return false;
                    }

                    //Checks if the user already has a faction
                    if(fileFaction.exists()){
                        sender.sendMessage(ChatColor.RED + "A faction with this name already exists. Please choose another name.");
                        return false;
                    }

                    //If the faction does not exist and the player is not in a faction, a new faction.yml file will be made.
                    if(!fileFaction.exists()){
                        try{
                            //Creates a new section in the .yml file and adds all of the following information, then saves the information.
                            factionData.createSection("faction");

                            List<Object> players = new ArrayList<>();
                            players.add(playerName);
                            factionData.set("faction.players", players);

                            List<Object> invited = new ArrayList<>();
                            factionData.set("faction.invited", invited);

                            factionData.set("faction.name", factionName);
                            factionData.set("faction.leader", playerName);
                            factionData.set("faction.balance", 0);
                            factionData.set("faction.raidable",false);

                            factionData.save(fileFaction);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        //Updates the sender's data .yml file with the following information.
                        playerData.set("faction.hasFaction", true);
                        playerData.set("faction.factionName", factionName);
                        playerData.set("faction.isLeader", true);

                        playerData.save(filePlayer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(args[0].equalsIgnoreCase("disband")){
                String playerName = sender.getName();
                File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

                String factionName = (String) playerData.get("faction.factionName");
                boolean factionLeader = (boolean) playerData.get("faction.isLeader");
                if(factionLeader == true){
                    File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                    fileFaction.delete();
                }
            }
            if(args[0].equalsIgnoreCase("invite")){
                //Checks to see if they have included the name of the player
                try{
                    Object tryCatchTest = args[1];
                    System.out.println(tryCatchTest);

                }catch(Exception e){
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.RED + "Please use /f invite <name>");
                    return false;
                }

                String playerName = sender.getName();
                String inviteeName = args[1];

                File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

                String factionName = (String) playerData.get("faction.factionName");

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                boolean isLeader = (boolean) playerData.get("faction.isLeader");
                if(isLeader == true){
                    try {
                        List<Object> invited = (List<Object>) factionData.getList("faction.invited");
                        invited.add(inviteeName);

                        factionData.save(fileFaction);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(args[0].equalsIgnoreCase("join")){
                try{
                    Object tryCatchTest = args[1];
                    System.out.println(tryCatchTest);

                }catch(Exception e){
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.RED + "Please use /f join <factionname>");
                    return false;
                }

                String playerName = sender.getName();
                String factionName = args[1];

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                List<Object> invitedPlayers = (List<Object>) factionData.get("faction.invited");
                if(invitedPlayers.contains(playerName)){
                    try {
                        List<Object> players = (List<Object>) factionData.get("faction.players");
                        players.add(playerName);

                        factionData.save(fileFaction);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
