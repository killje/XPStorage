package me.killje.xpstorage.gui.settings;

import me.killje.gui.Exit;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.permission.Permissions;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Patrick Beuks (s2288842) <patrick.beuks@gmail.com>
 */
public class Settings extends InventoryUtils implements GuiElement {

    private final Player player;
    private final AbstractXpSign xpSign;

    public Settings(AbstractXpSign xpSign, Player player) {
        this.player = player;
        this.xpSign = xpSign;
    }

    @Override
    protected void initInventory() {
        this
                .setInventoryName(GuiSettingsFromFile.getText("settings"))
                .addGuiElement(new SetMessage(player))
                .addGuiElement(new DefaultSign(player));

        if (player.hasPermission(Permissions.CHANGE_OWNER.getPermission())) {
            this.addGuiElement(new ChangeOwnerList(player, xpSign));
        }
        this.addGuiElement(new Exit(), 8);
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("settings");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(event.getWhoClicked(), this);
    }

    @Override
    protected Plugin getInstance() {
        return XPStorage.getInstance();
    }

}
