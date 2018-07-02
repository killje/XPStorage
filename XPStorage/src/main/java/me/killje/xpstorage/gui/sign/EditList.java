package me.killje.xpstorage.gui.sign;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.editplayer.CurrentList;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EditList implements GuiElement {
    
    private final AbstractSharedSign sign;

    public EditList(AbstractSharedSign sign) {
        this.sign = sign;
    }
    
    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("editList");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        CurrentList currentList = new CurrentList(player, sign);
        currentInventoryUtils.openNewInventory(player, currentList);
        
    }
}
