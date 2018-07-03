package me.killje.gui.characters;

import me.killje.gui.guiElement.GuiElement;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class SetAmountButton implements GuiElement {

    private AmountStorage amountStorage;

    protected AmountStorage getAmountStorage() {
        return amountStorage;
    }

    public void setAmountStorage(AmountStorage amountStorage) {
        this.amountStorage = amountStorage;
    }

}
