package me.killje.xpstorage.gui.guiElement;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class SimpleGuiElement implements GuiElement{

    private final ItemStack element;

    public SimpleGuiElement (ItemStack itemStack) {
        this.element = itemStack;
    }
    
    @Override
    public ItemStack getItemStack() {
        return element;
        
    }
    
    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
    }
    
}
