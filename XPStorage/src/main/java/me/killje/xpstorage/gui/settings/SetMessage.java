package me.killje.xpstorage.gui.settings;

import me.killje.gui.InventoryUtils;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.utils.PlayerInformation;
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
    public ItemStack getItemStack() {
        if (playerInformation.isMessage()) {
            return GuiSettingsFromFile.getItemStack("message.remove");
        } else {
            return GuiSettingsFromFile.getItemStack("message.add");
        }
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        boolean currentGetMessage = playerInformation.isMessage();
        playerInformation.isMessage(!currentGetMessage);
        
        if (currentGetMessage) {
            player.sendMessage(GuiSettingsFromFile.getText("message.remove"));
        } else {
            player.sendMessage(GuiSettingsFromFile.getText("message.add"));
        }
        
        currentInventoryUtils.closeInventory(player);
    }

}
