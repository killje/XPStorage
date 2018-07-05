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
import me.killje.xpstorage.permission.Permissions;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GroupList extends GuiElementList {
    
    private final Player player;
    private final AbstractXpSign xpSign;
    private final Map<String, GuiElement> groups = new HashMap<>();
    
    public GroupList(Player player, GroupListGuiElement groupListGuiElement, AbstractXpSign xpSign) {
        super(XPStorage.getGuiSettings(), player, XPStorage.getGuiSettings().getText("choseGroup"));
        this.player = player;
        this.xpSign = xpSign;
        
        List<Group> groupList = PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroups(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
        
        for (Group group : groupList) {
            GuiElement groupGuiElement = groupListGuiElement.getGuiElement(group.getGroupUuid(), xpSign);
            groups.put(group.getGroupName(), groupGuiElement);
        }
    }
    
    @Override
    protected int initInventory(int startIndex, int stopIndex, int maxItemsOnPage) {
        
        int initInventory = super.initInventory(startIndex, stopIndex, maxItemsOnPage);
        if (player.hasPermission(Permissions.CREATE_XP_GROUP.getPermission())) {
            this.addGuiElement(new CreateNewGroup(player, xpSign), 1);
        } else {
            this.addGuiElement(new SimpleGuiElement(guiSettings.getItemStack("newGroup.noacces")), 1);
        }
        
        return initInventory;
        
    }

    @Override
    protected Map<String, ? extends GuiElement> getElementMap() {
        return groups;
    }

}
