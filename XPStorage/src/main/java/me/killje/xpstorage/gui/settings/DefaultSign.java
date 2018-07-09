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
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DefaultSign extends InventoryBase implements GuiElement {

    private final Player player;

    public DefaultSign(Player player) {
        super(XPStorage.getGuiSettings());
        this.player = player;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("defaultSign");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        currentinventoryBase.openNewInventory(event.getWhoClicked(), this);
    }

    @Override
    protected void initInventory() {
        this.setInventoryName(getGuiSettings().getText("pickDefaultSign"));
        if (Permission.CREATE_LOCAL_PLAYER.hasPermission(player)) {
            this.addGuiElement(new DefaultSignPicker(player, LocalPlayerSign.class, "localPlayer"));
        }
        if (Permission.CREATE_ENDER_PLAYER.hasPermission(player)) {
            this.addGuiElement(new DefaultSignPicker(player, EnderPlayerSign.class, "enderPlayer"));
        }
        if (Permission.CREATE_LOCAL_GROUP.hasPermission(player)) {
            this.addGuiElement(new DefaultSignPicker(player, LocalGroupSign.class, "localGroup"));
        }
        this.addGuiElement(new Exit(), 8);
    }

}
