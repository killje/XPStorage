package me.killje.xpstorage.gui.choosegroup;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for selecting a group from a list of groups
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChooseGroup implements GuiElement {

    /**
     * The group that will be selected when clicked on
     */
    private final UUID groupId;

    /**
     * The sign that will become the new group sign when selected
     */
    private AbstractXpSign xpSign;

    /**
     * The player selecting the group
     */
    private final Player player;

    /**
     * Creates a icon for a group that when selected converts the sign into that
     * group
     *
     * @param groupId The group id for witch to convert into
     * @param xpSign The sign that will be converted
     * @param player The player changing the group
     */
    public ChooseGroup(UUID groupId, AbstractXpSign xpSign, Player player) {
        this.groupId = groupId;
        this.xpSign = xpSign;
        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        Group group = Group.getGroupFromUUID(groupId);
        return group.getGroupIcon();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) {
            return;
        }
        if (!xpSign.destroySign(player)) {
            return;
        }
        xpSign = new EnderGroupSign(xpSign.getSign(), groupId);
        xpSign.changeSign();

        currentInventoryBase.closeInventory(entity);
    }

}
