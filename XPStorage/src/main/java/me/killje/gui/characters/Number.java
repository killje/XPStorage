package me.killje.gui.characters;

import me.killje.gui.InventoryUtils;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.guiElement.GuiElement;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Number implements GuiElement {

    private final int number;
    private final AmountStorage amountStorage;
    
    public Number(int number, AmountStorage amountStorage) {
        this.number = number;
        this.amountStorage = amountStorage;
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("alphaNum." + number);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        amountStorage.add(number);
    }
    
}
