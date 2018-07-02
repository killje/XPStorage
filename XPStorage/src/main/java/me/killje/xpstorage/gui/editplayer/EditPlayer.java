package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.HeadUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
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
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player playerWhoClicked = (Player) event.getWhoClicked();
        
        EditPlayerOptions editList = sign.getEditList(this.player, playerWhoClicked.getUniqueId());
        
        currentInventoryUtils.openNewInventory(playerWhoClicked, editList);
        
    }
    
}
