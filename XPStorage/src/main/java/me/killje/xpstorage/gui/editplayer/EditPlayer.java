package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.utils.HeadUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public class EditPlayer implements GuiElement {
    
    private final UUID player;
    private final AbstractSharedSign sign;

    public EditPlayer(UUID player, AbstractSharedSign sign) {
        this.player = player;
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        ItemStack itemStack = HeadUtils.getPlayerHead(offlinePlayer);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + offlinePlayer.getName());
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
        
        Inventory inventory = sign.getEditList(this.player, player.getUniqueId()).getInventory();
        event.getWhoClicked().openInventory(inventory);
        inventory.setItem(0, inventory.getItem(0));
        //event.getWhoClicked().getInventory().setContents(event.getWhoClicked().getInventory().getContents());
        
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), player::updateInventory);
        
    }
    
}
