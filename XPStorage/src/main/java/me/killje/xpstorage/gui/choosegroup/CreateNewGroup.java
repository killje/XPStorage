package me.killje.xpstorage.gui.choosegroup;

import me.killje.spigotgui.character.KeyBoard;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.permission.Permission;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A icon and inventory for creating a new group
 *
 * Then clicked on will show a keyboard to enter a new for the new group.
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class CreateNewGroup extends KeyBoard implements GuiElement {

    /**
     * The player making a new group
     */
    private final Player player;

    /**
     * Icon and inventory to create a new group
     *
     * @param player The player creating the new group
     * @param xpSign The sign where the new group will be put on
     */
    public CreateNewGroup(Player player, AbstractXpSign xpSign) {
        super(XPStorage.getGuiSettings(), player,
                new SetNewGroupNameButton(player, xpSign));

        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("newGroup");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        if (!Permission.CREATE_NEW_GROUP.hasPermission(event.getWhoClicked())) {
            return;
        }
        currentInventoryBase.openNewInventory(player, this);

    }

}
