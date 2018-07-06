package me.killje.xpstorage.eventListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.sign.SignInventory;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.util.PluginUtils;
import me.killje.xpstorage.xpsign.AbstractXpSign;
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

    private final static FileConfiguration config = PluginUtils.getConfig();

    public OnSignChange() {
    }

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

        PluginUtils.runTask(new Runnable() {
            @Override
            public void run() {
                Map<String, String> interactMatiral = new HashMap<>();
                interactMatiral.put("SIGN_INTERACT_MATERIAL", SignInventory.INTERACT_MATERIAL.toString());
                PlayerInformation playerInformation = PlayerInformation.getPlayerInformation(event.getPlayer().getUniqueId());
                AbstractXpSign sign = AbstractXpSign.createSign(playerInformation.getDefaultSign(), (Sign) event.getBlock().getState(), event.getPlayer().getUniqueId());
                sign.updateSign();
                if (playerInformation.isMessage()) {
                    event.getPlayer().sendMessage(XPStorage.getGuiSettings().getText("signCreated", interactMatiral));
                }

            }
        });
    }
}
