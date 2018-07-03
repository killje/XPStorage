package me.killje.xpstorage.gui.groupsettings;

import me.killje.gui.InventoryUtils;
import me.killje.gui.characters.SetStringButton;
import me.killje.util.GuiSettingsFromFile;
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
    protected ItemStack confirmItem() {
        return GuiSettingsFromFile.getItemStack("setName.confirm");
    }

    @Override
    protected ItemStack noNameYetItem() {
        return GuiSettingsFromFile.getItemStack("setName.noNameYet");
    }
    
    @Override
    protected String textForEmpty() {
        return GuiSettingsFromFile.getText("setName.empty");
    }
    
    @Override
    protected void executeSet(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        groupSign.getGroup().setGroupName(getKeyBoardStringStorage().getCurrent());
        currentInventoryUtils.closeInventory(event.getWhoClicked());
        event.getWhoClicked().sendMessage(GuiSettingsFromFile.getText("nameSet"));
        groupSign.updateSign();
    }

    
}
