package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.editplayer.CurrentList;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public class EditList implements GuiElement {
    
    private final AbstractSharedSign sign;

    public EditList(AbstractSharedSign sign) {
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        
        ItemStack itemStack = new ItemStack(Material.BOOK_AND_QUILL);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Edit person from shared list");
        
        List<String> lore = new ArrayList<>();
        lore.add("Opens a list of all shared players.");
        lore.add("You can edit the players here.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Inventory inventory = new CurrentList(player, sign).getInventory();
        event.getWhoClicked().openInventory(inventory);
        
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
        
    }
}
