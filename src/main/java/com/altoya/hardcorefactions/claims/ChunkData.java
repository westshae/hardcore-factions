package com.altoya.hardcorefactions.claims;

import com.altoya.hardcorefactions.Factions;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkData implements Listener {
    @EventHandler
    public void chunkUpdate(ChunkLoadEvent event){
        Factions claimcommand = new Factions();
        Chunk chunk = event.getChunk();

    }
}
