package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;

/**
 * Listener for block ignite events
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockIgnite extends OnBlockDestory {

    /**
     * This is called when a block ignites in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to the block igniting
     */
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        event.setCancelled(!isDestroyable(event.getBlock(), event.getPlayer()));
    }
}
