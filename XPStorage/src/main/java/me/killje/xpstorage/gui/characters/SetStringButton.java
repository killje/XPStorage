package me.killje.xpstorage.gui.characters;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class SetStringButton implements GuiElement {
    
    private KeyBoardStringStorage keyBoardStringStorage;
    
    protected KeyBoardStringStorage getKeyBoardStringStorage() {
        return keyBoardStringStorage;
    }
    
    public void setKeyBoardStringStorage(KeyBoardStringStorage keyBoardStringStorage) {
        this.keyBoardStringStorage = keyBoardStringStorage;
    }
    
    
    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        if (keyBoardStringStorage.getCurrent().equals("")) {
            event.getWhoClicked().sendMessage(textForEmpty());
            return;
        }
        executeSet(currentInventoryUtils, event);
    }
    
    
    
    @Override
    public ItemStack getItemStack() {
        if (keyBoardStringStorage.getCurrent().equals("")) {
            return noNameYetItem();
        }
        return confirmItem();
    }
    
    protected abstract ItemStack confirmItem();
    protected abstract ItemStack noNameYetItem();
    protected abstract String textForEmpty();
    protected abstract void executeSet(InventoryUtils currentInventoryUtils, InventoryClickEvent event);
}
