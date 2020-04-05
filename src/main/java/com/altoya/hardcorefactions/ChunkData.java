package com.altoya.hardcorefactions;

import com.altoya.hardcorefactions.Factions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.io.File;
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
}
