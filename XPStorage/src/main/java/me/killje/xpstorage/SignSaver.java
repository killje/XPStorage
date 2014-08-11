package me.killje.xpstorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author Patrick Beuks (killje)
 */
public class SignSaver implements ConfigurationSerializable {

    private final Map<String, Object> sign;
    
    public SignSaver(Block block, String uuid){
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

    public SignSaver(Map<String, Object> map) {
        sign = map;
    }
    
    @Override
    public Map<String, Object> serialize() {
        return sign;
    }
    
    public String getOwnerUuid(){
        return (String) sign.get("ownerUuid");
    }
    
    public Location getLocation(){
        World world = Bukkit.getWorld(UUID.fromString((String) sign.get("world")));
        return new Location(world, (int) sign.get("x"), (int) sign.get("y"), (int) sign.get("z"));
    }
}
