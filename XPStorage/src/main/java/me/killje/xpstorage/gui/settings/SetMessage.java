package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.util.PlayerInformation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for (un)setting to receive messages from XPStorage
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetMessage implements GuiElement {

    /**
     * The player being edited
     */
    private final Player player;
    /**
     * Player information of the player to set the message variable to
     */
    private final PlayerInformation playerInformation;

    /**
     * Icon for setting to receive messages from XPStorage about interaction
     * material and no xp left
     *
     * @param player The player being edited
     */
    public SetMessage(Player player) {
        this.player = player;
        playerInformation
                = PlayerInformation.getPlayerInformation(player.getUniqueId());
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        if (playerInformation.isMessage()) {
            return guiSettings.getItemStack("message.remove");
        } else {
            return guiSettings.getItemStack("message.add");
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        boolean currentGetMessage = playerInformation.isMessage();
        playerInformation.isMessage(!currentGetMessage);

        if (currentGetMessage) {
            player.sendMessage(currentInventoryBase.getGuiSettings()
                    .getText("message.remove"));

        } else {
            player.sendMessage(currentInventoryBase.getGuiSettings()
                    .getText("message.add"));

        }

        currentInventoryBase.closeInventory(player);
    }

}
