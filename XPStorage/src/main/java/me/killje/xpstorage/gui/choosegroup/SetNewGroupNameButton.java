package me.killje.xpstorage.gui.choosegroup;

import me.killje.spigotgui.character.SetStringButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.EnderGroupSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for setting the typed string as a new group and converting the sign to
 * that group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetNewGroupNameButton extends SetStringButton {

    /**
     * The player typing the new name for the group
     */
    private final Player player;
    /**
     * The sign the new group will be created on
     */
    private final AbstractXpSign xpSign;

    /**
     * When clicked on the icon will set the typed group name as a new group
     *
     * @param player The player creating the new group
     * @param xpSign The sign the new group will be created on
     */
    public SetNewGroupNameButton(Player player, AbstractXpSign xpSign) {
        this.player = player;
        this.xpSign = xpSign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected ItemStack confirmItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("newGroupName.confirm");
    }

    @Override
    /**
     * {@inheritDoc}
     *
     * Creates a new group with the typed in name
     */
    protected void executeSet(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) {
            return;
        }
        if (!xpSign.destroySign(player)) {
            return;
        }

        Group group = new Group(player.getUniqueId(),
                getKeyBoardStringStorage().getCurrent());

        EnderGroupSign sign = new EnderGroupSign(xpSign.getSign(),
                group.getGroupUuid());

        sign.changeSign();
        currentInventoryBase.closeInventory(entity);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected ItemStack noNameYetItem(GuiSetting guiSettings) {
        return guiSettings.getItemStack("newGroupName.noNameYet");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String textForEmpty(GuiSetting guiSettings) {
        return guiSettings.getText("newGroupName.empty");
    }

}
