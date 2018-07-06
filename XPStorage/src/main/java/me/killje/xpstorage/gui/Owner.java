package me.killje.xpstorage.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.HeadUtil;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Owner implements GuiElement {

    private final AbstractXpSign xpSign;
    private final Player playerViewing;

    public Owner(AbstractXpSign xpSign, Player playerViewing) {
        this.xpSign = xpSign;
        this.playerViewing = playerViewing;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(xpSign.getOwner());

        Map<String, String> replacement = new HashMap<>();
        replacement.put("PLAYER_NAME", player.getName());
        replacement.put("SIGN_TYPE", xpSign.signType());
        replacement.put("OWNER_UUID", xpSign.getOwner().toString());

        ItemStack itemStack = HeadUtil.getPlayerHead(player);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(guiSettings.getText("information"));
        List<String> lore = new ArrayList<>();
        lore.add(guiSettings.getText("player", replacement));
        lore.add(guiSettings.getText("signType", replacement));

        if (playerViewing.hasPermission("xpStorage.showuuid")) {

            lore.add(guiSettings.getText("ownerUUID", replacement));

        }

        if (xpSign instanceof AbstractSharedSign) {

            AbstractSharedSign sign = (AbstractSharedSign) xpSign;

            replacement.put("GROUP_NAME", sign.getGroup().getGroupName());
            replacement.put("GROUP_UUID", sign.getGroup().getGroupUuid().toString());

            lore.add(guiSettings.getText("groupNameOwner", replacement));

            if (playerViewing.hasPermission("xpStorage.showuuid")) {
                lore.add(guiSettings.getText("groupUUID", replacement));
            }

        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        if (!humanEntity.hasPermission("xpStorage.showuuid")) {
            return;
        }
        humanEntity.sendMessage("Owner: " + xpSign.getOwner().toString());
        if (xpSign instanceof AbstractSharedSign) {
            AbstractSharedSign sign = (AbstractSharedSign) xpSign;
            humanEntity.sendMessage("Group: " + sign.getGroup().getGroupUuid().toString());
        }
    }

}
