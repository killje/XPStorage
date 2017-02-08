package me.killje.xpstorage.gui.editplayer;

import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.specialheads.RightArrow;
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
class NextPage extends RightArrow {
    
    private final int page;
    private final AbstractSharedSign sign;
    
    public NextPage(int currentPage, AbstractSharedSign sign) {
        super(ChatColor.WHITE + "Next page");
        page = currentPage + 1;
        this.sign = sign;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        
        Inventory inventory = new CurrentList(sign, page).getInventory();
        
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
