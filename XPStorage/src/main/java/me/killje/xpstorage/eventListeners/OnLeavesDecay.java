package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 *
 * @author patrick
 */
public class OnLeavesDecay extends OnBlockDestory{
    
    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!isDestroyable(event.getBlock())) {
            event.setCancelled(true);
            
            byte d = event.getBlock().getData();
            event.getBlock().setData((byte) (d%4));
        }
    }
}
