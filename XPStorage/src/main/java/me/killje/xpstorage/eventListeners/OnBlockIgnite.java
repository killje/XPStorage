package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockIgnite extends OnBlockDestory {
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        event.setCancelled(!isDestroyable(event.getBlock()));
    }
}
