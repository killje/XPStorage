package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonRetractEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockPistonRetract extends OnBlockDestory{
    
    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (!isDestroyable(event.getBlock(), null)) {
            event.setCancelled(true);
        }
        else if (!isDestroyable(event.getBlocks(), null)) {
            event.setCancelled(true);
        }
    }
}
