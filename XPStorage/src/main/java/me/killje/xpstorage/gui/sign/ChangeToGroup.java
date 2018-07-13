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
 * Change the sign to a ender group sign
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeToGroup implements GuiElement, GroupListGuiElement {

    /**
     * The sign being changed
     */
    private AbstractXpSign xpSign;
    /**
     * Check if this option is already selected
     */
    private final boolean isSelected;
    /**
     * Check if this is changed from the group elements, not the default row of
     * items
     */
    private final boolean fromGroup;
    /**
     * The player editing the sign
     */
    private final Player player;

    /**
     * Icon for changing the sign to a ender group sign This will assume it is
     * not change form a group list and will not be selectable when the sign is
     * already of this type
     *
     * @param player The player editing the sign
     * @param xpSign The sign being edited
     */
    public ChangeToGroup(Player player, AbstractXpSign xpSign) {
        this(player, xpSign, false);
    }

    /**
     * Icon for changing the sign to a different Ender group sign.
     *
     * @param player The player editing the sign
     * @param xpSign The sign being edited
     * @param fromGroup When true this can be selected even if the sign is
     * already of a ender group type
     */
    public ChangeToGroup(Player player, AbstractXpSign xpSign, boolean fromGroup) {
        this.xpSign = xpSign;
        this.isSelected = xpSign instanceof EnderGroupSign;
        this.fromGroup = fromGroup;
        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
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
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        if (isSelected && !fromGroup) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)) {
            event.setCancelled(true);
            return;
        }

        InventoryBase groupList = new GroupList(player, this, xpSign);

        currentInventoryBase.openNewInventory(player, groupList);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public GuiElement getGuiElement(UUID groupUUID, AbstractXpSign sign) {
        return new ChooseGroup(groupUUID, sign, player);
    }

}
