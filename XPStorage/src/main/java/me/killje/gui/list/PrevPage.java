package me.killje.gui.list;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PrevPage implements GuiElement {
    
    private final List list;
    
    public PrevPage(List list) {
        this.list = list;
    }
    
    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        list.previousPage();
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("page.prev");
    }
    
}
