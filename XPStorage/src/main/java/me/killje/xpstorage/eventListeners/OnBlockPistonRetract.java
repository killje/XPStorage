package me.killje.xpstorage.eventListeners;

import java.util.Arrays;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonRetractEvent;

/**
 *
 * @author patrick
 */
public class OnBlockPistonRetract extends OnBlockDestory{
    
    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (!isDestroyable(event.getBlock())) {
            event.setCancelled(true);
        }
        else if (!isDestroyable(event.getBlocks())) {
            event.setCancelled(true);
        }
    }
}
