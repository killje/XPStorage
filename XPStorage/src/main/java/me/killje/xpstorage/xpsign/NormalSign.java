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
        this.xpInStorage = 0;
        updateSign();
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
        if (sign.containsKey("xpStored")) {
            this.xpInStorage = (int) sign.get("xpStored");
        }
        else {
            this.xpInStorage = 0;
        }
        updateSign();
    }
    
    @Override
    protected void setNewXp(int xpInStorage) {
        this.xpInStorage = xpInStorage;
        //AbstractXpSign.saveSigns();
    }

    @Override
    protected int getCurrentXp() {
        return xpInStorage;
    }
    
    public void onSignWrite () {
        updateSign();
    }
    
    @Override
    protected String getSignText() {
        return getSaveName(Bukkit.getOfflinePlayer(getOwner()).getName());
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
            return false;
        }
        if (getCurrentXp() == 0) {
            return super.destroySign();
        }
        allXpOut(player);
        return super.destroySign();
    }

    @Override
    public boolean canDestroySign() {
        Player player = Bukkit.getPlayer(getOwner());
        if (player == null) {
            return false;
        }
        return super.canDestroySign();
    }
    
    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
    }

    
    /**
     * Function to save this class to YAML
     * 
     * @return 
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> saveMap = super.serialize();
        
        saveMap.put("xpStored", this.xpInStorage);
        return saveMap;
    }
    
}
