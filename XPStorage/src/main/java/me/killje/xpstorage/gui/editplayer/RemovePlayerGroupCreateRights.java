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
 * @author Zolder
 */
public class RemovePlayerGroupCreateRights implements GuiElement{

    private final UUID player;
    private final AbstractSharedSign sign;

    public RemovePlayerGroupCreateRights(UUID player, AbstractSharedSign sign) {
        this.player = player;
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("removePlayerCreateRights");
    }
    
    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).removeRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
        currentInventoryUtils.closeInventory(event.getWhoClicked());
    }
    
}
