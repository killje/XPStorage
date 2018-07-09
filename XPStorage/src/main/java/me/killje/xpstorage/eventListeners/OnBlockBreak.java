package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnBlockBreak extends OnBlockDestory {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(!isDestroyable(event.getBlock(), event.getPlayer()));
    }
}
