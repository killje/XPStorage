package me.killje.xpstorage.gui.editplayer;

import java.util.UUID;
import me.killje.xpstorage.gui.GuiElement;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class RemovePlayer implements GuiElement {

    private final UUID player;
    private final AbstractSharedSign sign;

    public RemovePlayer(UUID player, AbstractSharedSign sign) {
        this.player = player;
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        return createSimpleItemStack(Material.FIREBALL, ChatColor.RED + "Remove player");
    }
    
    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        sign.getGroup().removePlayerFromGroup(player);
        event.getView().close();
    }
}
