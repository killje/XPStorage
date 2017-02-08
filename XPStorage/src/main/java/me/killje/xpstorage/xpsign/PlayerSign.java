package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.utils.PlayerInformation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

/**
 *
 * @author Zolder
 */
public class PlayerSign extends AbstractXpSign {
    
    private final PlayerInformation playerInformation;
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
        }
        else {
            playerInformation = null;
        }
    }

    @Override
    protected void setNewXp(int xpInStorage) {
        playerInformation.setPlayerXpAmounth(xpInStorage);
        ArrayList<PlayerSign> playerSigns = PlayerSignHolder.getSignsForPlayer(getOwner());
        for (PlayerSign playerSign : playerSigns) {
            if (playerSign.equals(this)) {
                continue;
            }
            playerSign.updateSign();
        }
    }

    @Override
    protected int getCurrentXp() {
        return playerInformation.getPlayerXpAmounth();
    }

    @Override
    protected void changeToSign() {
        this.getSign().setLine(3, ChatColor.DARK_PURPLE + getSaveName(Bukkit.getOfflinePlayer(getOwner()).getName()));
    }

    @Override
    public String signType() {
        return "Player sign";
    }

    @Override
    public boolean hasAccess(UUID player) {
        return player.equals(getOwner());
    }
    
    @Override
    public boolean destroySign() {
        PlayerSignHolder.removeSignFromPlayer(getOwner(), this);
        return super.destroySign();
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
    }
    
}
