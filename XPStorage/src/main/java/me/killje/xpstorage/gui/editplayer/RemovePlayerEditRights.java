package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.GuiElement;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public class RemovePlayerEditRights implements GuiElement{

    private final UUID player;
    private final AbstractSharedSign sign;

    public RemovePlayerEditRights(UUID player, AbstractSharedSign sign) {
        this.player = player;
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add("Removes the right from the player");
        lore.add("to add and remove players");
        lore.add("to this shared sign");
        return createSimpleItemStack(Material.REDSTONE_TORCH_ON, ChatColor.BLUE + "Remove add/remove rights", lore);
    }
    
    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        PlayerInformation.getPlayerInformation(player).getGroupRights(sign.getGroup().getGroupUuid()).removeRight(GroupRights.Right.CAN_EDIT_PLAYERS);
        event.getView().close();
    }
    
}
