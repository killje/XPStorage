package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.list.PlayerList;
import me.killje.spigotgui.list.PlayerListElementFetcher;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.permission.Permission;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon and inventory for changing the owner of a sign
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeOwnerList implements GuiElement, PlayerListElementFetcher {

    /**
     * The player editing the sign
     */
    private final Player player;
    /**
     * The sign being edited
     */
    private final AbstractXpSign sign;

    /**
     * Icon and inventory for changing the owner of a sign
     *
     * @param player The player editing the sign
     * @param sign The sign being edited
     */
    public ChangeOwnerList(Player player, AbstractXpSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("changeOwner");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        if (!Permission.CHANGE_OWNER.hasPermission(player)) {
            return;
        }

        InventoryBase inventoryBase
                = new PlayerList(XPStorage.getGuiSettings(), player, this);

        currentInventoryBase.openNewInventory(player, inventoryBase);

    }

    @Override
    /**
     * {@inheritDoc}
     */
    public GuiElement getGuiElement(OfflinePlayer offlinePlayer) {
        return new ChangeOwner(offlinePlayer, sign);
    }

}
