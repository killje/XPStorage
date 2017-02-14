package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnEntityChangeBlock extends OnBlockDestory {
    
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!isDestroyable(event.getBlock())) {
            event.setCancelled(true);
            event.getBlock().getState().update();
        }
        
    }
    
}
