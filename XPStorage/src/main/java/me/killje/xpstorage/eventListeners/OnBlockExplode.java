package me.killje.xpstorage.eventListeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Listener to block burning events
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockExplode extends OnBlockDestory {

    /**
     * This is called when a block explodes in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to the block exploding
     */
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(!isDestroyable(event.blockList(), null));
    }

    /**
     * This is called when a entity explodes in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to a entity exploding
     */
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            event.setCancelled(!isDestroyable(event.blockList(),
                    (Player) event.getEntity()
            ));
        } else {
            event.setCancelled(!isDestroyable(event.blockList(), null));
        }
    }
}
