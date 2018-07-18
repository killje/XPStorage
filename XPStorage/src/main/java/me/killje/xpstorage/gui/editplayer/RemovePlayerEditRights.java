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
 * Creates a icon that when clicked on removes the right to a player to add and
 * remove players to a group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class RemovePlayerEditRights implements GuiElement {

    /**
     * The player being edited
     */
    private final UUID player;
    /**
     * The Sign for the group
     */
    private final AbstractGroupSign sign;

    /**
     * Icon for removing the rights of a player to add and remove players to the
     * group
     *
     * @param player The player being edited
     * @param sign   The sign being edited
     */
    public RemovePlayerEditRights(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("removePlayerEditRights");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        PlayerInformation.getPlayerInformation(player).getGroupRights(
                sign.getGroup().getGroupUuid()
        ).removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);

        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
