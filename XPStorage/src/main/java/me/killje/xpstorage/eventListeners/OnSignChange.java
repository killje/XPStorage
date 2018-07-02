package me.killje.xpstorage.eventListeners;

import java.util.ArrayList;
import java.util.List;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnSignChange implements Listener {
    
    private final static FileConfiguration config = XPStorage.getInstance().getConfig();
    
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        
        List<String> tagList = new ArrayList<>();
        if (config.contains("creationTags") && config.isList("creationTags")) {
            tagList = config.getStringList("creationTags");
        }
        
        String signText = event.getLine(0).toLowerCase();
        if (!tagList.contains(signText)) {
            return;
        }
        
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                PlayerInformation playerInformation = PlayerInformation.getPlayerInformation(event.getPlayer().getUniqueId());
                AbstractXpSign sign = AbstractXpSign.createSign(playerInformation.getDefaultSign(),(Sign) event.getBlock().getState(), event.getPlayer().getUniqueId());
                sign.updateSign();
                if(playerInformation.isMessage()) {
                    event.getPlayer().sendMessage(GuiSettingsFromFile.getText("signCreated"));
                }
                
            }
        });
    }
}
