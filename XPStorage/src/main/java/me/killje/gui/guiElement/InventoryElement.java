package me.killje.gui.guiElement;

import me.killje.gui.InventoryUtils;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public interface InventoryElement {

    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event);

}
