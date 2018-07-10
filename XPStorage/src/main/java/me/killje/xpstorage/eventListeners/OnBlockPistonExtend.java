package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;

/**
 * Listener for pistons extending events
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockPistonExtend extends OnBlockDestory {

    /**
     * This is called when a piston extends in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to the piston extends
     */
    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!isDestroyable(event.getBlock(), null)) {
            event.setCancelled(true);
        } else if (!isDestroyable(event.getBlocks(), null)) {
            event.setCancelled(true);
        }
    }
}
