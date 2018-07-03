package me.killje.util;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PluginUtils {
    
    private static Plugin plugin = null;
    
    public static void setPlugin(Plugin plugin) {
        PluginUtils.plugin = plugin;
    }
    
    public static Plugin getPlugin() {
        if (plugin == null) {
            throw new NullPointerException("Plugin reference is not set. Please add a plugin before referencing the PluginUtils");
        }
        return plugin;
    }
    public static Logger getLogger() {
        return getPlugin().getLogger();
    }
    
    public static BukkitTask runTask(Runnable runnable) {
        return Bukkit.getScheduler().runTask(getPlugin(), runnable);
    }
    
    public static BukkitTask runTaskAsynchronously(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), runnable);
    }
    
    public static BukkitTask runTaskLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(getPlugin(), runnable, delay);
    }
    
    public static BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(getPlugin(), runnable, delay, period);
    }
    
    public static BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), runnable, delay, period);
    }
    
    public static void registerEvents(Listener listener) {
        getPlugin().getServer().getPluginManager().registerEvents(listener, getPlugin());
    }
    
    public static FileConfiguration getConfig() {
        return getPlugin().getConfig();
    }
    
}
