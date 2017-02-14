package me.killje.xpstorage.gui.choosegroup;

import java.util.UUID;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class ChooseGroup implements GuiElement{
    
    private final UUID groupId;
    private AbstractXpSign xpSign;

    public ChooseGroup(UUID groupId, AbstractXpSign xpSign) {
        this.groupId = groupId;
        this.xpSign = xpSign;
    }
    
    @Override
    public ItemStack getItemStack() {
        
        return ItemStackFromFile.getItemStack("chooseGroup", Group.getGroupFromUUID(groupId).getGroupName());
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        if (!xpSign.destroySign()) {
            return;
        }
        xpSign = new GroupSign(xpSign.getSign(), groupId);
        xpSign.changeSign();
        event.getView().close();
    }
    
}
