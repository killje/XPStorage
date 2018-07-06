package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.list.PlayerList;
import me.killje.spigotgui.list.PlayerListElementFetcher;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.permission.Permissions;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeOwnerList implements GuiElement, PlayerListElementFetcher {

    private final Player player;
    private final AbstractXpSign sign;

    public ChangeOwnerList(Player player, AbstractXpSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("changeOwner");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {

        if (!player.hasPermission(Permissions.CHANGE_OWNER.getPermission())) {
            return;
        }

        InventoryUtil inventoryUtils = new PlayerList(XPStorage.getGuiSettings(), player, this);

        currentInventoryUtils.openNewInventory(player, inventoryUtils);

    }

    @Override
    public GuiElement getGuiElement(OfflinePlayer offlinePlayer) {
        return new ChangeOwner(offlinePlayer, sign);
    }

}
