package me.killje.xpstorage.gui.characters;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 * @param <TYPE> The storage type
 */
public abstract class AbstractStorage<TYPE> {
    
    private final List<StorageUpdateListener> listeners = new ArrayList<>();
    
    protected abstract void removeLastFromStorage();
    public abstract TYPE getCurrent();
    
    protected abstract void addToStorage(TYPE amountToAdd);
    
    public void add(TYPE amountToAdd){
        addToStorage(amountToAdd);
        sendUpdate();
    }
    
    public void removeLast(){
        removeLastFromStorage();
        sendUpdate();
    }
    
    private void sendUpdate() {
        for (StorageUpdateListener listener : listeners) {
            listener.onStorageupdateEvent();
        }
    }
    
    
    public void addListener(StorageUpdateListener listener) {
        listeners.add(listener);
    }
    
}
