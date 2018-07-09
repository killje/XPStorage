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
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetName extends KeyBoard implements GuiElement {

    private final Player player;

    public SetName(Player player, EnderGroupSign enderGroupSign) {
        super(XPStorage.getGuiSettings(), player, new SetNameButton(enderGroupSign));
        this.player = player;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setName");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        currentinventoryBase.openNewInventory(player, this);
    }

}
