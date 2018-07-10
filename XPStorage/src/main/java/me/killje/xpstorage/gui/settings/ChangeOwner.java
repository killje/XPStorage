package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.HeadUtil;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.EnderGroupSign;
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
    public void onInventoryClickEvent(InventoryBase currentInventoryBase, InventoryClickEvent event) {
        if (sign instanceof AbstractGroupSign) {
            AbstractGroupSign abstractGroupSign = (AbstractGroupSign) sign;
            PlayerInformation.getPlayerInformation(sign.getOwner()).getGroupRights(abstractGroupSign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);
            PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroupRights(abstractGroupSign.getGroup().getGroupUuid()).removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);
            if (sign instanceof EnderGroupSign) {
                PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroupRights(abstractGroupSign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
            }
        }
        sign.setOwner(player.getUniqueId());
        sign.updateSign();
        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
