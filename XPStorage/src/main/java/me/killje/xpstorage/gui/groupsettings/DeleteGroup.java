package me.killje.xpstorage.gui.groupsettings;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DeleteGroup implements GuiElement {
    
    private final GroupSign groupSign;
    private final Player player;

    public DeleteGroup(GroupSign groupSign, Player player) {
        this.groupSign = groupSign;
        this.player = player;
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("deleteGroup");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        if (!groupSign.canDestroySign(player)) {
            return;
        }
        
        currentInventoryUtils.closeInventory(entity);
        
        groupSign.getGroup().destoryGroup(player);
        
    }
    
}
