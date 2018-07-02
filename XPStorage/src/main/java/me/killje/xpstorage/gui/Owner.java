package me.killje.xpstorage.gui;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.killje.util.GuiSettingsFromFile;
import me.killje.util.HeadUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Owner implements GuiElement {

    private final AbstractXpSign xpSign;

    public Owner(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
    }
    
    @Override
    public ItemStack getItemStack() {
        OfflinePlayer player = Bukkit.getOfflinePlayer(xpSign.getOwner());
        
        Map<String, String> replacement = new HashMap<>();
        replacement.put("PLAYER_NAME", player.getName());
        replacement.put("SIGN_TYPE", xpSign.signType());
        replacement.put("OWNER_UUID", xpSign.getOwner().toString());
        
        ItemStack itemStack = HeadUtils.getPlayerHead(player);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(GuiSettingsFromFile.getText("information"));
        List<String> lore = new ArrayList<>();
        lore.add(GuiSettingsFromFile.getText("player", replacement));
        lore.add(GuiSettingsFromFile.getText("signType", replacement));
        
        if (xpSign instanceof AbstractSharedSign) {
            AbstractSharedSign sign = (AbstractSharedSign) xpSign;
            replacement.put("GROUP_UUID", sign.getGroup().getGroupUuid().toString());
            
            lore.add(GuiSettingsFromFile.getText("groupUUID", replacement));
        }
        
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
    }
    
    
}
