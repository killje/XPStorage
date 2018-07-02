package me.killje.xpstorage.eventListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreakDoorEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnEntityBreakDoor extends OnBlockBreak{
    
    @EventHandler
    public void onEntityBreakDoor (EntityBreakDoorEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(!isDestroyable(event.getBlock(), (Player) event.getEntity()));
        } else {
            event.setCancelled(!isDestroyable(event.getBlock(), null));
        }
    }
    
}
