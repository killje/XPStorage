package me.killje.gui.list;

import java.util.UUID;
import me.killje.gui.guiElement.GuiElement;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public interface PlayerListElementFetcher {
    
    public GuiElement getGuiElement (UUID offlinePlayer);
    
}
