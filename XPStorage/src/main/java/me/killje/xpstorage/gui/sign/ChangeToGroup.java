package me.killje.xpstorage.gui.sign;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.gui.choosegroup.ChooseGroup;
import me.killje.xpstorage.gui.list.GroupList;
import me.killje.xpstorage.gui.list.GroupListGuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.EnderGroupSign;
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
        this.isSelected = xpSign instanceof EnderGroupSign;
        this.fromGroup = fromGroup;
        this.player = player;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        if (fromGroup) {
            return guiSettings.getItemStack("diffrentGroup");
        }
        if (isSelected) {
            return guiSettings.getItemStack("selected.enderGroup");
        }
        return guiSettings.getItemStack("changeTo.enderGroup");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        if (isSelected && !fromGroup) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)) {
            event.setCancelled(true);
            return;
        }

        InventoryBase groupList = new GroupList(player, this, xpSign);

        currentinventoryBase.openNewInventory(player, groupList);
    }

    @Override
    public GuiElement getGuiElement(UUID groupUUID, AbstractXpSign sign) {
        return new ChooseGroup(groupUUID, sign, player);
    }

}
