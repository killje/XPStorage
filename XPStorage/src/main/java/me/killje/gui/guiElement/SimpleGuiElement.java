package me.killje.gui.guiElement;

import me.killje.gui.InventoryUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SimpleGuiElement implements GuiElement{

    private final ItemStack element;
    private final String name;

    public SimpleGuiElement (ItemStack itemStack) {
        this.element = itemStack;
        name = null;
    }

    public SimpleGuiElement(ItemStack element, String name) {
        this.element = element;
        this.name = name;
    }
    
    
    @Override
    public ItemStack getItemStack() {
        if (name != null) {
            ItemMeta itemMeta = element.getItemMeta();
            itemMeta.setDisplayName(name);
            element.setItemMeta(itemMeta);
        }
        return element;
        
    }
    
    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
    }
    
}
