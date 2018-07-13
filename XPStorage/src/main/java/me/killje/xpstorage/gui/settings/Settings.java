package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.permission.Permission;
import static me.killje.xpstorage.permission.Permission.*;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon and inventory for settings for XPStorage
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Settings extends InventoryBase implements GuiElement {

    /**
     * The player to set the settings for
     */
    private final Player player;
    /**
     * The sign being edited
     */
    private final AbstractXpSign xpSign;

    /**
     * Icon and inventory for setting settings for the player
     *
     * @param xpSign The sign being edited
     * @param player The player to set the settings for
     */
    public Settings(AbstractXpSign xpSign, Player player) {
        super(XPStorage.getGuiSettings());
        this.player = player;
        this.xpSign = xpSign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void initInventory() {
        this
                .setInventoryName(getGuiSettings().getText("settings"))
                .addGuiElement(new SetMessage(player));
        if (Permission.hasAnyPermission(player, CREATE_LOCAL_PLAYER, CREATE_ENDER_PLAYER, CREATE_LOCAL_GROUP)) {
            this.addGuiElement(new DefaultSign(player));
        }
        if (Permission.CHANGE_OWNER.hasPermission(player)) {
            this.addGuiElement(new ChangeOwnerList(player, xpSign));
        }
        this.addGuiElement(new Exit(), 8);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("settings");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(InventoryBase currentInventoryBase, InventoryClickEvent event) {
        currentInventoryBase.openNewInventory(event.getWhoClicked(), this);
    }

}
