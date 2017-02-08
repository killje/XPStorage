package me.killje.xpstorage.xpsign;

import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Zolder
 */
public class NormalSign extends AbstractXpSign {

    private int xpInStorage;
    private UUID owner;
    
    public NormalSign(Sign sign, UUID player) {
        super(sign);
        this.owner = player;
        // Check if the owner is real
        if (player == null) {
            loadError = LoadError.NO_PLAYER;
        }
    }
    
    public NormalSign(Map<String, Object> sign) {
        super(sign);
        this.owner = UUID.fromString((String) sign.get("ownerUuid"));
        if (this.owner == null) {
            this.loadError = LoadError.NO_PLAYER;
            return;
        }
        if (getError() != LoadError.NONE) {
            return;
        }
        getXpFromSign();
    }
    
    public void getXpFromSign () {
        xpInStorage = Integer.parseInt(getSign().getLine(1));
    }
    
    @Override
    protected void setNewXp(int xpInStorage) {
        this.xpInStorage = xpInStorage;
    }

    @Override
    protected int getCurrentXp() {
        return xpInStorage;
    }
    
    public void onSignWrite () {
        Sign sign = getSign();
        sign.setLine(0, ChatColor.BLUE + "[XP Storage]");
        sign.setLine(1, "0");
        sign.setLine(2, "[5] 20 100 500");
        String playername = getSaveName(Bukkit.getOfflinePlayer(getOwner()).getName());
        sign.setLine(3, playername);
        updateSign();
    }
    
    @Override
    protected void changeToSign() {
        setNewXp(0);
        getSign().setLine(3, getSaveName(Bukkit.getOfflinePlayer(getOwner()).getName()));
    }

    @Override
    public String signType() {
        return "Location sign";
    }

    @Override
    public boolean hasAccess(UUID player) {
        return player.equals(getOwner());
    }
    
    @Override
    public boolean destroySign() {
        Player player = Bukkit.getPlayer(getOwner());
        if (player == null) {
            return true;
        }
        if (getCurrentXp() == 0) {
            return super.destroySign();
        }
        allXpOut(player);
        return super.destroySign();
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
    }

}
