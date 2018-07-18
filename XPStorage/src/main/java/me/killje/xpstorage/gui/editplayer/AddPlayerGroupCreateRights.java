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
 * Icon for adding creation and destroy rights of signs for the group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AddPlayerGroupCreateRights implements GuiElement {

    /**
     * The player being edited
     */
    private final UUID player;
    /**
     * The group that this is added for
     */
    private final AbstractGroupSign sign;

    /**
     * Creates a icon for adding the rights to a player to create new signs for
     * a group and destroy existing signs
     *
     * @param player The player that is being edited
     * @param sign   The sign this is being added to
     */
    public AddPlayerGroupCreateRights(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("addPlayerCreateRights");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        PlayerInformation.getPlayerInformation(player).getGroupRights(
                sign.getGroup().getGroupUuid()
        ).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);

        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
