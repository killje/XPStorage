package me.killje.xpstorage.xpsign;

import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptions;
import me.killje.util.GuiSettingsFromFile;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SharedSign extends AbstractSharedSign {

    public SharedSign(Sign sign, UUID player) {
        super(sign, new Group(player));
    }

    public SharedSign(Map<String, Object> sign) {
        super(sign);
    }

    @Override
    protected String getSignText() {
        return GuiSettingsFromFile.getText("sharedSignText");
    }

    @Override
    public String signType() {
        return GuiSettingsFromFile.getText("sharedSignType");
    }

    @Override
    public boolean destroySign(Player playerWhoDestroys) {
        if (!super.destroySign(playerWhoDestroys)) {
            return false;
        }
        Group group = getGroup();
        if (group != null) {
            group.destoryGroup(playerWhoDestroys);
        }
        return true;
    }

    @Override
    public EditPlayerOptions getEditList(UUID playerToEdit, UUID playerEditing) {
        return new EditPlayerOptions(this, playerToEdit, playerEditing);
    }

}
