package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBurnEvent;

/**
 *
 * @author Zolder
 */
public class OnBlockBurn extends OnBlockDestory{
    
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(destroyEvent(event));
    }
}
