package me.killje.xpstorage.gui.groupsettings;

import java.util.UUID;
import me.killje.gui.Exit;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.guiElement.SimpleGuiElement;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetIcon extends InventoryUtils implements GuiElement {

    private final UUID groupUUID;
    
    public SetIcon(UUID groupUUID) {
        super(InventoryUtilsType.HOPPER);
        this.groupUUID = groupUUID;
    }

    @Override
    protected void initInventory() {
        String iconInMiddle = GuiSettingsFromFile.getText("iconInMiddle");
        this
                .setInventoryName(GuiSettingsFromFile.getText("setIcon"))
                .isIgnorePlayerInventory(false)
                .addGuiElement(new SimpleGuiElement(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getWoolData()), iconInMiddle))
                .addGuiElement(new SimpleGuiElement(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getWoolData()), iconInMiddle))
                .addInventoryElement(new SetIconElement(groupUUID))
                .addGuiElement(new SimpleGuiElement(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getWoolData()), iconInMiddle))
                .addGuiElement(new Exit());
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("setIcon");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(event.getWhoClicked(), this);
    }
    
}
