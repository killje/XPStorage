package me.killje.xpstorage.gui.groupsettings;

import me.killje.spigotgui.character.SetStringButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetNameButton extends SetStringButton {

    private final EnderGroupSign enderGroupSign;

    public SetNameButton(EnderGroupSign enderGroupSign) {
        this.enderGroupSign = enderGroupSign;
    }

    @Override
    protected ItemStack confirmItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setName.confirm");
    }

    @Override
    protected ItemStack noNameYetItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setName.noNameYet");
    }

    @Override
    protected String textForEmpty(GuiSetting guiSettings) {
        return guiSettings.getText("setName.empty");
    }

    @Override
    protected void executeSet(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        enderGroupSign.getGroup().setGroupName(getKeyBoardStringStorage().getCurrent());
        currentinventoryBase.closeInventory(event.getWhoClicked());
        event.getWhoClicked().sendMessage(currentinventoryBase.getGuiSettings().getText("nameSet"));
        enderGroupSign.updateSign();
    }

}
