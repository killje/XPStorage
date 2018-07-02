package me.killje.schematic;

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
public class JsonSchematic implements GuiElement {

    private final String schematicName;
    
    public JsonSchematic(String schematicName) {
        this.schematicName = schematicName;
    }
    
    @Override
    public ItemStack getItemStack() {
        HashMap<String, String> replaceMap = new HashMap<>();
        replaceMap.put("NAME", schematicName);
        return GuiSettingsFromFile.getItemStack("schematic.json", replaceMap);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
