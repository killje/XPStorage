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
public class Character implements GuiElement {
    
    private final char character;
    private final KeyBoardStringStorage keyBoardStringStorage;

    public Character(char character, KeyBoardStringStorage keyBoardStringStorage) {
        this.character = character;
        this.keyBoardStringStorage = keyBoardStringStorage;
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("alphaNum." + character);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        keyBoardStringStorage.add(character + "");
    }

}
