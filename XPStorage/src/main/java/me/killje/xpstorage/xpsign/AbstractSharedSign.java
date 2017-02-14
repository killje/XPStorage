package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptions;
import me.killje.xpstorage.gui.sign.EditList;
import me.killje.xpstorage.gui.sign.FromList;
import me.killje.xpstorage.utils.PlayerInformation;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Zolder
 */
public abstract class AbstractSharedSign extends AbstractXpSign {
    
    
    private final Group group;
    
    public AbstractSharedSign(Sign sign, Group group) {
        super(sign);
        this.group = group;
        group.addSignToGroup(this);
    }
    
    public AbstractSharedSign(Map<String, Object> sign) {
        super(sign);
        if (getError() != LoadError.NONE) {
            this.group = null;
            return;
        }
        Group tempGroup = Group.getGroupFromUUID(UUID.fromString((String) sign.get("uuidGroup")));
        if (tempGroup == null) {
            this.loadError = LoadError.NO_GROUP;
            this.group = null;
            return;
        }
        tempGroup.addSignToGroup(this);
        this.group = tempGroup;
    }

    @Override
    protected void setNewXp(int xpInStorage) {
        group.setXp(xpInStorage);
        updateSign();
    }
    

    @Override
    protected int getCurrentXp() {
        return group.getXp();
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("x", getSign().getX());
        returnMap.put("y", getSign().getY());
        returnMap.put("z", getSign().getZ());
        returnMap.put("world", getSign().getWorld().getUID().toString());
        returnMap.put("uuidGroup", group.getGroupUuid().toString());
        return returnMap;
    }
    
    
    @Override
    public boolean isGroupSign() {
        return true;
    }
    
    @Override
    public boolean hasAccess(UUID player) {
        return group.hasPlayer(player);
    }
    
    @Override
    public boolean destroySign() {
        group.removeSignFromGroup(this);
        return super.destroySign();
    }
    
    public Group getGroup() {
        return group;
    }

    @Override
    public UUID getOwner() {
        return group.getOwner();
    }

    @Override
    public void setOwner(UUID newOwner) {
        group.setOwner(newOwner);
    }

    @Override
    public ArrayList<GuiElement> getAdditionalGuiElements(Player player) {
        ArrayList<GuiElement> guiElements = new ArrayList<>();
        if (getOwner().equals(player.getUniqueId()) || PlayerInformation.getPlayerInformation(player.getUniqueId()).getGroupRights(getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_EDIT_PLAYERS)) {
            guiElements.add(new FromList(this));
            guiElements.add(new EditList(this));
        }
        return guiElements;
    }
    
    public abstract EditPlayerOptions getEditList(UUID playerToEdit, UUID playerEditing);
}
