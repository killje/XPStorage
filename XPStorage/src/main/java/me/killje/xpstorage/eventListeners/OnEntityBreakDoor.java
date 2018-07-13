package me.killje.xpstorage.eventListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreakDoorEvent;

/**
 * Listener for entities breaking door events
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnEntityBreakDoor extends OnBlockBreak {

    /**
     * This is called when a entity breaks a door in the game
     *
     * This cancels the event if a block is protected by this plugin
     *
     * @param event The event that belongs to the entity breaking a door
     */
    @EventHandler
    public void onEntityBreakDoor(EntityBreakDoorEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(!isDestroyable(event.getBlock(),
                    (Player) event.getEntity()
            ));
        } else {
            event.setCancelled(!isDestroyable(event.getBlock(), null));
        }
    }

}
