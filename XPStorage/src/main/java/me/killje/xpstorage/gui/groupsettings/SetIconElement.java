package me.killje.xpstorage.gui.groupsettings;

import java.util.UUID;
import me.killje.xpstorage.group.Group;
import me.killje.gui.InventoryUtils;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.guiElement.InventoryElement;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetIconElement implements InventoryElement {
    
    private final UUID groupUUID;
    
    public SetIconElement(UUID groupUUID) {
        this.groupUUID = groupUUID;
    }
    
    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        ItemStack chosenIcon = null;
        switch (event.getAction()) {
            case MOVE_TO_OTHER_INVENTORY:
                chosenIcon = event.getCurrentItem();
                break;
            case PLACE_ALL:
            case PLACE_ONE:
                chosenIcon = event.getCursor();
                event.getWhoClicked().getInventory().addItem(chosenIcon.clone());
                event.getCursor().setAmount(0);
                break;
            case HOTBAR_SWAP:
                chosenIcon = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
                break;
            default:
                break;
        }
        if (chosenIcon == null) {
            return;
        }
        
        setIcon(chosenIcon.getType());
        event.getWhoClicked().sendMessage(GuiSettingsFromFile.getText("iconSet"));
        currentInventoryUtils.closeInventory(event.getWhoClicked());
        
        
    }
    
    private void setIcon(Material groupIcon) {
        Group group = Group.getGroupFromUUID(groupUUID);
        if (group == null) {
            return;
        }
        group.setGroupIcon(groupIcon);
    }
    
}
