package me.killje.xpstorage.gui.list;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;

/**
 * Gets a icon for the group specified
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public interface GroupListGuiElement {

    /**
     * The icon for the given group
     *
     * @param groupUUID The group provided
     * @param sign The sign being edited
     * @return The icon for the group specified
     */
    public GuiElement getGuiElement(UUID groupUUID, AbstractXpSign sign);

}
