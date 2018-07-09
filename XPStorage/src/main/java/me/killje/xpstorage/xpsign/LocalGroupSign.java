package me.killje.xpstorage.xpsign;

import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptions;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class LocalGroupSign extends AbstractGroupSign {

    public LocalGroupSign(Sign sign, UUID player) {
        super(sign, new Group(player));
    }

    public LocalGroupSign(Map<String, Object> sign) {
        super(sign);
    }

    @Override
    protected String getSignText() {
        return XPStorage.getGuiSettings().getText("localGroupSignText");
    }

    @Override
    public String signType() {
        return XPStorage.getGuiSettings().getText("localGroupSignType");
    }

    @Override
    public boolean destroySign(Player playerWhoDestroys) {
        if (!super.destroySign(playerWhoDestroys)) {
            return false;
        }
        allXpOut(playerWhoDestroys);
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
