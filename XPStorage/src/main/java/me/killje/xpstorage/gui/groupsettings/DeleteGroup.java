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
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DeleteGroup implements GuiElement {

    private final EnderGroupSign enderGroupSign;
    private final Player player;

    public DeleteGroup(EnderGroupSign enderGroupSign, Player player) {
        this.enderGroupSign = enderGroupSign;
        this.player = player;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("deleteGroup");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {

        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) {
            return;
        }
        if (!enderGroupSign.canDestroySign(player)) {
            return;
        }

        currentinventoryBase.closeInventory(entity);

        enderGroupSign.getGroup().destoryGroup(player);

    }

}
