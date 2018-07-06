package me.killje.xpstorage.gui.groupsettings;

import me.killje.spigotgui.character.KeyBoard;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetName extends KeyBoard implements GuiElement {

    private final Player player;

    public SetName(Player player, GroupSign groupSign) {
        super(XPStorage.getGuiSettings(), player, new SetNameButton(groupSign));
        this.player = player;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("setName");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(player, this);
    }

}
