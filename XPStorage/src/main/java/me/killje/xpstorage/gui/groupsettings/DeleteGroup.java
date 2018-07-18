package me.killje.xpstorage.gui.groupsettings;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for deleting the Ender group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DeleteGroup implements GuiElement {

    /**
     * The sign currently being edited
     */
    private final EnderGroupSign enderGroupSign;
    /**
     * The player editing the sign
     */
    private final Player player;

    /**
     * Icon for deleting the entire group
     *
     * @param enderGroupSign The sign being edited
     * @param player         The player editing the sign
     */
    public DeleteGroup(EnderGroupSign enderGroupSign, Player player) {
        this.enderGroupSign = enderGroupSign;
        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("deleteGroup");
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
        if (!enderGroupSign.canDestroySign(player)) {
            return;
        }

        currentInventoryBase.closeInventory(entity);

        enderGroupSign.getGroup().destoryGroup(player);

    }

}
