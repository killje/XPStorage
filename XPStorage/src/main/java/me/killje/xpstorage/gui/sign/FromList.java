package me.killje.xpstorage.gui.sign;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.list.PlayerList;
import me.killje.spigotgui.list.PlayerListElementFetcher;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.gui.addplayer.AddPlayer;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class FromList implements GuiElement, PlayerListElementFetcher {
    
    private final AbstractSharedSign sign;

    public FromList(AbstractSharedSign sign) {
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("fromList");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        
        Player player = (Player) event.getWhoClicked();
        PlayerList playerList = new PlayerList(currentInventoryUtils.getGuiSettings(), player, this);
        currentInventoryUtils.openNewInventory(player, playerList);
        
    }

    @Override
    public GuiElement getGuiElement(OfflinePlayer offlinePlayer) {
        return new AddPlayer(offlinePlayer, sign);
    }
    
}
