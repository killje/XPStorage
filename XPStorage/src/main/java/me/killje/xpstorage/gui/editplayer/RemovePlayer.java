package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon that when clicked on removes a player from the group selected
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class RemovePlayer implements GuiElement {

    /**
     * The player being edited
     */
    private final UUID player;
    /**
     * The sign this action will apply on
     */
    private final AbstractGroupSign sign;

    /**
     * Creates a icon for removing a player from a group
     *
     * @param player The player being edited
     * @param sign The sign the player will be removed from
     */
    public RemovePlayer(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("removePlayer");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        sign.getGroup().removePlayerFromGroup(player);
        currentInventoryBase.closeInventory(event.getWhoClicked());
    }
}
