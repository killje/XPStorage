package me.killje.xpstorage.gui.groupsettings;

import java.util.UUID;
import me.killje.spigotgui.guielement.InventoryElement;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.Group;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon spot used for setting the icon of a group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetIconElement implements InventoryElement {

    /**
     * The group being edited
     */
    private final UUID groupUUID;

    /**
     * Icon spot for setting the icon of a group. When a item is set here it
     * will change the icon of the group into this
     *
     * @param groupUUID The group being edited
     */
    public SetIconElement(UUID groupUUID) {
        this.groupUUID = groupUUID;
    }

    /**
     * Sets the group icon to the selected material
     *
     * @param groupIcon The material to set the icon to
     */
    private void setIcon(Material groupIcon) {
        Group group = Group.getGroupFromUUID(groupUUID);
        if (group == null) {
            return;
        }
        group.setGroupIcon(groupIcon);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        ItemStack chosenIcon = null;
        switch (event.getAction()) {
            case MOVE_TO_OTHER_INVENTORY:
                chosenIcon = event.getCurrentItem();
                break;
            case PLACE_ALL:
            case PLACE_ONE:
                chosenIcon = event.getCursor().clone();

                event.getWhoClicked().getInventory()
                        .addItem(chosenIcon.clone());

                event.getCursor().setAmount(0);
                break;
            case HOTBAR_SWAP:
                chosenIcon = event.getWhoClicked().getInventory()
                        .getItem(event.getHotbarButton());

                break;
            default:
                break;
        }
        if (chosenIcon == null) {
            return;
        }

        setIcon(chosenIcon.getType());
        event.getWhoClicked().sendMessage(
                currentInventoryBase.getGuiSettings().getText("iconSet")
        );

        currentInventoryBase.closeInventory(event.getWhoClicked());

    }

}
