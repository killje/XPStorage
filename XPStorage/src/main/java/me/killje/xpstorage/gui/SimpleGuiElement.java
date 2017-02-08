package me.killje.xpstorage.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 *
 * @author Zolder
 */
public class SimpleGuiElement implements GuiElement{

    private final ItemStack element;

    public SimpleGuiElement(Material material, String title) {
        element = new ItemStack(material);
        ItemMeta itemMeta = element.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + title);
        element.setItemMeta(itemMeta);
    }
    
    public SimpleGuiElement(MaterialData materialData, String title) {
        element = materialData.toItemStack(1);
        ItemMeta itemMeta = element.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + title);
        element.setItemMeta(itemMeta);
    }
    
    @Override
    public ItemStack getItemStack() {
        return element;
        
    }
    
    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
    }
    
}
