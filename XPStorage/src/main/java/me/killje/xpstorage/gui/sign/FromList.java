package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.GuiElement;
import me.killje.xpstorage.gui.addplayer.PlayerList;
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
public class FromList implements GuiElement {
    
    private final AbstractSharedSign sign;

    public FromList(AbstractSharedSign sign) {
        this.sign = sign;
    }
    
    

    @Override
    public ItemStack getItemStack() {
        
        ItemStack itemStack;
        itemStack = new ItemStack(Material.BOOK);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Add person from player list");
        
        List<String> lore = new ArrayList<>();
        lore.add("Opens a list of all players.");
        lore.add("You can select here the player you want to add.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        //event.getView().close();
        Inventory inventory = new PlayerList(sign).getInventory();
        event.getWhoClicked().openInventory(inventory);
        inventory.setItem(0, inventory.getItem(0));
        //event.getWhoClicked().getInventory().setContents(event.getWhoClicked().getInventory().getContents());
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
