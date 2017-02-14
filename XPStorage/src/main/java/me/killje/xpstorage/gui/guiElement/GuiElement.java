package me.killje.xpstorage.gui.guiElement;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public interface GuiElement {
    
    public ItemStack getItemStack();
    public void onGuiElementClickEvent(InventoryClickEvent event);
    
}
