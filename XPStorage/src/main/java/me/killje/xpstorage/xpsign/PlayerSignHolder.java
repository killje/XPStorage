package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Zolder
 */
public class PlayerSignHolder {
    
    private static final HashMap<String, ArrayList<PlayerSign>> SIGNS = new HashMap<>();
    
    public static void addSignToPlayer(UUID player, final PlayerSign sign) {
        UUID playerUUID = player;
        if (!SIGNS.containsKey(playerUUID.toString())) {
            SIGNS.put(playerUUID.toString(), new ArrayList<>());
        }
        SIGNS.get(playerUUID.toString()).add(sign);
        
    }
    
    public static ArrayList<PlayerSign> getSignsForPlayer(UUID player) {
        return SIGNS.get(player.toString());
    }

    static void removeSignFromPlayer(UUID owner, PlayerSign sign) {
        ArrayList<PlayerSign> currentSigns = SIGNS.get(owner.toString());
        if (currentSigns == null) {
            return;
        }
        currentSigns.remove(sign);
    }
    
}
