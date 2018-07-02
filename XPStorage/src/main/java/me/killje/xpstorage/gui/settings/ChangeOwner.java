package me.killje.xpstorage.gui.settings;

import me.killje.gui.guiElement.GuiElement;
import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.gui.InventoryUtils;
import me.killje.util.HeadUtils;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeOwner implements GuiElement {

    private final UUID player;
    private final AbstractXpSign sign;

    public ChangeOwner(UUID player, AbstractXpSign sign) {
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
        if (sign instanceof AbstractSharedSign) {
            AbstractSharedSign abstractSharedSign = (AbstractSharedSign) sign;
            PlayerInformation.getPlayerInformation(sign.getOwner()).getGroupRights(abstractSharedSign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);
            PlayerInformation.getPlayerInformation(player).getGroupRights(abstractSharedSign.getGroup().getGroupUuid()).removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);
            if (sign instanceof GroupSign) {
                PlayerInformation.getPlayerInformation(player).getGroupRights(abstractSharedSign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
            }
        }
        sign.setOwner(player);
        sign.updateSign();
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }
    
}
