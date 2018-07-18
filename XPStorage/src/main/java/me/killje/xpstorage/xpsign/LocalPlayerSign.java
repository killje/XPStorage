package me.killje.xpstorage.xpsign;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import static me.killje.xpstorage.xpsign.AbstractXpSign.getSaveName;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * Sign that can be used by one person in one location
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class LocalPlayerSign extends AbstractXpSign {

    /**
     * The owner of the sign
     */
    private UUID owner;
    /**
     * The amount of xp stored on the sign
     */
    private int xpInStorage;

    /**
     * Creates a Local Player sign
     *
     * @param sign   The sign that is the local player sign
     * @param player The player creating the sign
     */
    public LocalPlayerSign(Sign sign, UUID player) {
        super(sign);
        this.owner = player;
        // Check if the owner is real
        if (player == null) {
            loadError = LoadError.NO_PLAYER;
        }
        this.xpInStorage = 0;
    }

    /**
     * Creates a sign from file storage. Should only be used when loading from
     * file
     *
     * @param sign The sign information
     */
    public LocalPlayerSign(Map<String, Object> sign) {
        super(sign);
        this.owner = UUID.fromString((String) sign.get("ownerUuid"));
        if (this.owner == null) {
            this.loadError = LoadError.NO_PLAYER;
            return;
        }
        if (getError() != LoadError.NONE) {
            return;
        }
        if (sign.containsKey("xpStored")) {
            this.xpInStorage = (int) sign.get("xpStored");
        } else {
            this.xpInStorage = 0;
        }
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
        return true;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public int getCurrentXp() {
        return xpInStorage;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public UUID getOwner() {
        return owner;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
    }

    /**
     * SHOULD ONLY BE USED FOR CONVERTING FROM OLD SIGNS
     *
     * @param xp the xp to set the sign to
     */
    public void setXP(int xp) {
        this.xpInStorage = xp;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public boolean hasAccess(UUID player) {
        return player.equals(getOwner());
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> serialize() {
        Map<String, Object> saveMap = super.serialize();

        saveMap.put("xpStored", this.xpInStorage);
        return saveMap;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public String signType() {
        return XPStorage.getGuiSettings().getText("localPlayerSignType");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void setNewXp(int xpInStorage) {
        this.xpInStorage = xpInStorage;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String getSignText() {
        Map<String, String> replacement = new HashMap<>();
        replacement.put("PLAYER_NAME", getSaveName(
                Bukkit.getOfflinePlayer(getOwner()).getName()
        ));

        return XPStorage.getGuiSettings()
                .getText("localPlayerSignText", replacement);
    }

}
