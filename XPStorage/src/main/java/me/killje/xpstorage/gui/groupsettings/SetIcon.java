package me.killje.xpstorage.gui.groupsettings;

import java.util.UUID;
import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.guielement.SimpleGuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon and inventory to set a icon as the ender group icon
 *
 * @author Patrick Beuks (killje) <code@beuks.net>
 */
public class SetIcon extends InventoryBase implements GuiElement {

    /**
     * The group being edited
     */
    private final UUID groupUUID;

    /**
     * Icon and inventory for setting the icon of the group
     *
     * @param groupUUID The group being edited
     */
    public SetIcon(UUID groupUUID) {
        super(XPStorage.getGuiSettings(), InventoryBaseType.HOPPER);
        this.groupUUID = groupUUID;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setIcon");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(InventoryBase currentInventoryBase,
            InventoryClickEvent event) {
        currentInventoryBase.openNewInventory(event.getWhoClicked(), this);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void initInventory() {
        
        ItemStack fillerItem = getGuiSettings().getItemStack("setIconFiller");

        this
                .setInventoryName(guiSettings.getText("setIcon"))
                .isIgnorePlayerInventory(false)
                .addGuiElement(new SimpleGuiElement(fillerItem))
                .addGuiElement(new SimpleGuiElement(fillerItem))
                .addInventoryElement(new SetIconElement(groupUUID))
                .addGuiElement(new SimpleGuiElement(fillerItem))
                .addGuiElement(new Exit());
    }

}
