package me.killje.xpstorage.gui.choosegroup;

import java.util.ArrayList;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.Exit;
import me.killje.xpstorage.gui.InventoryUtils;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;

/**
 *
 * @author Zolder
 */
public class GroupList extends InventoryUtils {
    
    private final AbstractXpSign xpSign;
    private final Player player;
    private final int page;
    
    public GroupList(Player player, AbstractXpSign xpSign) {
        this(player, xpSign, 0);
    }
    
    public GroupList(Player player, AbstractXpSign xpSign, int page) {
        super(6);
        this.xpSign = xpSign;
        this.player = player;
        this.page = page;
    }
    
    @Override
    protected void initInventory() {
        
        this.addGuiElement(new CreateNewGroup());
        int maxItemsOnPage = 45; // 4 * 9
        
        int startIndex = page * maxItemsOnPage;
        int stopIndex = (page + 1) * maxItemsOnPage;
        this.nextRow();
        //Player[] onlinePlayersArray = new Player[45];
        ArrayList<Group> groups = PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroups(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);

        int toIndex = stopIndex;
        if (toIndex > groups.size()) {
            toIndex = groups.size();
        }
        ArrayList<Group> subArray = new ArrayList<>(groups.subList(startIndex, toIndex));

        for (Group group : subArray) {
            this.addGuiElement(new ChooseGroup(group.getGroupUuid(), xpSign));
        }
        
        if (page != 0) {
            this.addGuiElement(new PrevPage(page, xpSign), 3);
        }
        if (groups.size() > stopIndex) {
            this.addGuiElement(new NextPage(page, xpSign), 5);
        }
        
        
        this.addGuiElement(new Exit(), 8);
        
        this.setInventoryName("Choose group | Page " + (page + 1) + "/" + (groups.size()/maxItemsOnPage + 1));
        
    }
}
