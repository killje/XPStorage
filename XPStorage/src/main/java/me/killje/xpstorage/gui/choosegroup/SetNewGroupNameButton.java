package me.killje.xpstorage.gui.choosegroup;

import me.killje.spigotgui.character.SetStringButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetNewGroupNameButton extends SetStringButton {

    private final Player player;
    private final AbstractXpSign xpSign;

    public SetNewGroupNameButton(Player player, AbstractXpSign xpSign) {
        this.player = player;
        this.xpSign = xpSign;
    }

    @Override
    protected ItemStack confirmItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("newGroupName.confirm");
    }

    @Override
    protected ItemStack noNameYetItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("newGroupName.noNameYet");
    }

    @Override
    protected String textForEmpty(GuiSetting guiSettings) {
        return guiSettings.getText("newGroupName.empty");
    }

    @Override
    protected void executeSet(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) {
            return;
        }
        if (!xpSign.destroySign(player)) {
            return;
        }
        Group group = new Group(player.getUniqueId(), getKeyBoardStringStorage().getCurrent());
        GroupSign sign = new GroupSign(xpSign.getSign(), group.getGroupUuid());
        sign.changeSign();
        currentinventoryBase.closeInventory(entity);
    }

}
