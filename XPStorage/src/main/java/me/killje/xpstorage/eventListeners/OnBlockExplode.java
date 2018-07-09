package me.killje.xpstorage.eventListeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockExplode extends OnBlockDestory {

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(!isDestroyable(event.blockList(), null));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            event.setCancelled(!isDestroyable(event.blockList(), (Player) event.getEntity()));
        } else {
            event.setCancelled(!isDestroyable(event.blockList(), null));
        }
    }
}
