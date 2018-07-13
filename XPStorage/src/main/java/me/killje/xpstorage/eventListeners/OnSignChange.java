package me.killje.xpstorage.eventListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.sign.SignInventory;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Listener for signs being changed. Notably when a player enters the text after
 * it has been placed.
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnSignChange implements Listener {

    private final static FileConfiguration config
            = XPStorage.getPluginUtil().getConfig();

    /**
     * This is called when a sign text changes
     *
     * @param event The event that belongs to the sign changing
     */
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

        XPStorage.getPluginUtil().runTask(new Runnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();

                AbstractXpSign sign = AbstractXpSign.createSign(
                        (Sign) event.getBlock().getState(), player);

                if (sign == null) {
                    player.sendMessage(XPStorage.getGuiSettings()
                            .getText("noCreatePermmissions"));
                    return;
                }

                sign.updateSign();

                Map<String, String> interactMaterial = new HashMap<>();
                interactMaterial.put("SIGN_INTERACT_MATERIAL",
                        SignInventory.INTERACT_MATERIAL.toString());

                PlayerInformation playerInformation
                        = PlayerInformation.getPlayerInformation(
                                player.getUniqueId()
                        );

                if (playerInformation.isMessage()) {
                    player.sendMessage(XPStorage.getGuiSettings()
                            .getText("signCreated", interactMaterial));
                }

            }
        });
    }
}
