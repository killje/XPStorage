package me.killje.xpstorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

@Deprecated
/**
 * Old implementation of xpSigns. This is used to convert to the new signs
 *
 * Do not use this for anything else
 *
 * @author Patrick Beuks (killje)
 */
public class SignSaver implements ConfigurationSerializable {

    /**
     * The sign information
     */
    private final Map<String, Object> sign;

    /**
     * Creates a xpSign
     *
     * @param block The sign block
     * @param uuid  The person making the sign
     */
    public SignSaver(Block block, String uuid) {
        sign = new HashMap<>();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        String world = block.getWorld().getUID().toString();
        sign.put("x", x);
        sign.put("y", y);
        sign.put("z", z);
        sign.put("world", world);
        sign.put("ownerUuid", uuid);
    }

    /**
     * Loads a sign from file. Do not use this other than loading signs from
     * file
     *
     * @param map The sign information
     */
    public SignSaver(Map<String, Object> map) {
        sign = map;
    }

    /**
     * Get the location from the sign
     *
     * @return The sign location
     */
    public Location getLocation() {
        World world = Bukkit.getWorld(UUID.fromString(
                (String) sign.get("world")
        ));

        return new Location(world, (int) sign.get("x"), (int) sign.get("y"),
                (int) sign.get("z"));
    }

    /**
     * Gets the owner uuid of the sign
     *
     * @return The owner uuid as string
     */
    public String getOwnerUuid() {
        return (String) sign.get("ownerUuid");
    }

    /**
     * Gets the world of the sign
     *
     * @return The world
     */
    public World getWorld() {
        return Bukkit.getWorld(UUID.fromString((String) sign.get("world")));
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> serialize() {
        return sign;
    }
}
