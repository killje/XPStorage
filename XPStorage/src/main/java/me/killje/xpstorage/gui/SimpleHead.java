package me.killje.xpstorage.gui;

import me.killje.xpstorage.utils.HeadUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public abstract class SimpleHead implements GuiElement {
    
    private final String texture;
    private final String displayName;

    public SimpleHead(String texture) {
        this.texture = texture;
        this.displayName = null;
    }
    
    public SimpleHead(String texture, String displayName) {
        this.texture = texture;
        this.displayName = displayName;
    }
    
    @Override
    public ItemStack getItemStack() {
        
        ItemStack itemStack = HeadUtils.getTexturedHead(texture);
        
        if (displayName != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(displayName);
            itemStack.setItemMeta(itemMeta);
        }
        
        return itemStack;
    }
    
}
