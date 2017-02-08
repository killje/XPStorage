package me.killje.xpstorage.eventListeners2;

import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;

/**
 *
 * @author Zolder
 */
public abstract class OnBlockDestory implements Listener {
    
    protected boolean destroyEvent(BlockEvent event) {
        if (!event.getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
            return false;
        }
        AbstractXpSign xpSign = (AbstractXpSign) event.getBlock().getMetadata("XP_STORAGE_XPSIGN").get(0).value();
        return xpSign.destroySign();
    }
}
