package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.PlayerSign;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class ChangeToEnder implements GuiElement{
    
    private AbstractXpSign xpSign;
    private final boolean isSelected;

    public ChangeToEnder(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
        isSelected = xpSign instanceof PlayerSign;
    }
    
    

    @Override
    public ItemStack getItemStack() {
        
        List<String> lore = new ArrayList<>();
        lore.add("Change the sign to a player bound sign.");
        lore.add("With this the XP will be");
        lore.add("available on other signs for you.");
        if (isSelected) {
            return ItemStackFromFile.getItemStack("selected.ender", ChatColor.YELLOW + "Currently selected:" + ChatColor.WHITE + " Player sign", lore);
        }
        return ItemStackFromFile.getItemStack("changeToEnder", ChatColor.WHITE + "Player sign", lore);
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
        if (!xpSign.destroySign()) {
            return;
        }
        xpSign = new PlayerSign(xpSign.getSign(), entity.getUniqueId());
        xpSign.changeSign();
        event.getView().close();
    }
    
}
