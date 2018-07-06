package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.util.PlayerInformation;
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
    public ItemStack getItemStack(GuiSetting guiSettings) {
        String iconToPick;
        if (playerInformation.getDefaultSign() == defaultSignPick) {
            iconToPick = "selected." + settingsName;
        } else {
            iconToPick = "changeTo." + settingsName;
        }
        return guiSettings.getItemStack(iconToPick);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        if (playerInformation.getDefaultSign() == defaultSignPick) {
            return;
        }
        playerInformation.setDefaultSign(defaultSignPick);
        player.sendMessage(currentInventoryUtils.getGuiSettings().getText("defaultSignSet"));
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }

}
