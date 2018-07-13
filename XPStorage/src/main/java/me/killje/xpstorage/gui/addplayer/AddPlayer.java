package me.killje.xpstorage.gui.addplayer;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.HeadUtil;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Icon for selecting a player to be added to a group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AddPlayer implements GuiElement {

    /**
     * The player this icon represents
     */
    private final OfflinePlayer player;

    /**
     * The group sign that this player will be added to
     */
    private final AbstractGroupSign sign;

    /**
     * Create a new icon element for adding a player to a group
     *
     * @param player The player this icon represents
     * @param sign The sign the player will be added to when clicked on
     */
    public AddPlayer(OfflinePlayer player, AbstractXpSign sign) {
        this.player = player;
        this.sign = (AbstractGroupSign) sign;
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
        sign.getGroup().addPlayerToGroup(player);
        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
