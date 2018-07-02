package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockPistonExtend extends OnBlockDestory{
    
    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!isDestroyable(event.getBlock(), null)) {
            event.setCancelled(true);
        }
        else if (!isDestroyable(event.getBlocks(), null)) {
            event.setCancelled(true);
        }
    }
}
