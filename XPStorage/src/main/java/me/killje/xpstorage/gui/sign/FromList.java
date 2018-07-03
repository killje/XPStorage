package me.killje.xpstorage.gui.sign;

import java.util.UUID;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.addplayer.AddPlayer;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.list.PlayerList;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import me.killje.gui.list.PlayerListElementFetcher;

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
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("fromList");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        PlayerList playerList = new PlayerList(player, this);
        currentInventoryUtils.openNewInventory(player, playerList);
        
    }

    @Override
    public GuiElement getGuiElement(UUID offlinePlayer) {
        return new AddPlayer(offlinePlayer, sign);
    }
    
}
