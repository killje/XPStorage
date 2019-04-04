package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Listener for block breaking events
 *
 * @author Patrick Beuks (killje) <code@beuks.net>
 */
public class OnBlockBreak extends OnBlockDestory {

    /**
     * This is called when a block breaks in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to the block breaking
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(
                event.isCancelled() 
                || !isDestroyable(event.getBlock(), event.getPlayer())
        );
    }
}
