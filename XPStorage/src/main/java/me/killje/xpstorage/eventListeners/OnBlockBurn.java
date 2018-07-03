package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBurnEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockBurn extends OnBlockDestory{
    
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(!isDestroyable(event.getBlock(), null));
    }
}
