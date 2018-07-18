package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for selecting current sign as the new default sign
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DefaultSignPicker implements GuiElement {

    /**
     * The sign to be set as a default sign
     */
    private final Class<? extends AbstractXpSign> defaultSignPick;
    /**
     * The player to set the default sign for
     */
    private final Player player;
    /**
     * The player information for the current player to set the default sign for
     */
    private final PlayerInformation playerInformation;
    /**
     * The GUI.yml name to use for the text
     */
    private final String settingsName;

    /**
     * Icon for setting the default sign of the player to the given type
     *
     * @param player          The player to set the default sign type for
     * @param defaultSignPick The sign type to set it to
     * @param settingsName    The GUI.yml name for text
     */
    public DefaultSignPicker(Player player,
            Class<? extends AbstractXpSign> defaultSignPick,
            String settingsName) {

        this.defaultSignPick = defaultSignPick;
        this.player = player;
        this.settingsName = settingsName;
        playerInformation
                = PlayerInformation.getPlayerInformation(player.getUniqueId());
    }

    @Override
    /**
     * {@inheritDoc}
     */
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
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        if (playerInformation.getDefaultSign() == defaultSignPick) {
            return;
        }

        playerInformation.setDefaultSign(defaultSignPick);
        player.sendMessage(currentInventoryBase.getGuiSettings()
                .getText("defaultSignSet"));

        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
