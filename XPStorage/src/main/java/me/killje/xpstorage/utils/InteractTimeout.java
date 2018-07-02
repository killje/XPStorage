package me.killje.xpstorage.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import org.bukkit.Bukkit;
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
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
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
