package me.killje.xpstorage.gui.addplayer;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.HeadUtil;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AddPlayer implements GuiElement {

    private final OfflinePlayer player;
    private final AbstractSharedSign sign;
    private final String displayName;

    public AddPlayer(OfflinePlayer player, AbstractXpSign sign, String displayName) {
        this.player = player;
        this.sign = (AbstractSharedSign) sign;
        this.displayName = displayName;
    }

    public AddPlayer(OfflinePlayer player, AbstractXpSign sign) {
        this(player, sign, null);
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        ItemStack itemStack = HeadUtil.getPlayerHead(player);

        ItemMeta itemMeta = itemStack.getItemMeta();

        String headName;
        if (displayName == null) {
            headName = player.getName();
        } else {
            headName = displayName;
        }
        itemMeta.setDisplayName(ChatColor.WHITE + headName);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentInventoryBase, InventoryClickEvent event) {

        sign.getGroup().addPlayerToGroup(player.getUniqueId());
        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
