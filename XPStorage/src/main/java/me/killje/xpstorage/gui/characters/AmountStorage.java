package me.killje.xpstorage.gui.characters;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AmountStorage extends AbstractStorage<Integer> {
    
    private int currentAmount = 0;
    
    @Override
    protected void addToStorage(Integer amountToAdd) {
        if (currentAmount > Integer.MAX_VALUE / 10 - amountToAdd) {
            return;
        }
        currentAmount = currentAmount * 10 + amountToAdd;
    }
    
    @Override
    public Integer getCurrent() {
        return currentAmount;
    }

    @Override
    protected void removeLastFromStorage() {
        currentAmount /= 10;
    }

    
}
