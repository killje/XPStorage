package me.killje.xpstorage.gui;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public interface GuiElement {
    
    public ItemStack getItemStack();
    public void onGuiElementClickEvent(InventoryClickEvent event);
    
    public default ItemStack createSimpleItemStack(Material material, String displayName){
        return createSimpleItemStack(material, displayName, null);
    }
    
    public default ItemStack createSimpleItemStack(Material material, String displayName, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public default ItemStack createSimpleItemStack(Material material, String displayName, int damageValue, List<String> lore){
        ItemStack itemStack = new ItemStack(material, 1,(short) damageValue);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
