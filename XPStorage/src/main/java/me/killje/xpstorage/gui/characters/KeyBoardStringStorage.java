package me.killje.xpstorage.gui.characters;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class KeyBoardStringStorage extends AbstractStorage<String> {
    
    private String currentStorage = "";
    
    @Override
    protected void addToStorage(String character) {
        currentStorage += character;
    }
    
    @Override
    public String getCurrent() {
        return currentStorage;
    }
    
    @Override
    public void removeLastFromStorage() {
        if (currentStorage.isEmpty()) {
            return;
        }
        currentStorage = currentStorage.substring(0, currentStorage.length() - 1);
    }
    
    
}
