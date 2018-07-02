package me.killje.xpstorage.gui.characters;

import me.killje.xpstorage.XPStorage;
import me.killje.gui.Exit;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class NumberBoard extends InventoryUtils implements StorageUpdateListener {

    private final AmountStorage amountStorage;
    private final Player player;
    private final GuiElement setAmountButton;
    
    public NumberBoard(Player player, SetAmountButton setAmountButton) {
        amountStorage = new AmountStorage();
        amountStorage.addListener(this);
        this.player = player;
        this.setAmountButton = setAmountButton;
        setAmountButton.setAmountStorage(amountStorage);
    }
    
    @Override
    protected void initInventory() {
        
        this
                .setInventoryName(getInventoryName())
                .addGuiElementTransposed(new Number(1, amountStorage),1)
                .addGuiElement(new Number(2, amountStorage))
                .addGuiElement(new Number(3, amountStorage))
                .nextRow()
                .addGuiElementTransposed(new Number(4, amountStorage), 1)
                .addGuiElement(new Number(5, amountStorage))
                .addGuiElement(new Number(6, amountStorage))
                .nextRow()
                .addGuiElementTransposed(new Number(7, amountStorage), 1)
                .addGuiElement(new Number(8, amountStorage))
                .addGuiElement(new Number(9, amountStorage))
                .nextRow()
                .addGuiElementTransposed(new Number(0, amountStorage), 2)
                .addGuiElement(new Undo(amountStorage, "undo.number"), 24)
                .addGuiElement(setAmountButton)
                .addGuiElement(new Exit(), 8);
    }
    
    @Override
    public void onStorageupdateEvent() {
        player.openInventory(this.getInventory());
        
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
    protected final AmountStorage getAmountStorage() {
        return amountStorage;
    }

    @Override
    protected Plugin getInstance() {
        return XPStorage.getInstance();
    }

    
    
    protected abstract String getInventoryName();
    
}
