package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.permission.Permission;
import me.killje.xpstorage.xpsign.LocalPlayerSign;
import me.killje.xpstorage.xpsign.EnderPlayerSign;
import me.killje.xpstorage.xpsign.LocalGroupSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon and inventory for changing the default sign type when creating a new
 * sign
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DefaultSign extends InventoryBase implements GuiElement {

    /**
     * The player being edited
     */
    private final Player player;

    /**
     * Icon and inventory for changing the default sign type when creating a new
     * sign
     *
     * @param player The player being edited
     */
    public DefaultSign(Player player) {
        super(XPStorage.getGuiSettings());
        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("defaultSign");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        currentInventoryBase.openNewInventory(event.getWhoClicked(), this);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void initInventory() {
        this.setInventoryName(getGuiSettings().getText("pickDefaultSign"));

        if (Permission.CREATE_LOCAL_PLAYER.hasPermission(player)) {
            this.addGuiElement(new DefaultSignPicker(
                    player, LocalPlayerSign.class, "localPlayer"
            ));
        }

        if (Permission.CREATE_ENDER_PLAYER.hasPermission(player)) {
            this.addGuiElement(new DefaultSignPicker(
                    player, EnderPlayerSign.class, "enderPlayer"
            ));
        }

        if (Permission.CREATE_LOCAL_GROUP.hasPermission(player)) {
            this.addGuiElement(new DefaultSignPicker(
                    player, LocalGroupSign.class, "localGroup"
            ));
        }

        this.addGuiElement(new Exit(), 8);
    }

}
