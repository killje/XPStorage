package me.killje.xpstorage.gui.sign;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for removing all xp out of the current player
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GetAllXp implements GuiElement {

    /**
     * The sign being edited
     */
    private final AbstractXpSign xpSign;

    /**
     * Icon for removing all xp from the editing player and storing them in the
     * sign
     *
     * @param xpSign The current sign being edited
     */
    public GetAllXp(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("getAllXP");
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

        xpSign.allXpOut((Player) entity);
        currentInventoryBase.closeInventory(entity);
    }

}
