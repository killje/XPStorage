package me.killje.xpstorage;

import me.killje.xpstorage.eventListeners.OnBlockBreak;
import me.killje.xpstorage.eventListeners.OnBlockBurn;
import me.killje.xpstorage.eventListeners.OnPlayerInteract;
import me.killje.xpstorage.eventListeners.OnSignChange;
import me.killje.xpstorage.eventListeners.OnBlockExplode;
import me.killje.xpstorage.eventListeners.OnBlockIgnite;
import me.killje.xpstorage.eventListeners.OnEntityChangeBlock;
import me.killje.xpstorage.eventListeners.OnBlockPistonExtend;
import me.killje.xpstorage.eventListeners.OnBlockPistonRetract;
import me.killje.xpstorage.eventListeners.OnEntityBreakDoor;
import me.killje.xpstorage.eventListeners.OnLeavesDecay;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.util.clsConfiguration;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
import me.killje.xpstorage.xpsign.NormalSign;
import me.killje.xpstorage.xpsign.PlayerSign;
import me.killje.xpstorage.xpsign.SharedSign;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class XPStorage extends JavaPlugin {

    private static clsConfiguration signs;
    private static XPStorage instance;
    private boolean init = true;

    public static XPStorage getInstance() {
        return instance;
    }
    
    public static clsConfiguration getSignConfig() {
        return signs;
    }
    
    public boolean isInit() {
        return init;
    }
    
    @Override
    public void onEnable() {
        
        saveDefaultConfig();
        
        signs = new clsConfiguration(this, "Signs.yml");
        instance = this;
        
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
        
        ConfigurationSerialization.registerClass(PlayerInformation.class);
        ConfigurationSerialization.registerClass(GroupRights.class);
        ConfigurationSerialization.registerClass(Group.class);
        ConfigurationSerialization.registerClass(SignSaver.class);
        ConfigurationSerialization.registerClass(PlayerSign.class);
        ConfigurationSerialization.registerClass(NormalSign.class);
        ConfigurationSerialization.registerClass(SharedSign.class);
        ConfigurationSerialization.registerClass(GroupSign.class);
        
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
