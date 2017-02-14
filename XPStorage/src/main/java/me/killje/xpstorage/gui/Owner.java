package me.killje.xpstorage.gui;

import me.killje.xpstorage.gui.guiElement.GuiElement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.list.PlayerList;
import me.killje.xpstorage.gui.list.PlayerListGuiElement;
import me.killje.xpstorage.permission.Permissions;
import me.killje.xpstorage.utils.HeadUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public class Owner implements GuiElement, PlayerListGuiElement {

    private final AbstractXpSign xpSign;
    private final Player playerWhoClicked;

    public Owner(AbstractXpSign xpSign, Player playerWhoClicked) {
        this.xpSign = xpSign;
        this.playerWhoClicked = playerWhoClicked;
    }
    
    @Override
    public ItemStack getItemStack() {
        OfflinePlayer player = Bukkit.getOfflinePlayer(xpSign.getOwner());
        ItemStack itemStack = HeadUtils.getPlayerHead(player);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Information");
        List<String> lore = new ArrayList<>();
        lore.add("Player: " + player.getName());
        lore.add("Sign type: " + xpSign.signType());
        if (xpSign instanceof AbstractSharedSign) {
            AbstractSharedSign sign = (AbstractSharedSign) xpSign;
            lore.add("Group UUID: " + sign.getGroup().getGroupUuid().toString());
        }
        lore.add("Owner UUID: " + xpSign.getOwner().toString());
        if (!playerWhoClicked.hasPermission(Permissions.CHANGE_OWNER.getPermission())) {
            lore.add("Click here to change the owner.");
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        
        Player player = (Player) event.getWhoClicked();
        if (!player.hasPermission(Permissions.CHANGE_OWNER.getPermission())) {
            return;
        }
        //event.getView().close();
        Inventory inventory = new PlayerList(player, xpSign, this).getInventory();
        event.getWhoClicked().openInventory(inventory);
        
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
        
    }

    @Override
    public GuiElement getGuiElement(UUID offlinePlayer, AbstractXpSign sign) {
        return new ChangeOwner(offlinePlayer, sign);
    }
    
    
}
