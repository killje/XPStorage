package me.killje.xpstorage.gui.list;

import java.util.ArrayList;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.choosegroup.CreateNewGroup;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;

/**
 *
 * @author Zolder
 */
public class GroupList extends List {
    
    private final AbstractXpSign xpSign;
    private final Player player;
    private final GroupListGuiElement groupListGuiElement;
    
    public GroupList(Player player, GroupListGuiElement groupListGuiElement, AbstractXpSign xpSign) {
        super(player);
        this.xpSign = xpSign;
        this.player = player;
        this.groupListGuiElement = groupListGuiElement;
    }
    
    @Override
    protected int initInventory(int startIndex, int stopIndex, int maxItemsOnPage) {
        
        this.addGuiElement(new CreateNewGroup());
        
        this.nextRow();
        
        ArrayList<Group> groups = PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroups(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);

        int toIndex = stopIndex;
        if (toIndex > groups.size()) {
            toIndex = groups.size();
        }
        ArrayList<Group> subArray = new ArrayList<>(groups.subList(startIndex, toIndex));

        for (Group group : subArray) {
            this.addGuiElement(this.groupListGuiElement.getGuiElement(group.getGroupUuid(), xpSign));
        }
        
        return groups.size();
        
    }

    @Override
    protected String getInventoryName() {
        return "Choose group";
    }

}
