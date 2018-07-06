package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.Exit;
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
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Settings extends InventoryBase implements GuiElement {

    private final Player player;
    private final AbstractXpSign xpSign;

    public Settings(AbstractXpSign xpSign, Player player) {
        super(XPStorage.getGuiSettings());
        this.player = player;
        this.xpSign = xpSign;
    }

    @Override
    protected void initInventory() {
        this
                .setInventoryName(getGuiSettings().getText("settings"))
                .addGuiElement(new SetMessage(player))
                .addGuiElement(new DefaultSign(player));

        if (player.hasPermission(Permission.CHANGE_OWNER.getPermission())) {
            this.addGuiElement(new ChangeOwnerList(player, xpSign));
        }
        this.addGuiElement(new Exit(), 8);
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("settings");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        currentinventoryBase.openNewInventory(event.getWhoClicked(), this);
    }

}
