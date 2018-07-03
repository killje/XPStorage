package me.killje.xpstorage.gui.settings;

import me.killje.gui.InventoryUtils;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DefaultSignPicker implements GuiElement {
    
    private final Class<? extends AbstractXpSign> defaultSignPick;
    private final PlayerInformation playerInformation;
    private final Player player;
    private final String settingsName;

    public DefaultSignPicker(Player player, Class<? extends AbstractXpSign> defaultSignPick, String settingsName) {
        this.defaultSignPick = defaultSignPick;
        this.player = player;
        this.settingsName = settingsName;
        playerInformation = PlayerInformation.getPlayerInformation(player.getUniqueId());
    }
    
    @Override
    public ItemStack getItemStack() {
        String iconToPick;
        if (playerInformation.getDefaultSign() == defaultSignPick) {
            iconToPick = "selected." + settingsName;
        } else {
            iconToPick = "changeTo." + settingsName;
        }
        return GuiSettingsFromFile.getItemStack(iconToPick);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        if (playerInformation.getDefaultSign() == defaultSignPick) {
            return;
        }
        playerInformation.setDefaultSign(defaultSignPick);
        player.sendMessage(GuiSettingsFromFile.getText("defaultSignSet"));
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }
    
}
