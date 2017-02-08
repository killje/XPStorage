package me.killje.xpstorage.gui.addplayer;

import me.killje.xpstorage.gui.GuiElement;
import me.killje.xpstorage.utils.HeadUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public class AddPlayer implements GuiElement {
    
    private final OfflinePlayer player;
    private final AbstractSharedSign sign;
    private final String displayName;

    public AddPlayer(OfflinePlayer player, AbstractSharedSign sign, String displayName) {
        this.player = player;
        this.sign = sign;
        this.displayName = displayName;
    }
    
    public AddPlayer(OfflinePlayer player, AbstractSharedSign sign) {
        this(player, sign, null);
    }
    
    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = HeadUtils.getPlayerHead(player);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (displayName == null) {
            itemMeta.setDisplayName(ChatColor.WHITE + player.getName());
        }
        else {
            itemMeta.setDisplayName(ChatColor.WHITE + displayName);
        }
        itemStack.setItemMeta(itemMeta);

        
        return itemStack;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        
        sign.getGroup().addPlayerToGroup(player.getUniqueId());
        event.getView().close();
    }
    
}
