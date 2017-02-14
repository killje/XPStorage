package me.killje.xpstorage.eventListeners;

import java.util.Arrays;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;

/**
 *
 * @author patrick
 */
public class OnBlockPistonExtend extends OnBlockDestory{
    
    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!isDestroyable(event.getBlock())) {
            event.setCancelled(true);
        }
        else if (!isDestroyable(event.getBlocks())) {
            event.setCancelled(true);
        }
    }
}
