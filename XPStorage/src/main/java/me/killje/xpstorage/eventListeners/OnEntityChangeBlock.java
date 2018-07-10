package me.killje.xpstorage.eventListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 * Listener for entities changing blocks events
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnEntityChangeBlock extends OnBlockDestory {

    /**
     * This is called when a entity changes a block in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to the entity changing a block
     */
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        Player player = null;
        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        }
        if (!isDestroyable(event.getBlock(), player)) {
            event.setCancelled(true);
            event.getBlock().getState().update();
        }

    }

}
