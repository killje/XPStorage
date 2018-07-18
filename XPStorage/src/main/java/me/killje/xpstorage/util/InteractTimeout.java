package me.killje.xpstorage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import org.bukkit.block.Sign;

/**
 * Because with the offhand it is possible to interact twice with a block we
 * want to be able to escape that so we need a very small timeout when
 * interacting
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public final class InteractTimeout {

    /**
     * Map of the timeouts currently active
     */
    private static final Map<UUID, ArrayList<Sign>> CURRENT_TIMEOUTS
            = new HashMap<>();

    /**
     * Add a timeout for the player and current sign
     *
     * @param player The player interacting
     * @param sign   The sign being interact with
     */
    public static void addInteractTimeout(UUID player, Sign sign) {

        if (!CURRENT_TIMEOUTS.containsKey(player)) {
            CURRENT_TIMEOUTS.put(player, new ArrayList<>());
        }
        CURRENT_TIMEOUTS.get(player).add(sign);
        XPStorage.getPluginUtil().runTask(new Runnable() {
            @Override
            public void run() {
                CURRENT_TIMEOUTS.get(player).remove(sign);
            }
        });
    }

    /**
     * Check if for the given player and sign there is a timeout running
     *
     * @param player The player to check
     * @param sign   The sign to check for
     *
     * @return True if there is a timeout running for the player and the sign
     */
    public static boolean hasInteractTimeout(UUID player, Sign sign) {
        if (!CURRENT_TIMEOUTS.containsKey(player)) {
            return false;
        }

        return CURRENT_TIMEOUTS.get(player).contains(sign);
    }

}
