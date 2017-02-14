package me.killje.xpstorage.eventListeners;

import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.xpsign.NormalSign;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 *
 * @author Zolder
 */
public class OnSignChange implements Listener {
    
    
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!event.getLine(0).toLowerCase().equals("[xp storage]") && !event.getLine(0).toLowerCase().equals("[xp]")) {
            return;
        }
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                new NormalSign((Sign) event.getBlock().getState(), event.getPlayer().getUniqueId());
            }
        });
    }
}
