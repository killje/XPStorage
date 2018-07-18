package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class holds a list of all ender signs a player has created
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EnderPlayerSignHolder {

    /**
     * The signs a player has created stored by player uuid.toString()
     */
    private static final HashMap<String, ArrayList<EnderPlayerSign>> SIGNS
            = new HashMap<>();

    /**
     * Add a ender player sign to the list of the given player
     *
     * @param player The player to add the files to
     * @param sign   The sign to add
     */
    public static void addSignToPlayer(
            UUID player, final EnderPlayerSign sign) {

        UUID playerUUID = player;
        if (!SIGNS.containsKey(playerUUID.toString())) {
            SIGNS.put(playerUUID.toString(), new ArrayList<>());
        }
        SIGNS.get(playerUUID.toString()).add(sign);

    }

    /**
     * Get the list of ender player signs for the given player
     *
     * @param player The player to lookup
     *
     * @return The list of signs belonging to the player
     */
    public static ArrayList<EnderPlayerSign> getSignsForPlayer(UUID player) {
        return SIGNS.get(player.toString());
    }

    /**
     * Removes a ender player sign from the player
     *
     * @param owner The player to remove it from
     * @param sign  The sign to remove
     */
    static void removeSignFromPlayer(UUID owner, EnderPlayerSign sign) {
        ArrayList<EnderPlayerSign> currentSigns = SIGNS.get(owner.toString());
        if (currentSigns == null) {
            return;
        }
        currentSigns.remove(sign);
    }

}
