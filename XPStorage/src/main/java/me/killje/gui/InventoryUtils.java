package me.killje.gui;

import java.util.ArrayList;
import me.killje.gui.guiElement.GuiElement;
import me.killje.gui.guiElement.InventoryElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class InventoryUtils implements Listener {

    public enum InventoryUtilsType {

        CUSTOM(9, 54, false, InventoryType.CHEST),
        HOPPER(5, 5, true, InventoryType.HOPPER);

        private final int rowSize;
        private final int MaxInventorySize;
        private final boolean fixedRows;
        private final InventoryType inventoryType;

        private InventoryUtilsType(int rowSize, int maxInventorySize, boolean fixedRows, InventoryType inventoryType) {
            this.rowSize = rowSize;
            this.MaxInventorySize = maxInventorySize;
            this.fixedRows = fixedRows;
            this.inventoryType = inventoryType;
        }

        public int getMaxInventorySize() {
            return MaxInventorySize;
        }

        public int getRowSize() {
            return rowSize;
        }

        public boolean isFixedRows() {
            return fixedRows;
        }

        public InventoryType getInventoryType() {
            return inventoryType;
        }

    }

    private Inventory inventory;
    private final InventoryUtilsType inventoryUtilsType;
    private final ArrayList<GuiElement> guiElements = new ArrayList<>();
    private final ArrayList<InventoryElement> inventoryElements = new ArrayList<>();
    private int index = 0;
    private String name = null;
    private boolean isInit = true;
    private int fixedRows = 0;
    private boolean ignorePlayerInventory = true;

    public InventoryUtils() {
        this.inventoryUtilsType = InventoryUtilsType.CUSTOM;
    }

    public InventoryUtils(int rows) {
        this.fixedRows = rows;
        this.inventoryUtilsType = InventoryUtilsType.CUSTOM;
    }

    public InventoryUtils(InventoryUtilsType inventoryUtilsType) {
        this.inventoryUtilsType = inventoryUtilsType;
        if (inventoryUtilsType.isFixedRows()) {
            this.fixedRows = inventoryUtilsType.MaxInventorySize;
        }
    }

    protected abstract void initInventory();
    protected abstract Plugin getInstance();

    public Inventory getInventory() {
        guiElements.clear();
        inventoryElements.clear();
        initInventory();
        int realRows = guiElements.size() / inventoryUtilsType.getRowSize();

        if (guiElements.size() % inventoryUtilsType.getRowSize() != 0) {
            realRows++;
        }

        ItemStack[] inventoryItems = new ItemStack[realRows * inventoryUtilsType.getRowSize()];

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
            if (inventoryUtilsType.isFixedRows()) {
                this.inventory = Bukkit.createInventory(null, inventoryUtilsType.getInventoryType(), name);
            } else {
                this.inventory = Bukkit.createInventory(null, rowsToDraw * inventoryUtilsType.getRowSize(), name);
            }
        } else {
            if (inventoryUtilsType.isFixedRows()) {
                this.inventory = Bukkit.createInventory(null, inventoryUtilsType.getInventoryType());
            } else {
                this.inventory = Bukkit.createInventory(null, rowsToDraw * inventoryUtilsType.getRowSize());
            }
        }

        inventory.setContents(inventoryItems);
        
        isInit = false;
        index = 0;
        return inventory;
    }

    public InventoryUtils isIgnorePlayerInventory(boolean ignorePlayerInventory) {
        this.ignorePlayerInventory = ignorePlayerInventory;
        return this;
    }

    public InventoryUtils nextRow() {
        return nextRow(false);
    }

    public InventoryUtils nextRow(boolean preventEmptyRow) {
        if (preventEmptyRow && (index % inventoryUtilsType.getRowSize()) == 0) {
            return this;
        }
        index += inventoryUtilsType.getRowSize() - (index % inventoryUtilsType.getRowSize());
        return this;
    }

    public InventoryUtils addGuiElementTransposed(GuiElement guiElement, int positionsMoved) {
        return addGuiElement(guiElement, index + positionsMoved);
    }

    public InventoryUtils addGuiElement(GuiElement guiElement) {
        return addGuiElement(guiElement, index);
    }

    public InventoryUtils addGuiElement(GuiElement guiElement, int position) {
        if (position >= inventoryUtilsType.getMaxInventorySize()) {
            throw new ArrayIndexOutOfBoundsException("This index is to big to handel. Current postion: " + position + ". Max position: " + inventoryUtilsType.getMaxInventorySize());
        }

        while (position > this.guiElements.size() - 1) {
            this.guiElements.add(null);
            this.inventoryElements.add(null);
        }
        this.guiElements.set(position, guiElement);
        this.inventoryElements.set(position, guiElement);

        index = position + 1;
        return this;
    }

    public InventoryUtils addInventoryElementTransposed(InventoryElement inventoryElement, int positionsMoved) {
        return addInventoryElement(inventoryElement, index + positionsMoved);
    }

    public InventoryUtils addInventoryElement(InventoryElement inventoryElement) {
        return addInventoryElement(inventoryElement, index);
    }

    public InventoryUtils addInventoryElement(InventoryElement inventoryElement, int position) {
        if (position >= inventoryUtilsType.getMaxInventorySize()) {
            throw new ArrayIndexOutOfBoundsException("This index is to big to handel. Current postion: " + position + ". Max position: " + inventoryUtilsType.getMaxInventorySize());
        }

        while (position > this.guiElements.size() - 1) {
            this.guiElements.add(null);
            this.inventoryElements.add(null);
        }
        this.guiElements.set(position, null);
        this.inventoryElements.set(position, inventoryElement);

        index = position + 1;
        return this;
    }

    protected InventoryUtils setInventoryName(String name) {
        this.name = name;
        return this;
    }

    public void attachListener() {
        getInstance().getServer().getPluginManager().registerEvents(this, getInstance());
    }

    public void closeInventory(HumanEntity humanEntity) {
        closeInventory(humanEntity, false);
    }
    
    public void closeInventory(HumanEntity humanEntity, boolean isClosed) {
        for (RegisteredListener registeredListener : InventoryClickEvent.getHandlerList().getRegisteredListeners()) {
            
            if (registeredListener.getListener().equals(this)) {
                InventoryClickEvent.getHandlerList().unregister(registeredListener);
                InventoryDragEvent.getHandlerList().unregister(registeredListener);
            }
        }
        
        InventoryClickEvent.getHandlerList().unregister(this);
        if (!isClosed) {
            humanEntity.closeInventory();
        }
        if (humanEntity instanceof Player) {
            ((Player) humanEntity).updateInventory();
        }
        isInit = false;
    }

    public void openNewInventory(HumanEntity humanEntity, InventoryUtils inventoryUtils) {
        closeInventory(humanEntity);

        Bukkit.getScheduler().runTask(getInstance(), new Runnable() {
            @Override
            public void run() {
                humanEntity.openInventory(inventoryUtils.getInventory());
                inventoryUtils.attachListener();
            }
        });

    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            closeInventory(event.getPlayer(), true);
        }
    }
    
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryDragEvent(InventoryDragEvent event) {
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        
        if (isInit) {
            return;
        }
        
        if (!event.getInventory().equals(inventory)) {
            return;
        }

        event.setCancelled(true);
        
        if (!ignorePlayerInventory && event.getClickedInventory() != null && event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
            
            if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                for (int i = 0; i < inventoryElements.size(); i++) {
                    InventoryElement inventoryElement = inventoryElements.get(i);
                    if (inventoryElement == null) {
                        continue;
                    }
                    if (guiElements.get(i) != null) {
                        continue;
                    }
                    inventoryElement.onInventoryClickEvent(this, event);
                }
            } else {
                event.setCancelled(false);
            }
            return;
        }

        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(inventory)) {
            return;
        }
        
        int index = event.getRawSlot();
        if (index >= inventoryElements.size()) {
            return;
        }

        InventoryElement inventoryElement = inventoryElements.get(index);

        if (inventoryElement == null) {
            return;
        }

        inventoryElement.onInventoryClickEvent(this, event);

    }

}
