package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.util.PlayerInformation;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 * Creates a sign that can be used by one person in multiple locations
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EnderPlayerSign extends AbstractXpSign {

    /**
     * The uuid of the owner
     */
    private UUID owner;
    /**
     * The player information of the owner
     */
    private PlayerInformation playerInformation;

    /**
     * Creates a new Ender player sign
     *
     * @param sign   The sign where the new ender player sign was created
     * @param player The player that the sign belongs to
     */
    public EnderPlayerSign(Sign sign, UUID player) {
        super(sign);
        owner = player;
        EnderPlayerSignHolder.addSignToPlayer(player, this);
        playerInformation = PlayerInformation.getPlayerInformation(player);
    }

    /**
     * Creates a ender player sign from file. This should not be used other than
     * loading from file
     *
     * @param sign The sign information
     */
    public EnderPlayerSign(Map<String, Object> sign) {
        super(sign);
        this.owner = UUID.fromString((String) sign.get("ownerUuid"));
        if (this.owner == null) {
            playerInformation = null;
            this.loadError = LoadError.NO_PLAYER;
            return;
        }
        if (getError() != LoadError.NONE) {
            playerInformation = null;
            return;
        }
        if (getOwner() != null) {
            EnderPlayerSignHolder.addSignToPlayer(getOwner(), this);
            playerInformation = PlayerInformation.getPlayerInformation(
                    getOwner());
        } else {
            playerInformation = null;
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
        EnderPlayerSignHolder.removeSignFromPlayer(getOwner(), this);
        return true;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public int getCurrentXp() {
        return playerInformation.getPlayerXpAmount();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public final UUID getOwner() {
        return this.owner;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
        playerInformation = PlayerInformation.getPlayerInformation(newOwner);
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
    public String signType() {
        return XPStorage.getGuiSettings().getText("enderPlayerSignType");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void setNewXp(int xpInStorage) {
        playerInformation.setPlayerXpAmount(xpInStorage);
        ArrayList<EnderPlayerSign> enderPlayerSigns = EnderPlayerSignHolder.
                getSignsForPlayer(getOwner());
        for (EnderPlayerSign enderPlayerSign : enderPlayerSigns) {
            if (enderPlayerSign.equals(this)) {
                continue;
            }
            enderPlayerSign.updateSign();
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String getSignText() {
        Map<String, String> replacement = new HashMap<>();
        replacement.put("PLAYER_NAME", getSaveName(Bukkit.getOfflinePlayer(
                getOwner()).getName()));
        return XPStorage.getGuiSettings().getText("enderPlayerSignText",
                replacement);
    }

}
