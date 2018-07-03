package me.killje.util;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class clsConfiguration {

    private final Plugin plugin;
    private FileConfiguration conf = null;
    private File file = null;
    private String fname = null;

    public clsConfiguration(Plugin plugin, String filename) {
        this(plugin, filename, false);
    }

    public clsConfiguration(Plugin plugin, String filename, boolean saveDefault) {
        this.plugin = plugin;
        fname = filename;
        if (saveDefault) {
            file = new File(plugin.getDataFolder(), fname);
            if (!file.exists()) {
                InputStream isDefaults = plugin.getResource(fname);
                try {
                    try (FileOutputStream outputStream = new FileOutputStream(file)) {
                        ByteStreams.copy(isDefaults, outputStream);
                        outputStream.flush();
                        isDefaults.close();
                    }
                } catch (FileNotFoundException ex) {
                    plugin.getLogger().log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    plugin.getLogger().log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //Load the conf file and search for configs in the jar memory
    public void ReloadConfig() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), fname);
        }
        
        conf = YamlConfiguration.loadConfiguration(file);

        //Look for defaults in the jar
        InputStream isDefaults = plugin.getResource(fname);
        if (isDefaults != null) {
            Reader reader = new InputStreamReader(isDefaults);
            YamlConfiguration confDefault = YamlConfiguration.loadConfiguration(reader);
            conf.setDefaults(confDefault);
        }
    }

    public FileConfiguration GetConfig() {
        if (conf == null) {
            ReloadConfig();	//If it's null, try reloading the configs
        }
        return conf;
    }

    public void SaveConfig() {
        if (conf == null || file == null) {
            return;
        }
        try {
            conf.save(file);	//Save the memory configurations to the config file
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "IOException: Error saving configuration file '{0}'!", fname);
            plugin.getLogger().log(Level.SEVERE, ex.toString());
        }
    }
}
