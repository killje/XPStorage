package me.killje.gui.list;

import java.util.ArrayList;
import me.killje.gui.guiElement.GuiElement;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GuiElementList extends List {
    
    private final ArrayList<GuiElement> guiElements;
    private final String inventoryName;

    public GuiElementList(Player currentPlayer, ArrayList<GuiElement> guiElements, String inventoryName) {
        super(currentPlayer);
        this.guiElements = guiElements;
        this.inventoryName = inventoryName;
    }

    @Override
    protected int initInventory(int startIndex, int stopIndex, int maxItemsOnPage) {
        
        this.nextRow();
        for (int i = startIndex; i < stopIndex && i < guiElements.size(); i++) {
            this.addGuiElement(guiElements.get(i));
        }
        
        return guiElements.size();
    }

    @Override
    protected String getInventoryName() {
        return inventoryName;
    }
    
}
