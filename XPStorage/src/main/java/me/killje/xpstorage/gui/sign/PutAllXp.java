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
 * Icon for giving all xp to the current player
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PutAllXp implements GuiElement {

    /**
     * The sign being edited
     */
    private final AbstractXpSign xpSign;

    /**
     * Icon for giving all xp from the sign and giving it to the editing player
     *
     * @param xpSign The sign being edited
     */
    public PutAllXp(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("putAllXP");
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
        xpSign.allXpIn((Player) entity);
        currentInventoryBase.closeInventory(entity);
    }

}
