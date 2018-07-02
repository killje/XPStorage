package me.killje.gui;

import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Exit implements GuiElement {

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("exit");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }
    
    
}
