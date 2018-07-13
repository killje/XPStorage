package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.HeadUtil;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This is a icon representing a player from a group. Will open a inventory when
 * clicked on to edit the player
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EditPlayer implements GuiElement {

    /**
     * The player for this icon
     */
    private final UUID player;

    /**
     * The group the player is being edited from
     */
    private final AbstractGroupSign sign;

    /**
     * Creates a icon that represents a player that when clicked on can be
     * edited
     *
     * @param player The player that can be edited
     * @param sign The sign the player belongs to
     */
    public EditPlayer(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        ItemStack itemStack = HeadUtil.getPlayerHead(offlinePlayer);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + offlinePlayer.getName());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(InventoryBase currentInventoryBase, InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player playerWhoClicked = (Player) event.getWhoClicked();

        EditPlayerOptions editList = sign.getEditList(this.player, playerWhoClicked.getUniqueId());

        currentInventoryBase.openNewInventory(playerWhoClicked, editList);

    }

}
