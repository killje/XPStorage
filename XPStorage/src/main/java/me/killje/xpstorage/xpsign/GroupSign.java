package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptions;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptionsGroup;
import me.killje.xpstorage.gui.groupsettings.DeleteGroup;
import me.killje.xpstorage.gui.groupsettings.SetIcon;
import me.killje.xpstorage.gui.groupsettings.SetName;
import me.killje.xpstorage.gui.sign.ChangeToGroup;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GroupSign extends AbstractSharedSign {

    public GroupSign(Sign sign, UUID groupId) {
        super(sign, Group.getGroupFromUUID(groupId));
    }

    public GroupSign(Map<String, Object> sign) {
        super(sign);
    }

    @Override
    protected String getSignText() {
        return XPStorage.getGuiSettings().getText("groupSignText");
    }

    @Override
    protected String getSecondLine() {
        Map<String, String> replacement = new HashMap<>();

        String groupName = "";
        if (getGroup() != null) {
            groupName = getGroup().getGroupName();
        }

        String saveName = getSaveName(groupName);
        replacement.put("GROUP_NAME", saveName);
        return XPStorage.getGuiSettings().getText("groupName", replacement);
    }

    @Override
    public String signType() {
        return XPStorage.getGuiSettings().getText("groupSignType");
    }

    @Override
    public boolean hasAccess(UUID player) {
        return getGroup().hasPlayer(player);
    }

    @Override
    public ArrayList<GuiElement> getAdditionalGuiElements(Player player) {
        ArrayList<GuiElement> guiElements = super.getAdditionalGuiElements(player);
        if (getOwner().equals(player.getUniqueId())) {
            guiElements.add(new ChangeToGroup(player, this, true));
            guiElements.add(new SetName(player, this));
            guiElements.add(new SetIcon(getGroup().getGroupUuid()));
            guiElements.add(new DeleteGroup(this, player));
        }
        return guiElements;
    }

    @Override
    public EditPlayerOptions getEditList(UUID playerToEdit, UUID playerEditing) {
        return new EditPlayerOptionsGroup(this, playerToEdit, playerEditing);
    }
}
