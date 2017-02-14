package me.killje.xpstorage.gui.list;

import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class NextPage implements GuiElement {
    
    private final List list;
    
    public NextPage(List list) {
        this.list = list;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        list.nextPage();
    }

    @Override
    public ItemStack getItemStack() {
        return ItemStackFromFile.getItemStack("nextPage", ChatColor.WHITE + "Next page");
    }
    
    
    
}
