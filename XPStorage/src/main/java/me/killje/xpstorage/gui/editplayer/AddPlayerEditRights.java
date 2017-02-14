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
public class AddPlayerEditRights implements GuiElement{

    private final UUID player;
    private final AbstractSharedSign sign;

    public AddPlayerEditRights(UUID player, AbstractSharedSign sign) {
        this.player = player;
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        
        List<String> lore = new ArrayList<>();
        lore.add("Gives the player, the right");
        lore.add("to add and remove players");
        lore.add("to this shared sign");
        
        return ItemStackFromFile.getItemStack("addPlayerEditRights", ChatColor.GREEN + "Give add/remove rights", lore);
    }
    
    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).addRight(GroupRights.Right.CAN_EDIT_PLAYERS);
        event.getView().close();
    }
    
}
