package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.ChatColor;
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
        List<String> lore = new ArrayList<>();
        lore.add("Removes the right from the player");
        lore.add("to create and destroy sign");
        lore.add("for this group");
        return ItemStackFromFile.getItemStack("removePlayerCreateRights", ChatColor.BLUE + "Remove right to create/destroy rights", lore);
    }
    
    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).removeRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
        event.getView().close();
    }
    
}
