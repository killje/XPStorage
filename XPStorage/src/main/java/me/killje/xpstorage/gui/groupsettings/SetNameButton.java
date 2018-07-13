package me.killje.xpstorage.gui.groupsettings;

import me.killje.spigotgui.character.SetStringButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for setting the new name on the ender group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetNameButton extends SetStringButton {

    /**
     * The sign being edited
     */
    private final EnderGroupSign enderGroupSign;

    /**
     * Icon for setting the name entered as the new group name
     *
     * @param enderGroupSign
     */
    public SetNameButton(EnderGroupSign enderGroupSign) {
        this.enderGroupSign = enderGroupSign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected ItemStack confirmItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setName.confirm");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected ItemStack noNameYetItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setName.noNameYet");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String textForEmpty(GuiSetting guiSettings) {
        return guiSettings.getText("setName.empty");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void executeSet(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        enderGroupSign.getGroup().setGroupName(
                getKeyBoardStringStorage().getCurrent()
        );

        currentInventoryBase.closeInventory(event.getWhoClicked());
        event.getWhoClicked().sendMessage(
                currentInventoryBase.getGuiSettings().getText("nameSet")
        );

        enderGroupSign.updateSign();
    }

}
