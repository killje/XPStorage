package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.HeadUtil;
import me.killje.spigotgui.util.InventoryUtil;
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
    public ItemStack getItemStack(GuiSetting guiSettings) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        ItemStack itemStack = HeadUtil.getPlayerHead(offlinePlayer);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + offlinePlayer.getName());
        itemStack.setItemMeta(itemMeta);

        
        return itemStack;
    }

    
    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player playerWhoClicked = (Player) event.getWhoClicked();
        
        EditPlayerOptions editList = sign.getEditList(this.player, playerWhoClicked.getUniqueId());
        
        currentInventoryUtils.openNewInventory(playerWhoClicked, editList);
        
    }
    
}
