package me.killje.xpstorage.gui.sign;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.gui.editplayer.CurrentList;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon to open a list of players in the current group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EditList implements GuiElement {

    /**
     * The current sign being edited
     */
    private final AbstractGroupSign sign;

    /**
     * Icon for editing the current players in the group
     *
     * @param sign The sign being edited
     */
    public EditList(AbstractGroupSign sign) {
        this.sign = sign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("editList");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        CurrentList currentList = new CurrentList(player, sign);
        currentInventoryBase.openNewInventory(player, currentList);

    }
}
