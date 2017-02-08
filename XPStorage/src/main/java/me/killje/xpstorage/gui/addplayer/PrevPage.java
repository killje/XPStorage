package me.killje.xpstorage.gui.addplayer;

import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.specialheads.LeftArrow;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author Zolder
 */
class PrevPage extends LeftArrow {
    
    private final int page;
    private final AbstractSharedSign sign;
    
    public PrevPage(int currentPage, AbstractSharedSign sign) {
        super(ChatColor.WHITE + "Previous page");
        page = currentPage - 1;
        this.sign = sign;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        
        Inventory inventory = new PlayerList(sign, page).getInventory();
        
        event.getWhoClicked().openInventory(inventory);
        inventory.setItem(0, inventory.getItem(0));
        
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        
        Player player = (Player) event.getWhoClicked();
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
}
