package me.killje.xpstorage.gui.choosegroup;

import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.specialheads.RightArrow;
import me.killje.xpstorage.xpsign.AbstractXpSign;
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
    private final AbstractXpSign sign;
    
    public NextPage(int currentPage, AbstractXpSign sign) {
        super(ChatColor.WHITE + "Next page");
        page = currentPage + 1;
        this.sign = sign;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        
        if (!(event.getWhoClicked() instanceof Player)){
            event.setCancelled(true);
            return;
        }
        
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = new GroupList(player, sign, page).getInventory();
        
        event.getWhoClicked().openInventory(inventory);
        inventory.setItem(0, inventory.getItem(0));
        
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
}
