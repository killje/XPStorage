package me.killje.xpstorage.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class Exit implements GuiElement {

    @Override
    public ItemStack getItemStack() {
        return createSimpleItemStack(Material.BARRIER, ChatColor.WHITE + "Exit");
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        event.getView().close();
    }
    
    
}
