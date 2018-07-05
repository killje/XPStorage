package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.util.PlayerInformation;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetMessage implements GuiElement {

    private final Player player;
    private final PlayerInformation playerInformation;

    public SetMessage(Player player) {
        this.player = player;
        playerInformation = PlayerInformation.getPlayerInformation(player.getUniqueId());
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        if (playerInformation.isMessage()) {
            return guiSettings.getItemStack("message.remove");
        } else {
            return guiSettings.getItemStack("message.add");
        }
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        boolean currentGetMessage = playerInformation.isMessage();
        playerInformation.isMessage(!currentGetMessage);
        
        if (currentGetMessage) {
            player.sendMessage(currentInventoryUtils.getGuiSettings().getText("message.remove"));
        } else {
            player.sendMessage(currentInventoryUtils.getGuiSettings().getText("message.add"));
        }
        
        currentInventoryUtils.closeInventory(player);
    }

}
