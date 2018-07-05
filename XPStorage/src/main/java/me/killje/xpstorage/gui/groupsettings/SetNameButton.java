package me.killje.xpstorage.gui.groupsettings;

import me.killje.spigotgui.character.SetStringButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetNameButton extends SetStringButton {
    
    private final GroupSign groupSign;

    public SetNameButton(GroupSign groupSign) {
        this.groupSign = groupSign;
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
    protected void executeSet(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        groupSign.getGroup().setGroupName(getKeyBoardStringStorage().getCurrent());
        currentInventoryUtils.closeInventory(event.getWhoClicked());
        event.getWhoClicked().sendMessage(currentInventoryUtils.getGuiSettings().getText("nameSet"));
        groupSign.updateSign();
    }

    
}
