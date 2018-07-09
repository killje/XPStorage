package me.killje.xpstorage;

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
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class XPStorage extends JavaPlugin {

    private static GuiSetting guiSettings;
    private static PluginUtil pluginUtil;
    private static clsConfiguration signs;

    private boolean init = true;

    public static clsConfiguration getSignConfig() {
        return signs;
    }

    public static GuiSetting getGuiSettings() {
        return guiSettings;
    }

    public static PluginUtil getPluginUtil() {
        return pluginUtil;
    }

    public boolean isInit() {
        return init;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        pluginUtil = new PluginUtil(this);

        guiSettings = new GuiSetting(this, "GUI.yml");
        signs = new clsConfiguration(this, "Signs.yml");

        getServer().getPluginManager().registerEvents(new OnSignChange(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new OnBlockBurn(), this);
        getServer().getPluginManager().registerEvents(new OnBlockExplode(), this);
        getServer().getPluginManager().registerEvents(new OnBlockIgnite(), this);
        getServer().getPluginManager().registerEvents(new OnBlockPistonExtend(), this);
        getServer().getPluginManager().registerEvents(new OnBlockPistonRetract(), this);
        getServer().getPluginManager().registerEvents(new OnLeavesDecay(), this);
        getServer().getPluginManager().registerEvents(new OnEntityBreakDoor(), this);
        getServer().getPluginManager().registerEvents(new OnEntityChangeBlock(), this);

        getCommand("xpreloadgui").setExecutor((sender, command, label, args) -> {
            guiSettings.reloadConfig();
            sender.sendMessage("The GUI of XPStorage has been reloaded");
            return true;
        });

        getCommand("xpstorage").setExecutor((sender, command, label, args) -> {
            sender.sendMessage("Visit " + ChatColor.UNDERLINE + "https://github.com/killje/XPStorage/wiki/" + ChatColor.RESET + " for information on how to use XPStorage");
            return true;
        });

        ConfigurationSerialization.registerClass(PlayerInformation.class);
        ConfigurationSerialization.registerClass(GroupRights.class);
        ConfigurationSerialization.registerClass(Group.class);
        ConfigurationSerialization.registerClass(SignSaver.class);
        ConfigurationSerialization.registerClass(LocalPlayerSign.class, "me.killje.xpstorage.xpsign.NormalSign");
        ConfigurationSerialization.registerClass(LocalPlayerSign.class);
        ConfigurationSerialization.registerClass(EnderPlayerSign.class, "me.killje.xpstorage.xpsign.PlayerSign");
        ConfigurationSerialization.registerClass(EnderPlayerSign.class);
        ConfigurationSerialization.registerClass(LocalGroupSign.class, "me.killje.xpstorage.xpsign.SharedSign");
        ConfigurationSerialization.registerClass(LocalGroupSign.class);
        ConfigurationSerialization.registerClass(EnderGroupSign.class, "me.killje.xpstorage.xpsign.GroupSign");
        ConfigurationSerialization.registerClass(EnderGroupSign.class);

        PlayerInformation.loadPlayerInformation();
        Group.loadGroups();
        AbstractXpSign.loadSigns();
        init = false;

    }

    @Override
    public void onDisable() {
        PlayerInformation.savePlayerInformation();
        Group.saveGroups();
        AbstractXpSign.saveSigns(true);
        AbstractXpSign.destroyMetaDatas();
    }

}
