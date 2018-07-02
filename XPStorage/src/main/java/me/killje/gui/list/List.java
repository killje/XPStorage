package me.killje.gui.list;

import java.util.HashMap;
import java.util.Map;
import me.killje.gui.Exit;
import me.killje.gui.InventoryUtils;
import me.killje.util.GuiSettingsFromFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class List extends InventoryUtils {
    
    private int page = 0;
    private final Player player;
    
    public List(Player currentPlayer) {
        super(6);
        this.player = currentPlayer;
    }

    @Override
    protected void initInventory() {
        int maxItemsOnPage = 45; // 5 * 9
        
        int startIndex = page * maxItemsOnPage;
        int stopIndex = (page + 1) * maxItemsOnPage;
        
        int totalSize = initInventory(startIndex, stopIndex, maxItemsOnPage);
        
        
        if (page != 0) {
            this.addGuiElement(new PrevPage(this), 3);
        }
        
        if (totalSize > stopIndex) {
            this.addGuiElement(new NextPage(this), 5);
        }
        this.addGuiElement(new Exit(), 8);
        
        Map<String, String> replaceList = new HashMap<>();
        replaceList.put("INVENTORY_NAME", getInventoryName());
        replaceList.put("CURRENT_PAGE", (page + 1) + "");
        replaceList.put("LAST_PAGE", ((totalSize - 1)/maxItemsOnPage + 1) + "");
        
        this.setInventoryName(GuiSettingsFromFile.getText("list", replaceList));
        
    }
    
    /**
     * This is called when initializing the gui. Place in this your list code.
     * 
     * @param startIndex Start index of the current page
     * @param stopIndex End index of the current page
     * @param maxItemsOnPage Max displayed items on the page
     * @return The max items that are possible for this list
     */
    protected abstract int initInventory(int startIndex, int stopIndex, int maxItemsOnPage);
    
    /**
     * Get inventory name of the current inventory
     * 
     * @return The inventory name
     */
    protected abstract String getInventoryName();
    
    
    public void nextPage() {
        page++;
        player.openInventory(this.getInventory());
        Bukkit.getScheduler().runTask(getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
    public void previousPage() {
        page--;
        player.openInventory(this.getInventory());
        Bukkit.getScheduler().runTask(getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
}
