package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnLeavesDecay extends OnBlockDestory {

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!isDestroyable(event.getBlock(), null)) {
            event.setCancelled(true);

            byte d = event.getBlock().getData();
            event.getBlock().setData((byte) (d % 4));
        }
    }
}
