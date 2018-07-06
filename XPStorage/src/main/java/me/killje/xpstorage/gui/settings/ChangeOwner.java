package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.HeadUtil;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
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

    private final OfflinePlayer player;
    private final AbstractXpSign sign;

    public ChangeOwner(OfflinePlayer player, AbstractXpSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        ItemStack itemStack = HeadUtil.getPlayerHead(player);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + player.getName());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        if (sign instanceof AbstractSharedSign) {
            AbstractSharedSign abstractSharedSign = (AbstractSharedSign) sign;
            PlayerInformation.getPlayerInformation(sign.getOwner()).getGroupRights(abstractSharedSign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);
            PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroupRights(abstractSharedSign.getGroup().getGroupUuid()).removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);
            if (sign instanceof GroupSign) {
                PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroupRights(abstractSharedSign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
            }
        }
        sign.setOwner(player.getUniqueId());
        sign.updateSign();
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }

}
