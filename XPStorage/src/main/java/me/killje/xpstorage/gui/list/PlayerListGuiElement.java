package me.killje.xpstorage.gui.list;

import java.util.UUID;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public interface PlayerListGuiElement {
    
    
    public GuiElement getGuiElement (UUID offlinePlayer, AbstractXpSign sign);
    
    
}
