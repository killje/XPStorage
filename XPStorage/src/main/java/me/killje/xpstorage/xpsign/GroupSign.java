package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptions;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptionsGroup;
import me.killje.xpstorage.gui.sign.ChangeToGroup;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Zolder
 */
public class GroupSign extends AbstractSharedSign {
    
    public GroupSign(Sign sign, UUID groupId){
        super(sign, Group.getGroupFromUUID(groupId));
    }
    
    public GroupSign(Map<String, Object> sign) {
        super(sign);
    }

    @Override
    protected String getSignText() {
        return ChatColor.GREEN + "Group";
    }
    
    @Override
    public void updateSign() {
        getSign().setLine(0, ChatColor.BLUE + "[XP Storage]");
        getSign().setLine(1, ChatColor.AQUA + getSaveName(getGroup().getGroupName()));
        getSign().setLine(2, getCurrentXp() + "");
        getSign().setLine(3, getSignText() + "");
        getSign().update();
    }

    @Override
    public String signType() {
        return "Group sign";
    }

    @Override
    public boolean hasAccess(UUID player) {
        return getGroup().hasPlayer(player);
    }

    @Override
    public ArrayList<GuiElement> getAdditionalGuiElements(Player player) {
        ArrayList<GuiElement> guiElements = super.getAdditionalGuiElements(player);
        if (getOwner().equals(player.getUniqueId())) {
            guiElements.add(new ChangeToGroup(this, true));
        }
        return guiElements;
    }

    @Override
    public EditPlayerOptions getEditList(UUID playerToEdit, UUID playerEditing) {
        return new EditPlayerOptionsGroup(this, playerToEdit, playerEditing);
    }
}