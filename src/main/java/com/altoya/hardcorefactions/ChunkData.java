package com.altoya.hardcorefactions;

import com.altoya.hardcorefactions.Factions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChunkData implements Listener {
    @EventHandler
    public void playerBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        Chunk chunk = event.getBlock().getLocation().getChunk();
        String chunkString = chunk.toString();

        File folder = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + "Claims");
        File fileClaim = new File(folder, File.separator +  "claim.yml");
        FileConfiguration claimData = YamlConfiguration.loadConfiguration(fileClaim);

        String factionName = (String) claimData.get(chunkString + ".claimOwner");

        File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
        FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

        List<String> players = factionData.getStringList("faction.players");
        if(claimData.contains(chunkString)){
            if(players.contains(playerName)){
            }else{
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "This block is owned by another faction.");
            }
        }
    }
    @EventHandler
    public void playerPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        Chunk chunk = event.getBlock().getLocation().getChunk();
        String chunkString = chunk.toString();

        File folder = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + "Claims");
        File fileClaim = new File(folder, File.separator +  "claim.yml");
        FileConfiguration claimData = YamlConfiguration.loadConfiguration(fileClaim);

        String factionName = (String) claimData.get(chunkString + ".claimOwner");

        File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
        FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

        List<String> players = factionData.getStringList("faction.players");
        if(claimData.contains(chunkString)){
            if(players.contains(playerName)){
            }else{
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "This block is owned by another faction.");
            }
        }
    }

    @EventHandler
    public void InventoryOpenEvent(InventoryOpenEvent event){
        List<Object> protectedBlocks = new ArrayList<>();
        protectedBlocks.add("CHEST");
        protectedBlocks.add("TRAPPED_CHEST");
        protectedBlocks.add("FURNACE");
        protectedBlocks.add("JUKEBOX");
        protectedBlocks.add("ANVIL");
        protectedBlocks.add("SHULKER_BOX");
        protectedBlocks.add("WHITE_SHULKER_BOX");
        protectedBlocks.add("ORANGE_SHULKER_BOX");
        protectedBlocks.add("MAGENTA_SHULKER_BOX");
        protectedBlocks.add("LIGHT_BLUE_SHULKER_BOX");
        protectedBlocks.add("YELLOW_SHULKER_BOX");
        protectedBlocks.add("LIME_SHULKER_BOX");
        protectedBlocks.add("PINK_SHULKER_BOX");
        protectedBlocks.add("GRAY_SHULKER_BOX");
        protectedBlocks.add("LIGHT_GRAY_SHULKER_BOX");
        protectedBlocks.add("CYAN_SHULKER_BOX");
        protectedBlocks.add("PURPLE_SHULKER_BOX");
        protectedBlocks.add("BLUE_SHULKER_BOX");
        protectedBlocks.add("BROWN_SHULKER_BOX");
        protectedBlocks.add("GREEN_SHULKER_BOX");
        protectedBlocks.add("RED_SHULKER_BOX");
        protectedBlocks.add("BARREL");
        protectedBlocks.add("SMOKER");
        protectedBlocks.add("BLAST_FURNACE");
        protectedBlocks.add("BELL");
        protectedBlocks.add("HOPPER");
        protectedBlocks.add("DROPPER");
        protectedBlocks.add("LECTERN");
        protectedBlocks.add("BEACON");

        //ONLY PROTECTS INVENTORIES NOT DOORS ETC
        if(protectedBlocks.contains(event.getInventory().getType().toString())){
            Player player = (Player) event.getPlayer();
            String playerName = player.getName();
            Chunk chunk = event.getInventory().getLocation().getChunk();
            String chunkString = chunk.toString();

            File folder = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + "Claims");
            File fileClaim = new File(folder, File.separator +  "claim.yml");
            FileConfiguration claimData = YamlConfiguration.loadConfiguration(fileClaim);

            String factionName = (String) claimData.get(chunkString + ".claimOwner");

            File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
            FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

            List<String> players = factionData.getStringList("faction.players");
            if(claimData.contains(chunkString)){
                if(players.contains(playerName)){
                }else{
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "This block is owned by another faction.");
                }
            }
        }
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){
        System.out.println("T1");

        if(event.getClickedBlock() == null){
            System.out.println("T2");
            return;
        }
        System.out.println("T3");

        List<Object> protectedBlocks = new ArrayList<>();
        protectedBlocks.add("OAK_FENCE_GATE");
        protectedBlocks.add("SPRUCE_FENCE_GATE");
        protectedBlocks.add("BIRCH_FENCE_GATE");
        protectedBlocks.add("JUNGLE_FENCE_GATE");
        protectedBlocks.add("ACACIA_FENCE_GATE");
        protectedBlocks.add("DARK_OAK_FENCE_GATE");
        protectedBlocks.add("OAK_BUTTON");
        protectedBlocks.add("SPRUCE_BUTTON");
        protectedBlocks.add("BIRCH_BUTTON");
        protectedBlocks.add("JUNGLE_BUTTON");
        protectedBlocks.add("ACACIA_BUTTON");
        protectedBlocks.add("DARK_OAK_BUTTON");
        protectedBlocks.add("OAK_DOOR");
        protectedBlocks.add("SPRUCE_DOOR");
        protectedBlocks.add("BIRCH_DOOR");
        protectedBlocks.add("JUNGLE_DOOR");
        protectedBlocks.add("ACACIA_DOOR");
        protectedBlocks.add("DARK_OAK_DOOR");
        protectedBlocks.add("REPEATER");
        protectedBlocks.add("COMPARATOR");
        System.out.println("T4");

        if(protectedBlocks.contains(event.getClickedBlock().getType().toString())){
            Player player = event.getPlayer();
            String playerName = player.getName();
            Chunk chunk = event.getClickedBlock().getChunk();
            String chunkString = chunk.toString();

            File folder = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + "Claims");
            File fileClaim = new File(folder, File.separator +  "claim.yml");
            FileConfiguration claimData = YamlConfiguration.loadConfiguration(fileClaim);

            String factionName = (String) claimData.get(chunkString + ".claimOwner");

            File fileFaction = new File(Bukkit.getServer().getPluginManager().getPlugin("HardcoreFactions").getDataFolder(), File.separator + factionName + ".yml");
            FileConfiguration factionData = YamlConfiguration.loadConfiguration(fileFaction);

            List<String> players = factionData.getStringList("faction.players");
            if(claimData.contains(chunkString)){
                if(players.contains(playerName)){
                }else{
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "This block is owned by another faction.");
                }
            }
        }
    }
}
