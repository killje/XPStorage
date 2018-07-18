package me.killje.xpstorage.gui.sign;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.list.PlayerList;
import me.killje.spigotgui.list.PlayerListElementFetcher;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.gui.addplayer.AddPlayer;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Adds a icon for opening a menu of players to add to a group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class FromList implements GuiElement, PlayerListElementFetcher {

    /**
     * The group sign that opened this list
     */
    private final AbstractGroupSign sign;

    /**
     * A icon for adding players to the group with a player list
     *
     * @param sign The group sign that opened this list
     */
    public FromList(AbstractGroupSign sign) {
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public GuiElement getGuiElement(OfflinePlayer offlinePlayer) {
        return new AddPlayer(offlinePlayer, sign);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("fromList");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        PlayerList playerList = new PlayerList(
                currentInventoryBase.getGuiSettings(), player, this
        );

        currentInventoryBase.openNewInventory(player, playerList);

    }

}
