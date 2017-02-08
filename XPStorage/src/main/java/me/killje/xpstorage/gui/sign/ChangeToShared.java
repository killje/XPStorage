package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.gui.GuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.SharedSign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class ChangeToShared implements GuiElement{
    
    private AbstractXpSign xpSign;
    private final boolean isSelected;

    public ChangeToShared(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
        isSelected = xpSign instanceof SharedSign;
    }
    
    

    @Override
    public ItemStack getItemStack() {
        
        List<String> lore = new ArrayList<>();
        lore.add("Change the sign to a shared sign.");
        lore.add("With this the XP will be");
        lore.add("available to you and people you share it with.");
        if (isSelected) {
            return createSimpleItemStack(Material.STAINED_GLASS_PANE, ChatColor.YELLOW + "Currently selected:" + ChatColor.WHITE + " Shared sign", lore);
        }
        return createSimpleItemStack(Material.BEACON, ChatColor.WHITE + "Shared sign", lore);
        
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        if (isSelected) {
            return;
        }
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        if (xpSign.destroySign()) {
            return;
        }
        xpSign = new SharedSign(xpSign.getSign(), entity.getUniqueId());
        xpSign.changeSign();
        event.getView().close();
    }
    
}
