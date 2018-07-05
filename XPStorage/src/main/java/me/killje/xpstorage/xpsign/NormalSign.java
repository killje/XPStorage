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
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
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
    }
    
    @Override
    protected void setNewXp(int xpInStorage) {
        this.xpInStorage = xpInStorage;
    }

    @Override
    public int getCurrentXp() {
        return xpInStorage;
    }
    
    public void onSignWrite () {
        updateSign();
    }
    
    @Override
    protected String getSignText() {
        Map<String, String> replacement = new HashMap<>();
        replacement.put("PLAYER_NAME", getSaveName(Bukkit.getOfflinePlayer(getOwner()).getName()));
        return XPStorage.getGuiSettings().getText("normalSignText", replacement);
    }

    @Override
    public String signType() {
        return XPStorage.getGuiSettings().getText("normalSignType");
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
        allXpOut(playerWhoDestroys);
        return true;
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
    
    /**
     * SHOULD ONLY BE USED FOR CONVERTING FROM OLD SIGNS
     * @param xp 
     */
    public void setXP(int xp) {
        this.xpInStorage = xp;
    }
    
}
