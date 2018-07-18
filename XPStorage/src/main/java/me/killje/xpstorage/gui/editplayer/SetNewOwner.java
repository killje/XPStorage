package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for setting the current selected player as the owner of the group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetNewOwner implements GuiElement {

    /**
     * The player being edited
     */
    private final UUID player;
    /**
     * The sign being edited
     */
    private final AbstractGroupSign sign;

    /**
     * Icon fro setting the selected player as the owner of the group
     *
     * @param player The player being edited
     * @param sign   The sign being edited
     */
    public SetNewOwner(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setNewOwner");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        PlayerInformation.getPlayerInformation(
                sign.getOwner()
        ).getGroupRights(
                sign.getGroup().getGroupUuid()
        ).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);

        PlayerInformation.getPlayerInformation(player).getGroupRights(
                sign.getGroup().getGroupUuid()
        ).removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);

        if (sign instanceof EnderGroupSign) {

            PlayerInformation.getPlayerInformation(player).getGroupRights(
                    sign.getGroup().getGroupUuid()
            ).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);

        }

        sign.setOwner(player);

        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
