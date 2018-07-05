package me.killje.xpstorage.gui.list;

import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public interface GroupListGuiElement {
    
    
    public GuiElement getGuiElement (UUID groupUUID, AbstractXpSign sign);
    
    
}
