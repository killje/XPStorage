package me.killje.xpstorage;

import java.util.UUID;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.clsConfiguration;
import me.killje.xpstorage.eventListeners.OnBlockBreak;
import me.killje.xpstorage.eventListeners.OnBlockBurn;
import me.killje.xpstorage.eventListeners.OnBlockExplode;
import me.killje.xpstorage.eventListeners.OnBlockIgnite;
import me.killje.xpstorage.eventListeners.OnBlockPistonExtend;
import me.killje.xpstorage.eventListeners.OnBlockPistonRetract;
import me.killje.xpstorage.eventListeners.OnEntityBreakDoor;
import me.killje.xpstorage.eventListeners.OnEntityChangeBlock;
import me.killje.xpstorage.eventListeners.OnLeavesDecay;
import me.killje.xpstorage.eventListeners.OnPlayerInteract;
import me.killje.xpstorage.eventListeners.OnSignChange;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.util.PluginUtil;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import me.killje.xpstorage.xpsign.LocalPlayerSign;
import me.killje.xpstorage.xpsign.EnderPlayerSign;
import me.killje.xpstorage.xpsign.LocalGroupSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * The main file. This loads and unloads the plugin
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class XPStorage extends JavaPlugin {

    /**
     * Pattern for uuids
     */
    private static final String UUID_PATTERN
            = "[0-9a-f]{8}"
            + "-[0-9a-f]{4}"
            + "-[1-5][0-9a-f]{3}"
            + "-[89ab][0-9a-f]{3}"
            + "-[0-9a-f]{12}";
    /**
     * The gui file from where the icons and text are loaded
     */
    private static GuiSetting guiSettings;
    /**
     * The PluginUtil for the current instance
     */
    private static PluginUtil pluginUtil;
    /**
     * The current Plugin instance
     */
    private static Plugin plugin_;
    /**
     * The signs config from where signs are loaded
     */
    private static clsConfiguration signs;

    /**
     * Get the gui settings
     *
     * @return The gui settings
     */
    public static GuiSetting getGuiSettings() {
        return guiSettings;
    }

    /**
     * Get the plugin for the current instance
     *
     * @return The plugin
     */
    public static Plugin getPlugin() {
        return plugin_;
    }

    /**
     * The PluginUtil for the current instance
     *
     * @return The plugin utils
     */
    public static PluginUtil getPluginUtil() {
        return pluginUtil;
    }

    /**
     * Get the sign config for the current instance
     *
     * @return The config
     */
    public static clsConfiguration getSignConfig() {
        return signs;
    }
    /**
     * Whether or not the plugin has fully initialized
     */
    private boolean init = true;

    /**
     * Gets if the plugin is initialized
     *
     * @return True if the plugin is initialized, false otherwise
     */
    public boolean isInit() {
        return init;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onDisable() {
        PlayerInformation.savePlayerInformation();
        Group.saveGroups();
        AbstractXpSign.saveSigns(true);
        AbstractXpSign.destroyMetaDatas();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onEnable() {
        saveDefaultConfig();
        pluginUtil = new PluginUtil(this);
        plugin_ = this;

        guiSettings = new GuiSetting(this, "GUI.yml");
        signs = new clsConfiguration(this, "Signs.yml");

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new OnSignChange(), this);
        pluginManager.registerEvents(new OnPlayerInteract(), this);
        pluginManager.registerEvents(new OnBlockBreak(), this);
        pluginManager.registerEvents(new OnBlockBurn(), this);
        pluginManager.registerEvents(new OnBlockExplode(), this);
        pluginManager.registerEvents(new OnBlockIgnite(), this);
        pluginManager.registerEvents(new OnBlockPistonExtend(), this);
        pluginManager.registerEvents(new OnBlockPistonRetract(), this);
        pluginManager.registerEvents(new OnLeavesDecay(), this);
        pluginManager.registerEvents(new OnEntityBreakDoor(), this);
        pluginManager.registerEvents(new OnEntityChangeBlock(), this);

        getCommand("xpreloadgui").setExecutor((sender, command, label, args)
                -> {
            guiSettings.reloadConfig();
            sender.sendMessage("The GUI of XPStorage has been reloaded");
            return true;
        });

        getCommand("xpstorage").setExecutor((sender, command, label, args) -> {
            sender.sendMessage("Visit " + ChatColor.UNDERLINE
                    + "https://github.com/killje/XPStorage/wiki/"
                    + ChatColor.RESET
                    + " for information on how to use XPStorage");
            return true;
        });

        getCommand("xpworlduuid").setExecutor((sender, command, label, args)
                -> {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Can only use this command as a player."
                            + "Use 'all', a world name or a uuid instead");
                    return false;
                }
                World world = ((Player) sender).getWorld();

                sender.sendMessage(world.getName() + ": "
                        + world.getUID().toString());
                return true;
            }
            if (args[0].equals("all")) {
                for (World world : Bukkit.getWorlds()) {
                    sender.sendMessage(world.getName() + ": "
                            + world.getUID().toString());
                }
                return true;
            }
            if (args[0].toLowerCase().matches(UUID_PATTERN)) {
                World world = Bukkit.getWorld(UUID.fromString(args[0]));
                sender.sendMessage(world.getName() + ": "
                        + world.getUID().toString());

                return true;
            }
            World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                sender.sendMessage("Could not find the given world by name");
                return true;
            }
            sender.sendMessage(world.getName() + ": "
                    + world.getUID().toString());

            return true;
        });

        ConfigurationSerialization.registerClass(PlayerInformation.class);
        ConfigurationSerialization.registerClass(GroupRights.class);
        ConfigurationSerialization.registerClass(Group.class);
        ConfigurationSerialization.registerClass(SignSaver.class);
        ConfigurationSerialization.registerClass(LocalPlayerSign.class,
                "me.killje.xpstorage.xpsign.NormalSign");
        ConfigurationSerialization.registerClass(LocalPlayerSign.class);
        ConfigurationSerialization.registerClass(EnderPlayerSign.class,
                "me.killje.xpstorage.xpsign.PlayerSign");
        ConfigurationSerialization.registerClass(EnderPlayerSign.class);
        ConfigurationSerialization.registerClass(LocalGroupSign.class,
                "me.killje.xpstorage.xpsign.SharedSign");
        ConfigurationSerialization.registerClass(LocalGroupSign.class);
        ConfigurationSerialization.registerClass(EnderGroupSign.class,
                "me.killje.xpstorage.xpsign.GroupSign");
        ConfigurationSerialization.registerClass(EnderGroupSign.class);

        PlayerInformation.loadPlayerInformation();
        Group.loadGroups();
        AbstractXpSign.loadSigns();
        init = false;

    }

}
