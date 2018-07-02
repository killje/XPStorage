package me.killje.xpstorage.gui.addplayer;

import java.util.UUID;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.HeadUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AddPlayer implements GuiElement {
    
    private final UUID player;
    private final AbstractSharedSign sign;
    private final String displayName;

    public AddPlayer(UUID player, AbstractXpSign sign, String displayName) {
        this.player = player;
        this.sign = (AbstractSharedSign) sign;
        this.displayName = displayName;
    }
    
    public AddPlayer(UUID player, AbstractXpSign sign) {
        this(player, sign, null);
    }
    
    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = HeadUtils.getPlayerHead(Bukkit.getOfflinePlayer(player));
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        
        String headName;
        if (displayName == null) {
            headName = Bukkit.getOfflinePlayer(player).getName();
        }
        else {
            headName = displayName;
        }
        itemMeta.setDisplayName(ChatColor.WHITE + headName);
        
        itemStack.setItemMeta(itemMeta);

        
        return itemStack;
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        
        sign.getGroup().addPlayerToGroup(player);
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }
    
}
