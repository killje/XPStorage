package me.killje.xpstorage.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

/**
 *
 * @author Zolder
 */
public class OnBlockBreak extends OnBlockDestory{
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(destroyEvent(event));
    }
}
