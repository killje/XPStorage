package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AddPlayerEditRights implements GuiElement{

    private final UUID player;
    private final AbstractSharedSign sign;

    public AddPlayerEditRights(UUID player, AbstractSharedSign sign) {
        this.player = player;
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("addPlayerEditRights");
    }
    
    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }
    
}
