package me.killje.xpstorage.gui;

import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class Exit implements GuiElement {

    @Override
    public ItemStack getItemStack() {
        return ItemStackFromFile.getItemStack("exit", ChatColor.WHITE + "Exit");
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        event.getView().close();
    }
    
    
}
