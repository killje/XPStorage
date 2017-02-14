package me.killje.xpstorage.eventListeners;

import java.util.Arrays;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 *
 * @author patrick
 */
public class OnBlockExplode extends OnBlockDestory{
    
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(!isDestroyable(event.blockList()));
    }
    
    
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(!isDestroyable(event.blockList()));
    }
}
