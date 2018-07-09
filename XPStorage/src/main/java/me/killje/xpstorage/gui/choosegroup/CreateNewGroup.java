package me.killje.xpstorage.gui.choosegroup;

import me.killje.spigotgui.character.KeyBoard;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.permission.Permission;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class CreateNewGroup extends KeyBoard implements GuiElement {

    private final Player player;

    public CreateNewGroup(Player player, AbstractXpSign xpSign) {
        super(XPStorage.getGuiSettings(), player, new SetNewGroupNameButton(player, xpSign));
        this.player = player;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("newGroup");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        if(!Permission.CREATE_NEW_GROUP.hasPermission(event.getWhoClicked())) {
            return;
        }
        currentinventoryBase.openNewInventory(player, this);

    }

}
