package me.killje.xpstorage.gui.list;

import java.util.ArrayList;
import java.util.UUID;
import me.killje.gui.guiElement.GuiElement;
import me.killje.gui.list.GuiElementList;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.choosegroup.CreateNewGroup;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.permission.Permissions;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GroupList extends GuiElementList {
    
    private final Player player;
    private final AbstractXpSign xpSign;
    
    public GroupList(Player player, GroupListGuiElement groupListGuiElement, AbstractXpSign xpSign) {
        super(player, getGuiElements(player.getUniqueId(), groupListGuiElement, xpSign), GuiSettingsFromFile.getText("choseGroup"));
        this.player = player;
        this.xpSign = xpSign;
    }
    
    private static ArrayList<GuiElement> getGuiElements (UUID playerId, GroupListGuiElement groupListGuiElement, AbstractXpSign xpSign) {
        ArrayList<Group> groups = PlayerInformation.getPlayerInformation(playerId).getGroups(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
        ArrayList<GuiElement> guiElements = new ArrayList<>();
        for (Group group : groups) {
            guiElements.add(groupListGuiElement.getGuiElement(group.getGroupUuid(), xpSign));
        }
        return guiElements;
    }
    
    @Override
    protected int initInventory(int startIndex, int stopIndex, int maxItemsOnPage) {
        
        if (player.hasPermission(Permissions.CREATE_XP_GROUP.getPermission())) {
            this.addGuiElement(new CreateNewGroup(player, xpSign));
        }
        
        return super.initInventory(startIndex, stopIndex, maxItemsOnPage);
        
    }

}
