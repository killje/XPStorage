package me.killje.xpstorage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.block.Sign;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public final class InteractTimeout {

    private static final Map<UUID, ArrayList<Sign>> CURRENT_TIMEOUTS = new HashMap<>();

    public static void addInteractTimeout(UUID player, Sign sign) {

        if (!CURRENT_TIMEOUTS.containsKey(player)) {
            CURRENT_TIMEOUTS.put(player, new ArrayList<>());
        }
        CURRENT_TIMEOUTS.get(player).add(sign);
        PluginUtils.runTask(new Runnable() {
            @Override
            public void run() {
                CURRENT_TIMEOUTS.get(player).remove(sign);
            }
        });
    }
    
    public static boolean hasInteractTimeout(UUID player, Sign sign) {
        if (!CURRENT_TIMEOUTS.containsKey(player)) {
            return false;
        }
        
        return CURRENT_TIMEOUTS.get(player).contains(sign);
    }

}
