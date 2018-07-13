package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for setting the the rights to a player to add and remove players to a
 * sign
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AddPlayerEditRights implements GuiElement {

    /**
     * The player being edited
     */
    private final UUID player;

    /**
     * The sign the player is being edited on
     */
    private final AbstractGroupSign sign;

    /**
     * Creates a icon for adding the rights to a player for adding and removing
     * players from the group
     *
     * @param player The player being edited
     * @param sign The sign the player will be added to
     */
    public AddPlayerEditRights(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("addPlayerEditRights");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        PlayerInformation.getPlayerInformation(player).getGroupRights(
                sign.getGroup().getGroupUuid()
        ).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);

        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
