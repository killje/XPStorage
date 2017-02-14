package me.killje.xpstorage.gui.guiElement;

import me.killje.xpstorage.utils.HeadUtils;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public abstract class SimpleHead implements GuiElement {
    
    private final String texture;
    private final String displayName;

    public SimpleHead(String texture, String displayName) {
        this.texture = texture;
        this.displayName = displayName;
    }
    
    @Override
    public ItemStack getItemStack() {
        
        return HeadUtils.getTexturedHead(texture, displayName);
    }
    
}
