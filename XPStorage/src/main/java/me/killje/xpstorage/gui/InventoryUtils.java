package me.killje.xpstorage.gui;

import java.util.ArrayList;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.GuiElement;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Zolder
 */
public abstract class InventoryUtils implements Listener {
    
    private Inventory inventory;
    private final ArrayList<GuiElement> guiElements = new ArrayList<>();
    private int index = 0;
    private String name = null;
    private boolean isInit = true;
    private int fixedRows = 0;

    public InventoryUtils() {
    }
    
    public InventoryUtils(int rows) {
        this.fixedRows = rows;
    }
    
    protected abstract void initInventory();
    
    public Inventory getInventory() {
        initInventory();
        int realRows = guiElements.size() / 9;
        
        if (guiElements.size() % 9 != 0) {
            realRows++;
        }
        
        ItemStack[] inventoryItems = new ItemStack[realRows * 9];
        
        int i = -1;
        for (GuiElement guiElement : guiElements) {
            i++;
            if (guiElement == null) {
                continue;
            }
            inventoryItems[i] = guiElement.getItemStack();
        }
        
        int rowsToDraw = realRows;
        if (fixedRows != 0) {
            rowsToDraw = fixedRows;
        }
        
        if (name != null) {
            this.inventory = Bukkit.createInventory(null, rowsToDraw * 9, name);
        }
        else {
            this.inventory = Bukkit.createInventory(null, rowsToDraw * 9);
        }
        XPStorage.getInstance().getServer().getPluginManager().registerEvents(this, XPStorage.getInstance());
        inventory.setContents(inventoryItems);
        
        isInit = false;
        return inventory;
    }
    
    public InventoryUtils nextRow() {
        return nextRow(false);
    }
    
    public InventoryUtils nextRow(boolean preventEmptyRow) {
        if (preventEmptyRow && (index % 9) == 0) {
            return this;
        }
        index += 9 - (index % 9);
        return this;
    }
    
    public InventoryUtils addGuiElementTransposed(GuiElement guiElement, int positionsMoved) {
        return addGuiElement(guiElement, index + positionsMoved);
    }
    
    public InventoryUtils addGuiElement(GuiElement guiElement) {
        return addGuiElement(guiElement, index);
    }
    
    public InventoryUtils addGuiElement(GuiElement guiElement, int position) {
        if (position >= 54) {
            throw new ArrayIndexOutOfBoundsException("This index is to big to handel. Current postion: " + position + ". Max position: " + 54);
        }
        
        while (position > this.guiElements.size() - 1) {
            this.guiElements.add(null);
        }
        this.guiElements.set(position, guiElement);
        index = position + 1;
        return this;
    }
    
    protected InventoryUtils setInventoryName(String name) {
        this.name = name;
        return this;
    }
    
    
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (isInit) {
            return;
        }
        if (event.getInventory().equals(inventory)) {
            
            event.setCancelled(true);
        }
        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(this.inventory)) {
            return;
        }
        
        int index = event.getRawSlot();
        if (index >= guiElements.size()) {
            event.setCancelled(true);
            return;
        }
        
        GuiElement guiElement = guiElements.get(index);
        
        if (guiElement == null) {
            event.setCancelled(true);
            return;
        }
        
        guiElement.onGuiElementClickEvent(event);
        
    }
    
}
