/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.killje.xpstorage.gui;

import me.killje.xpstorage.gui.guiElement.GuiElement;
import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.utils.HeadUtils;
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
    public void onGuiElementClickEvent(InventoryClickEvent event) {
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
        event.getView().close();
    }
    
}
