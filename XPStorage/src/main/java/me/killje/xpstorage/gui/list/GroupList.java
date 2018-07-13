package me.killje.xpstorage.gui.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.guielement.SimpleGuiElement;
import me.killje.spigotgui.list.GuiElementList;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.choosegroup.CreateNewGroup;
import me.killje.xpstorage.permission.Permission;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;

/**
 * A inventory list of available groups
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GroupList extends GuiElementList {

    /**
     * The player being edited
     */
    private final Player player;
    /**
     * The sign being edited
     */
    private final AbstractXpSign xpSign;
    /**
     * Map of groups available
     */
    private final Map<String, GuiElement> groups = new HashMap<>();

    /**
     * Inventory list of groups available
     *
     * @param player The player editing
     * @param groupListGuiElement Class for creating the icon depending on the
     * group
     * @param xpSign The sign being edited
     */
    public GroupList(Player player, GroupListGuiElement groupListGuiElement,
            AbstractXpSign xpSign) {

        super(XPStorage.getGuiSettings(), player);
        this.player = player;
        this.xpSign = xpSign;

        List<Group> groupList = PlayerInformation.getPlayerInformation(
                player.getUniqueId()
        ).getGroups(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);

        for (Group group : groupList) {
            GuiElement groupGuiElement = groupListGuiElement
                    .getGuiElement(group.getGroupUuid(), xpSign);

            groups.put(group.getGroupName(), groupGuiElement);
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected int initInventory(
            int startIndex, int stopIndex, int maxItemsOnPage) {

        int initInventory = super.initInventory(
                startIndex, stopIndex, maxItemsOnPage
        );

        if (Permission.CREATE_NEW_GROUP.hasPermission(player)) {
            this.addGuiElement(new CreateNewGroup(player, xpSign), 1);
        } else {
            this.addGuiElement(new SimpleGuiElement(
                    guiSettings.getItemStack("newGroup.noacces")
            ), 1);
        }

        return initInventory;

    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Map<String, ? extends GuiElement> getElementMap() {
        return groups;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String getInventoryName() {
        return XPStorage.getGuiSettings().getText("choseGroup");
    }

}
