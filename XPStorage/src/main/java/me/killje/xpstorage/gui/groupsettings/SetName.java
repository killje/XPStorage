package me.killje.xpstorage.gui.groupsettings;

import me.killje.spigotgui.character.KeyBoard;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon and inventory for setting a new name for a group
 *
 * When the icon is clicked will show a keyboard to enter a new
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetName extends KeyBoard implements GuiElement {

    /**
     * The player editing
     */
    private final Player player;

    /**
     * Icon and inventory for setting a name for a Ender group.
     *
     * @param player         The player editing the sign
     * @param enderGroupSign The sign being edited
     */
    public SetName(Player player, EnderGroupSign enderGroupSign) {
        super(XPStorage.getGuiSettings(), player,
                new SetNameButton(enderGroupSign));

        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setName");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        currentInventoryBase.openNewInventory(player, this);
    }

}
