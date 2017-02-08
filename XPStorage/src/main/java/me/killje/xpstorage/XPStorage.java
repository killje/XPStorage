package me.killje.xpstorage;

import me.killje.xpstorage.EventListeners.OnBlockBreak;
import me.killje.xpstorage.EventListeners.OnBlockBurn;
import me.killje.xpstorage.EventListeners.OnPlayerInteract;
import me.killje.xpstorage.EventListeners.OnSignChange;
import me.killje.xpstorage.commandExecuters.CreateGroup;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.utils.clsConfiguration;
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
 * @author Patrick Beuks (killje)
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
        
        ConfigurationSerialization.registerClass(PlayerInformation.class);
        ConfigurationSerialization.registerClass(GroupRights.class);
        ConfigurationSerialization.registerClass(Group.class);
        ConfigurationSerialization.registerClass(SignSaver.class);
        ConfigurationSerialization.registerClass(PlayerSign.class);
        ConfigurationSerialization.registerClass(NormalSign.class);
        ConfigurationSerialization.registerClass(SharedSign.class);
        ConfigurationSerialization.registerClass(GroupSign.class);
        
        this.getCommand("createXpGroup").setExecutor(new CreateGroup());
        System.out.println("loadPlayer");
        PlayerInformation.loadPlayerInformation();
        System.out.println("LoadGroups");
        Group.loadGroups();
        System.out.println("LoadSigns");
        AbstractXpSign.loadSigns();
        init = false;
    }

    @Override
    public void onDisable() {
        PlayerInformation.savePlayerInformation();
        Group.saveGroups();
    }
    
}
