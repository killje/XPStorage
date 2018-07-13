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
 * Icon for removing the rights of a player to create and destroy signs for this
 * group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class RemovePlayerGroupCreateRights implements GuiElement {

    /**
     * The player being edited
     */
    private final UUID player;
    /**
     * The sign being edited
     */
    private final AbstractGroupSign sign;

    /**
     * Icon for removing the rights of a player to create and destroy signs for
     * this group
     *
     * @param player The player being edited
     * @param sign The sign being edited
     */
    public RemovePlayerGroupCreateRights(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("removePlayerCreateRights");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        PlayerInformation.getPlayerInformation(player).getGroupRights(
                sign.getGroup().getGroupUuid()
        ).removeRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);

        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
