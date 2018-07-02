package me.killje.schematic;

import java.io.File;
import java.util.HashMap;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class FileSchematic implements GuiElement {

    private final File file;
    
    public FileSchematic(File file) {
        this.file = file;
    }
    
    @Override
    public ItemStack getItemStack() {
        HashMap<String, String> replaceMap = new HashMap<>();
        replaceMap.put("NAME", file.getName());
        return GuiSettingsFromFile.getItemStack("schematic.file", replaceMap);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
