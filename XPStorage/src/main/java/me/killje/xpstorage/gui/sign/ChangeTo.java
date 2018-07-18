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
 * Icon for setting the sign to a new type by the type given
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeTo implements GuiElement {

    /**
     * The setting name in the GUI.yml file for the text
     */
    private final String guiSettingsName;
    /**
     * The player editing the sign
     */
    private final Player player;
    /**
     * The type to set the sign to when clicked
     */
    private final Class<? extends AbstractXpSign> signClass;
    /**
     * The sign being edited
     */
    private AbstractXpSign xpSign;

    /**
     * Icon for changing the sign to a given type of sign
     *
     * @param player          The player editing the sign
     * @param xpSign          The sign being edited
     * @param signClass       The type to set the sign to
     * @param guiSettingsName The settings name in the GUI.yml for texts
     */
    public ChangeTo(Player player,
            AbstractXpSign xpSign, Class<? extends AbstractXpSign> signClass,
            String guiSettingsName) {

        this.xpSign = xpSign;
        this.guiSettingsName = guiSettingsName;
        this.signClass = signClass;
        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        if (xpSign.getClass().equals(signClass)) {
            return guiSettings.getItemStack("selected." + guiSettingsName);
        }
        return guiSettings.getItemStack("changeTo." + guiSettingsName);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        if (xpSign.getClass().equals(signClass)) {
            return;
        }
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) {
            return;
        }
        if (!xpSign.destroySign(player)) {
            return;
        }

        xpSign = AbstractXpSign.createSign(signClass, xpSign.getSign(), entity);
        xpSign.changeSign();

        currentInventoryBase.closeInventory(entity);
    }

}
