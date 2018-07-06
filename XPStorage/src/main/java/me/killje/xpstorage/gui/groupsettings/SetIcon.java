package me.killje.xpstorage.gui.groupsettings;

import java.util.UUID;
import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.guielement.SimpleGuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.XPStorage;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetIcon extends InventoryUtil implements GuiElement {

    private final UUID groupUUID;

    public SetIcon(UUID groupUUID) {
        super(XPStorage.getGuiSettings(), InventoryUtilsType.HOPPER);
        this.groupUUID = groupUUID;
    }

    @Override
    protected void initInventory() {
        String iconInMiddle = getGuiSettings().getText("iconInMiddle");
        this
                .setInventoryName(guiSettings.getText("setIcon"))
                .isIgnorePlayerInventory(false)
                .addGuiElement(new SimpleGuiElement(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getWoolData()), iconInMiddle))
                .addGuiElement(new SimpleGuiElement(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getWoolData()), iconInMiddle))
                .addInventoryElement(new SetIconElement(groupUUID))
                .addGuiElement(new SimpleGuiElement(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getWoolData()), iconInMiddle))
                .addGuiElement(new Exit());
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setIcon");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(event.getWhoClicked(), this);
    }

}
