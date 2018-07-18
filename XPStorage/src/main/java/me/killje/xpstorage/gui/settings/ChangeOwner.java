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
 * Icon for changing the owner of the sign
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeOwner implements GuiElement {

    /**
     * The player editing the sign
     */
    private final OfflinePlayer player;
    /**
     * The sign being edited
     */
    private final AbstractXpSign sign;

    /**
     * Icon for selecting a new owner
     *
     * @param player The player editing the sign
     * @param sign   The sign being edited
     */
    public ChangeOwner(OfflinePlayer player, AbstractXpSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        ItemStack itemStack = HeadUtil.getPlayerHead(player);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + player.getName());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        if (sign instanceof AbstractGroupSign) {

            AbstractGroupSign abstractGroupSign = (AbstractGroupSign) sign;

            PlayerInformation.getPlayerInformation(sign.getOwner())
                    .getGroupRights(abstractGroupSign.getGroup().getGroupUuid())
                    .addRight(GroupRights.Right.CAN_EDIT_PLAYERS);

            PlayerInformation.getPlayerInformation(player.getUniqueId())
                    .getGroupRights(abstractGroupSign.getGroup().getGroupUuid())
                    .removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);

            if (sign instanceof EnderGroupSign) {

                PlayerInformation.getPlayerInformation(player.getUniqueId())
                        .getGroupRights(
                                abstractGroupSign.getGroup().getGroupUuid()
                        ).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);

            }
        }
        sign.setOwner(player.getUniqueId());
        sign.updateSign();
        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
