package me.killje.xpstorage.gui;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.utils.HeadUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public class Owner implements GuiElement {

    private AbstractXpSign xpSign;

    public Owner(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
    }
    
    @Override
    public ItemStack getItemStack() {
        String playerName = Bukkit.getOfflinePlayer(xpSign.getOwner()).getName();
        ItemStack itemStack = HeadUtils.getPlayerHead(playerName);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Information");
        List<String> lore = new ArrayList<>();
        lore.add("Player: " + playerName);
        lore.add("Sign type: " + xpSign.signType());
        if (xpSign instanceof AbstractSharedSign) {
            AbstractSharedSign sign = (AbstractSharedSign) xpSign;
            lore.add("Group UUID: " + sign.getGroup().getGroupUuid().toString());
        }
        lore.add("Owner UUID: " + xpSign.getOwner().toString());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
    }
    
    
}
