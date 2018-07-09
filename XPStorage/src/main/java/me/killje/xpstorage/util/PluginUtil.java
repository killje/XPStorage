package me.killje.xpstorage.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PluginUtil {

    private final Plugin plugin;

    public PluginUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        if (plugin == null) {
            throw new NullPointerException("Plugin reference is not set. Please add a plugin before referencing the PluginUtils");
        }
        return plugin;
    }

    public Logger getLogger() {
        return getPlugin().getLogger();
    }

    public BukkitTask runTask(Runnable runnable) {
        return Bukkit.getScheduler().runTask(getPlugin(), runnable);
    }

    public BukkitTask runTaskAsynchronously(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), runnable);
    }

    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(getPlugin(), runnable, delay);
    }

    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(getPlugin(), runnable, delay, period);
    }

    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), runnable, delay, period);
    }

    public void registerEvents(Listener listener) {
        getPlugin().getServer().getPluginManager().registerEvents(listener, getPlugin());
    }

    public FileConfiguration getConfig() {
        return getPlugin().getConfig();
    }

    public boolean unloadPlugin() throws NoSuchFieldException, IllegalAccessException, NullPointerException {

        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        SimpleCommandMap cmdMap = null;
        List<Plugin> plugins = null;
        Map<String, Plugin> names = null;
        Map<String, Command> commands = null;
        Map<Event, SortedSet<RegisteredListener>> listeners = null;
        boolean reloadlisteners = true;
        if (pluginManager == null) {
            throw new NullPointerException("Could not find pluginManager");
        }
        try {
            Field pluginsField = pluginManager.getClass().getDeclaredField("plugins");
            pluginsField.setAccessible(true);
            plugins = (List<Plugin>) pluginsField.get(pluginManager);

            Field lookupNamesField = pluginManager.getClass().getDeclaredField("lookupNames");
            lookupNamesField.setAccessible(true);
            names = (Map<String, Plugin>) lookupNamesField.get(pluginManager);

            try {
                Field listenersField = pluginManager.getClass().getDeclaredField("listeners");
                listenersField.setAccessible(true);
                listeners = (Map<Event, SortedSet<RegisteredListener>>) listenersField.get(pluginManager);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                reloadlisteners = false;
            }

            Field commandMapField = pluginManager.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            cmdMap = (SimpleCommandMap) commandMapField.get(pluginManager);

            Field knownCommandsField = cmdMap.getClass().getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            commands = (Map<String, Command>) knownCommandsField.get(cmdMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw e;
        }

        pluginManager.disablePlugin(plugin);

        if (plugins != null && plugins.contains(plugin)) {
            plugins.remove(plugin);
        }

        if (names != null && names.containsKey(plugin.getDescription().getName())) {
            names.remove(plugin.getDescription().getName());
        }

        if (listeners != null && reloadlisteners) {
            for (SortedSet<RegisteredListener> set : listeners.values()) {
                for (Iterator<RegisteredListener> it = set.iterator(); it.hasNext();) {
                    RegisteredListener value = it.next();

                    if (value.getPlugin() == plugin) {
                        it.remove();
                    }
                }
            }
        }

        for (Iterator<Map.Entry<String, Command>> it = commands.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Command> entry = it.next();
            if (entry.getValue() instanceof PluginCommand) {
                PluginCommand command = (PluginCommand) entry.getValue();
                if (command.getPlugin() == plugin) {
                    command.unregister(cmdMap);
                    it.remove();
                }
            }
        }

        return true;
    }

}
