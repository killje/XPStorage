package me.killje.xpstorage.xpsign;

import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptions;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * A sign that can be used by multiple people in one location
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class LocalGroupSign extends AbstractGroupSign {

    /**
     * Creates a new Local group sign
     *
     * @param sign   The sign this groups sign was created on
     * @param player The player creating the sign
     */
    public LocalGroupSign(Sign sign, UUID player) {
        super(sign, new Group(player));
    }

    /**
     * Creates a local group sign from file. This should only be used when
     * loading from file
     *
     * @param sign The sign information
     */
    public LocalGroupSign(Map<String, Object> sign) {
        super(sign);
    }

    @Override
    /**
     * {@inheritDoc}
     */
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
    /**
     * {@inheritDoc}
     */
    public EditPlayerOptions getEditList(
            UUID playerToEdit, UUID playerEditing) {
        return new EditPlayerOptions(this, playerToEdit, playerEditing);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public String signType() {
        return XPStorage.getGuiSettings().getText("localGroupSignType");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String getSignText() {
        return XPStorage.getGuiSettings().getText("localGroupSignText");
    }

}
