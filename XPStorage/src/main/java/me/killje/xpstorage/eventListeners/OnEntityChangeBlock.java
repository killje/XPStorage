package me.killje.xpstorage.eventListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnEntityChangeBlock extends OnBlockDestory {

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
