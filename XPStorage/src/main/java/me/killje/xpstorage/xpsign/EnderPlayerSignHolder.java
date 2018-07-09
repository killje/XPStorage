package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EnderPlayerSignHolder {

    private static final HashMap<String, ArrayList<EnderPlayerSign>> SIGNS = new HashMap<>();

    public static void addSignToPlayer(UUID player, final EnderPlayerSign sign) {
        UUID playerUUID = player;
        if (!SIGNS.containsKey(playerUUID.toString())) {
            SIGNS.put(playerUUID.toString(), new ArrayList<>());
        }
        SIGNS.get(playerUUID.toString()).add(sign);

    }

    public static ArrayList<EnderPlayerSign> getSignsForPlayer(UUID player) {
        return SIGNS.get(player.toString());
    }

    static void removeSignFromPlayer(UUID owner, EnderPlayerSign sign) {
        ArrayList<EnderPlayerSign> currentSigns = SIGNS.get(owner.toString());
        if (currentSigns == null) {
            return;
        }
        currentSigns.remove(sign);
    }

}
