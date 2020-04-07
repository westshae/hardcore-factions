package com.altoya.hardcorefactions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
                    if(hasFaction){
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

                            List<Object> claims = new ArrayList<>();
                            factionData.set("faction.claims", claims);

                            List<Object> onlinePlayers = new ArrayList<>();
                            onlinePlayers.add(playerName);
                            factionData.set("faction.onlinePlayers", onlinePlayers);

                            List<Object> offlinePlayers = new ArrayList<>();
                            factionData.set("faction.offlinePlayers", offlinePlayers);


                            factionData.set("faction.name", factionName);
                            factionData.set("faction.leader", playerName);
                            factionData.set("faction.balance", 0);
                            factionData.set("faction.raidable",false);

                            factionData.save(fileFaction);

                            //////////////////////////////

                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            String commandString = "lp user " + playerName + " permission set " + "factionName." + factionName + " true";
                            Bukkit.dispatchCommand(console, commandString);

                            //////////////////////////////

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
                    sender.sendMessage(ChatColor.GREEN + "The faction " + factionName + " has been created.");
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
                    FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                    List<String> players = factionData.getStringList("faction.players");

                    for(int i = 0; i < players.size(); i++){
                        String playerString = players.get(i);

                        File filePlayerTemp = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerString + ".yml");
                        FileConfiguration playerDataTemp = YamlConfiguration.loadConfiguration(filePlayerTemp);

                        playerDataTemp.set("faction.hasFaction", false);
                        playerDataTemp.set("faction.isLeader", false);
                        playerDataTemp.set("faction.factionName", null);

                        Player player = Bukkit.getPlayer(playerString);
                        player.sendMessage(ChatColor.GREEN + "Your faction has been disbanded by " + sender.getName() + ".");
                    }
                    try {
                        playerData.set("faction.hasFaction", false);
                        playerData.set("faction.isLeader", false);
                        playerData.set("faction.factionName", null);

                        playerData.save(filePlayer);

                        //////////////////////////////

                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String commandString = "lp user " + playerName + " permission unset " + "factionName." + factionName;
                        Bukkit.dispatchCommand(console, commandString);

                        //////////////////////////////
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    List<String> claims = factionData.getStringList("faction.claims");

                    File folder = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + "Claims");
                    File fileClaim = new File(folder, File.separator +  "claim.yml");
                    FileConfiguration claimData = YamlConfiguration.loadConfiguration(fileClaim);

                    for(int I = 0; I < claims.size(); I++){
                        try {
                            String chunkString = claims.get(I);
                            claimData.set(chunkString, null);

                            claimData.save(fileClaim);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    fileFaction.delete();
                }
            }
            if(args[0].equalsIgnoreCase("invite")){
                //Checks to see if they have included the name of the player
                try{
                    Object tryCatchTest = args[1];

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

                        sender.sendMessage(ChatColor.GREEN + "The player " + inviteeName + " has been invited to " + factionName + ".");
                        Player invitee = Bukkit.getServer().getPlayer(inviteeName);
                        invitee.sendMessage(ChatColor.GREEN + "You have been invited to " + factionName + ".");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(args[0].equalsIgnoreCase("join")){
                try{
                    Object tryCatchTest = args[1];

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

                        List<Object> invited = (List<Object>) factionData.getList("faction.invited");
                        invited.remove(playerName);

                        factionData.save(fileFaction);

                        File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                        FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

                        playerData.set("faction.hasFaction", true);
                        playerData.set("faction.factionName", factionName);

                        List<Object> onlinePlayers = (List<Object>) factionData.get("faction.onlinePlayers");
                        onlinePlayers.add(playerName);
                        factionData.set("faction.onlinePlayers", onlinePlayers);

                        factionData.save(fileFaction);

                        //////////////////////////////

                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String commandString = "lp user " + playerName + " permission set " + "factionName." + factionName + " true";
                        Bukkit.dispatchCommand(console, commandString);

                        //////////////////////////////
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage(ChatColor.GREEN + "You have joined the faction, " + factionName + ".");
                    List<String> players = factionData.getStringList("faction.players");
                    for(int i = 0; i < players.size(); i++){
                        String currentPlayerString = players.get(i);
                        Player currentPlayer = Bukkit.getServer().getPlayer(currentPlayerString);
                        if(currentPlayer.isOnline()){
                            currentPlayer.sendMessage(ChatColor.GREEN + "The player " + playerName + " has joined the faction.");
                        }
                    }
                }
            }
            if(args[0].equalsIgnoreCase("claim")){
                String playerName = sender.getName();
                Player player = (Player) sender;


                File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

                String factionName = (String) playerData.get("faction.factionName");

                File folder = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + "Claims");
                File fileClaim = new File(folder, File.separator +  "claim.yml");
                FileConfiguration claimData = YamlConfiguration.loadConfiguration(fileClaim);

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);


                boolean factionLeader = (boolean) playerData.get("faction.isLeader");


                if(factionData.getStringList("faction.players").contains(playerName)){
                    if(factionLeader == true){


                        Chunk playerChunk = player.getLocation().getChunk();

                        String chunkString = playerChunk.toString();

                        if(claimData.contains(chunkString)){

                            sender.sendMessage(ChatColor.RED + "This chunk is already claimed.");
                            return false;
                        }
                        if(!claimData.contains(chunkString)){
                            try {
                                claimData.createSection(chunkString + ".claimOwner");

                                String factionFName = (String) factionData.get("faction.name");
                                claimData.set(chunkString + ".claimOwner", factionFName);

                                claimData.save(fileClaim);

                                List<Object> claims = (List<Object>) factionData.getList("faction.claims");
                                claims.add(chunkString);

                                factionData.save(fileFaction);

                                sender.sendMessage(ChatColor.GREEN + "This chunk has been claimed by " + factionName + ".");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
            if(args[0].equalsIgnoreCase("unclaim")){
                String playerName = sender.getName();
                Player player = (Player) sender;

                File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

                String factionName = (String) playerData.get("faction.factionName");

                File folder = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + "Claims");
                File fileClaim = new File(folder, File.separator +  "claim.yml");
                FileConfiguration claimData = YamlConfiguration.loadConfiguration(fileClaim);

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);


                boolean factionLeader = (boolean) playerData.get("faction.isLeader");

                if(factionData.getStringList("faction.players").contains(playerName)) {
                    if (factionLeader == true) {
                        Chunk playerChunk = player.getLocation().getChunk();
                        String chunkString = playerChunk.toString();
                        String claimOwner = (String) claimData.get(chunkString + ".claimOwner");

                        if (!claimData.contains(chunkString)) {
                            sender.sendMessage(ChatColor.RED + "This chunk isn't claimed.");
                            return false;
                        }
                        if(claimOwner.equals(factionName)){
                            try {
                                claimData.set(chunkString, null);

                                claimData.save(fileClaim);

                                sender.sendMessage(ChatColor.GREEN + "This chunk has been unclaimed.");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            if(args[0].equalsIgnoreCase("uninvite")){
                String playerName = sender.getName();
                Player player = (Player) sender;
                String invitee = args[1].toString();


                File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

                String factionName = (String) playerData.get("faction.factionName");

                File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

                List<Object> invited = (List<Object>) factionData.getList("faction.invited");
                invited.remove(invitee);

                try {
                    factionData.set("faction.invited", invited);

                    factionData.save(fileFaction);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(args[0].equalsIgnoreCase("leave")){
                String playerName = sender.getName();
                File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);

                boolean hasFaction = (boolean) playerData.get("faction.hasFaction");
                boolean isLeader = (boolean) playerData.get("faction.isLeader");
                if(!isLeader) {
                    if (hasFaction == true) {

                        String factionName = (String) playerData.get("faction.factionName");

                        File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
                        FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);
                        try {

                            List<String> players = factionData.getStringList("faction.players");
                            players.remove(playerName);
                            factionData.set("faction.players", players);

                            factionData.save(fileFaction);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            playerData.set("faction.hasFaction", false);
                            playerData.set("faction.isLeader", false);
                            playerData.set("faction.factionName", null);

                            playerData.save(filePlayer);

                            //////////////////////////////

                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            String commandString = "lp user " + playerName + " permission unset " + "factionName." + factionName;
                            Bukkit.dispatchCommand(console, commandString);

                            //////////////////////////////

                            sender.sendMessage(ChatColor.GREEN + sender.getName() + " has left the faction.");

                            List<String> players = factionData.getStringList("faction.players");

                            for (int i = 0; i < players.size(); i++) {
                                String playerNameTemp = players.get(i);
                                Player player = Bukkit.getServer().getPlayer(playerNameTemp);
                                player.sendMessage(ChatColor.GREEN + "The player " + playerNameTemp + " has left the faction.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if(isLeader){
                    sender.sendMessage(ChatColor.RED + "Faction leaders cannot leave their own faction, they must disband.");
                }
            }
            if(args[0].equalsIgnoreCase("chat")){
                Player player = (Player) sender;
                String playerName = player.getName();

                File filePlayer = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerData").getDataFolder(), File.separator + playerName + ".yml");
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(filePlayer);
                boolean argsEmpty = false;
                try{
                    args[1].toString();
                }catch(Exception e){
                    argsEmpty = true;
                }
                if((boolean)playerData.get("faction.hasFaction") == true) {
                    if (argsEmpty == true) {
                        if ((boolean) playerData.get("faction.chat") == true) {
                            try {
                                playerData.set("faction.chat", false);

                                playerData.save(filePlayer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if ((boolean) playerData.get("faction.chat") == false) {
                            try {
                                playerData.set("faction.chat", true);

                                playerData.save(filePlayer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (argsEmpty == false) {
                        if (args[1].equalsIgnoreCase("on")) {
                            try {
                                playerData.set("faction.chat", true);

                                playerData.save(filePlayer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (args[1].equalsIgnoreCase("off")) {
                            try {
                                playerData.set("faction.chat", false);

                                playerData.save(filePlayer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
