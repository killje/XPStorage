package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class SetNewOwner implements GuiElement{

    private final UUID player;
    private final AbstractSharedSign sign;

    public SetNewOwner(UUID player, AbstractSharedSign sign) {
        this.player = player;
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add("Sets the player as the new owner");
        return ItemStackFromFile.getItemStack("setNewOwner", ChatColor.GOLD + "Set owner", lore);
    }
    
    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        PlayerInformation.getPlayerInformation(sign.getOwner()).getGroupRights(sign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);
        if (sign instanceof GroupSign) {
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
        }
        sign.setOwner(player);
            
        event.getView().close();
    }
    
}
