package me.killje.gui.characters;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Undo implements GuiElement {

    private final AbstractStorage abstractStorage;
    private final String guiSettingName;
    
    public Undo(AbstractStorage abstractStorage, String guiSettingName) {
        this.abstractStorage = abstractStorage;
        this.guiSettingName = guiSettingName;
    }
    
    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack(guiSettingName);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        abstractStorage.removeLast();
    }
    
}
