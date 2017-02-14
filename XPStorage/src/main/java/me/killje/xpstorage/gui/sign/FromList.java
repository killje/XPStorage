package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.addplayer.AddPlayer;
import me.killje.xpstorage.gui.list.PlayerList;
import me.killje.xpstorage.gui.list.PlayerListGuiElement;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
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
public class FromList implements GuiElement, PlayerListGuiElement {
    
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
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = new PlayerList(player, sign, this).getInventory();
        event.getWhoClicked().openInventory(inventory);
        
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
        
    }

    @Override
    public GuiElement getGuiElement(UUID offlinePlayer, AbstractXpSign sign) {
        return new AddPlayer(offlinePlayer, sign);
    }
    
}
