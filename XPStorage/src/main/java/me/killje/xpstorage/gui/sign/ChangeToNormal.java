package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.gui.GuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.NormalSign;
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
public class ChangeToNormal implements GuiElement{
    
    private AbstractXpSign xpSign;
    private final boolean isSelected;

    public ChangeToNormal(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
        isSelected = xpSign instanceof NormalSign;
    }
    
    @Override
    public ItemStack getItemStack() {
        
        List<String> lore = new ArrayList<>();
        lore.add("Change the sign to a location bound sign.");
        lore.add("The XP on this sign will only");
        lore.add("be available on this location for you.");
        if (isSelected) {
            return createSimpleItemStack(Material.STAINED_GLASS_PANE, ChatColor.YELLOW + "Currently selected:" + ChatColor.WHITE + " Location sign", lore);
        }
        return createSimpleItemStack(Material.CHEST, ChatColor.WHITE + "Location sign", lore);
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
        xpSign = new NormalSign(xpSign.getSign(), entity.getUniqueId());
        xpSign.changeSign();
        event.getView().close();
    }
    
}
