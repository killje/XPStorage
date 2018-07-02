package me.killje.xpstorage.gui.sign;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GetAllXp implements GuiElement{
    
    private final AbstractXpSign xpSign;

    public GetAllXp(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
    }
    
    

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("getAllXP");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        xpSign.allXpOut((Player) entity);
        currentInventoryUtils.closeInventory(entity);
    }
    
}
