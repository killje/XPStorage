package me.killje.xpstorage.gui.choosegroup;

import me.killje.xpstorage.group.Group;
import me.killje.gui.InventoryUtils;
import me.killje.xpstorage.gui.characters.SetStringButton;
import me.killje.util.GuiSettingsFromFile;
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
    protected ItemStack confirmItem() {
        return GuiSettingsFromFile.getItemStack("newGroupName.confirm");
    }

    @Override
    protected ItemStack noNameYetItem() {
        return GuiSettingsFromFile.getItemStack("newGroupName.noNameYet");
    }

    @Override
    protected String textForEmpty() {
        return GuiSettingsFromFile.getText("newGroupName.empty");
    }

    @Override
    protected void executeSet(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        if (!xpSign.destroySign(player)) {
            return;
        }
        Group group = new Group(player.getUniqueId(), getKeyBoardStringStorage().getCurrent());
        GroupSign sign = new GroupSign(xpSign.getSign(), group.getGroupUuid());
        sign.changeSign();
        currentInventoryUtils.closeInventory(entity);
    }
    
}
