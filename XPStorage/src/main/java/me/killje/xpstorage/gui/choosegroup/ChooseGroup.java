package me.killje.xpstorage.gui.choosegroup;

import java.util.UUID;
import me.killje.xpstorage.group.Group;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChooseGroup implements GuiElement{
    
    private final UUID groupId;
    private AbstractXpSign xpSign;
    private final Player player;

    public ChooseGroup(UUID groupId, AbstractXpSign xpSign, Player player) {
        this.groupId = groupId;
        this.xpSign = xpSign;
        this.player = player;
    }
    
    @Override
    public ItemStack getItemStack() {
        Group group = Group.getGroupFromUUID(groupId);
        return group.getGroupIcon();
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        if (!xpSign.destroySign(player)) {
            return;
        }
        xpSign = new GroupSign(xpSign.getSign(), groupId);
        xpSign.changeSign();
        
        currentInventoryUtils.closeInventory(entity);
    }
    
}
