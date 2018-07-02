package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.utils.PlayerInformation;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PlayerSign extends AbstractXpSign {

    private PlayerInformation playerInformation;
    private UUID owner;

    public PlayerSign(Sign sign, UUID player) {
        super(sign);
        owner = player;
        PlayerSignHolder.addSignToPlayer(player, this);
        playerInformation = PlayerInformation.getPlayerInformation(player);
    }

    public PlayerSign(Map<String, Object> sign) {
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
            PlayerSignHolder.addSignToPlayer(getOwner(), this);
            playerInformation = PlayerInformation.getPlayerInformation(getOwner());
        } else {
            playerInformation = null;
        }
    }

    @Override
    protected void setNewXp(int xpInStorage) {
        playerInformation.setPlayerXpAmount(xpInStorage);
        ArrayList<PlayerSign> playerSigns = PlayerSignHolder.getSignsForPlayer(getOwner());
        for (PlayerSign playerSign : playerSigns) {
            if (playerSign.equals(this)) {
                continue;
            }
            playerSign.updateSign();
        }
    }

    @Override
    public int getCurrentXp() {
        return playerInformation.getPlayerXpAmount();
    }

    @Override
    protected String getSignText() {
        Map<String, String> replacement = new HashMap<>();
        replacement.put("PLAYER_NAME", getSaveName(Bukkit.getOfflinePlayer(getOwner()).getName()));
        return GuiSettingsFromFile.getText("playerSignText", replacement);
    }

    @Override
    public String signType() {
        return GuiSettingsFromFile.getText("playerSignType");
    }

    @Override
    public boolean hasAccess(UUID player) {
        return player.equals(getOwner());
    }

    @Override
    public boolean destroySign(Player playerWhoDestroys) {
        if (!super.destroySign(playerWhoDestroys)) {
            return false;
        }
        PlayerSignHolder.removeSignFromPlayer(getOwner(), this);
        return true;
    }

    @Override
    public final UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
        playerInformation = PlayerInformation.getPlayerInformation(newOwner);
    }

}
