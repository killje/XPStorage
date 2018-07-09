package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class AddPlayerGroupCreateRights implements GuiElement {

    private final UUID player;
    private final AbstractGroupSign sign;

    public AddPlayerGroupCreateRights(UUID player, AbstractGroupSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("addPlayerCreateRights");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentInventoryBase, InventoryClickEvent event) {
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
        currentInventoryBase.closeInventory(event.getWhoClicked());
    }

}
