package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBurnEvent;

/**
 * Listener to block burning events
 *
 * @author Patrick Beuks (killje) <code@beuks.net>
 */
public class OnBlockBurn extends OnBlockDestory {

    /**
     * This is called when a block burns in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to the block burning
     */
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(event.isCancelled() 
                || !isDestroyable(event.getBlock(), null));
    }
}
