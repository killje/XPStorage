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
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

/**
 * A utility class for bukkit plugin to use some function with less input
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PluginUtil {

    /**
     * The plugin to reference
     */
    private final Plugin plugin;

    /**
     * Creates a plugin util
     *
     * @param plugin The plugin to use
     */
    public PluginUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @see Plugin#getConfig()
     *
     * Returns the config file of the plugin.
     *
     * @return The config of the plugin
     */
    public FileConfiguration getConfig() {
        return getPlugin().getConfig();
    }

    /**
     * Get the logger of the plugin
     *
     * @see Plugin#getLogger()
     *
     * @return The logger
     */
    public Logger getLogger() {
        return getPlugin().getLogger();
    }

    /**
     * Returns the plugin instance itself
     *
     * @return
     */
    public Plugin getPlugin() {
        if (plugin == null) {
            throw new NullPointerException(
                    "Plugin reference is not set. "
                    + "Please add a plugin before referencing the PluginUtils"
            );
        }
        return plugin;
    }

    /**
     * @see PluginManager#registerEvents(Listener, Plugin)
     *
     * Register the events in a class to the bukkit plugin manager
     *
     * @param listener The listener class to register
     */
    public void registerEvents(Listener listener) {
        getPlugin().getServer().getPluginManager()
                .registerEvents(listener, getPlugin());
    }

    /**
     * @see BukkitScheduler#runTask(Plugin, Runnable)
     *
     * @param runnable The runnable class to run
     *
     * @return The task created
     */
    public BukkitTask runTask(Runnable runnable) {
        return Bukkit.getScheduler().runTask(getPlugin(), runnable);
    }

    /**
     * @see BukkitScheduler#runTaskAsynchronously(Plugin, Runnable)
     *
     * @param runnable The runnable class to run asynchronous
     *
     * @return The task created
     */
    public BukkitTask runTaskAsynchronously(Runnable runnable) {
        return Bukkit.getScheduler()
                .runTaskAsynchronously(getPlugin(), runnable);
    }

    /**
     * @see BukkitScheduler#runTaskLater(Plugin, Runnable, long)
     *
     * @param runnable The runnable class to run after a period
     * @param delay    The delay in server ticks. On normal tps a second is
     *                 equal to 20 delay
     *
     * @return The task created
     */
    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(getPlugin(), runnable, delay);
    }

    /**
     * @see BukkitScheduler#runTaskTimer(Plugin, Runnable, long, long)
     *
     * @param runnable The runnable class to run after a period periodically
     * @param delay    The delay in server ticks. On normal tps a second is
     *                 equal to 20 delay
     * @param period   The period to wait before running again. On a normal tps
     *                 a second is to to a period of 20
     *
     * @return The task created
     */
    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler()
                .runTaskTimer(getPlugin(), runnable, delay, period);
    }

    /**
     * @see BukkitScheduler#runTaskTimerAsynchronously(Plugin, Runnable, long,
     * long)
     *
     * @param runnable The runnable class to run after a period periodically
     *                 asynchronously
     * @param delay    The delay in server ticks. On normal tps a second is
     *                 equal to 20 delay
     * @param period   The period to wait before running again. On a normal tps
     *                 a second is to to a period of 20
     *
     * @return The task created
     */
    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay,
            long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(),
                runnable, delay, period);
    }

    /**
     * Unloads the plugin from the server.
     *
     * @return True if the unloading was successful
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NullPointerException
     */
    public boolean unloadPlugin() throws NoSuchFieldException,
            IllegalAccessException, NullPointerException {

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
            Class<? extends PluginManager> pluginManagerClass
                    = pluginManager.getClass();

            Field pluginsField = pluginManagerClass.getDeclaredField("plugins");

            pluginsField.setAccessible(true);
            plugins = (List<Plugin>) pluginsField.get(pluginManager);

            Field lookupNamesField
                    = pluginManagerClass.getDeclaredField("lookupNames");

            lookupNamesField.setAccessible(true);
            names = (Map<String, Plugin>) lookupNamesField.get(pluginManager);

            try {
                Field listenersField
                        = pluginManagerClass.getDeclaredField("listeners");

                listenersField.setAccessible(true);

                Object field = listenersField.get(pluginManager);

                listeners = (Map<Event, SortedSet<RegisteredListener>>) field;

            } catch (IllegalAccessException | IllegalArgumentException
                    | NoSuchFieldException | SecurityException e) {
                reloadlisteners = false;
            }

            Field commandMapField
                    = pluginManagerClass.getDeclaredField("commandMap");

            commandMapField.setAccessible(true);
            cmdMap = (SimpleCommandMap) commandMapField.get(pluginManager);

            Field knownCommandsField
                    = cmdMap.getClass().getDeclaredField("knownCommands");

            knownCommandsField.setAccessible(true);
            commands = (Map<String, Command>) knownCommandsField.get(cmdMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw e;
        }

        pluginManager.disablePlugin(plugin);

        if (plugins != null && plugins.contains(plugin)) {
            plugins.remove(plugin);
        }

        if (names != null
                && names.containsKey(plugin.getDescription().getName())) {
            names.remove(plugin.getDescription().getName());
        }

        if (listeners != null && reloadlisteners) {
            for (SortedSet<RegisteredListener> set : listeners.values()) {
                for (Iterator<RegisteredListener> it
                        = set.iterator(); it.hasNext();) {

                    RegisteredListener value = it.next();

                    if (value.getPlugin() == plugin) {
                        it.remove();
                    }
                }
            }
        }

        for (Iterator<Map.Entry<String, Command>> it
                = commands.entrySet().iterator(); it.hasNext();) {

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
