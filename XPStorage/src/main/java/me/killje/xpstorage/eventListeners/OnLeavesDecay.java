package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 * Listener for leaves decaying events
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnLeavesDecay extends OnBlockDestory {

    /**
     * This is called when a leaf decays in the game
     *
     * This cancels the event if a block is protected by this plugin and makes
     * the leave non decayable.
     *
     * @param event The event that belongs to the leaf decaying
     */
    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!isDestroyable(event.getBlock(), null)) {
            event.setCancelled(true);

            byte d = event.getBlock().getData();
            event.getBlock().setData((byte) (d % 4));
        }
    }
}
