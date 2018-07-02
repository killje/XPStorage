package me.killje.xpstorage.gui.sign;

import java.util.UUID;
import me.killje.gui.InventoryUtils;
import me.killje.xpstorage.gui.choosegroup.ChooseGroup;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.gui.list.GroupList;
import me.killje.xpstorage.gui.list.GroupListGuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeToGroup implements GuiElement, GroupListGuiElement {
    
    private AbstractXpSign xpSign;
    private final boolean isSelected;
    private final boolean fromGroup;
    private final Player player;

    public ChangeToGroup(Player player, AbstractXpSign xpSign) {
        this(player, xpSign, false);
    }
    
    public ChangeToGroup(Player player, AbstractXpSign xpSign, boolean fromGroup) {
        this.xpSign = xpSign;
        this.isSelected = xpSign instanceof GroupSign;
        this.fromGroup = fromGroup;
        this.player = player;
    }
    
    @Override
    public ItemStack getItemStack() {
        if (fromGroup) {
            return GuiSettingsFromFile.getItemStack("diffrentGroup");
        }
        if (isSelected) {
            return GuiSettingsFromFile.getItemStack("selected.group");
        }
        return GuiSettingsFromFile.getItemStack("changeTo.group");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        if (isSelected && !fromGroup) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)){
            event.setCancelled(true);
            return;
        }
        
        InventoryUtils groupList = new GroupList(player, this, xpSign);
        
        currentInventoryUtils.openNewInventory(player, groupList);
    }
    
    @Override
    public GuiElement getGuiElement(UUID groupUUID, AbstractXpSign sign) {
        return new ChooseGroup(groupUUID, sign, player);
    }
    
}
