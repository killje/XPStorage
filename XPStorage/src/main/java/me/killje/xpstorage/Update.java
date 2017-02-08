package me.killje.xpstorage;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.NormalSign;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Zolder
 */
public class Update {
    
    public Update (FileConfiguration config) {
        
        List<SignSaver> signsList = (List<SignSaver>) config.getList("Signs");
        
        for (SignSaver signSaver : signsList) {
            Location location = signSaver.getLocation();
            if (location != null) {
                Block block = location.getBlock();
                if (block == null || !AbstractXpSign.isSign(block.getType())) {
                    XPStorage.getInstance().getLogger().log(Level.WARNING, "Sign does not exsist anymore at: x={0}, y={1}, z={2}", new Object[]{location.getX(), location.getY(), location.getZ()});
                    continue;
                }
                Sign sign = (Sign) block.getState();
                NormalSign normalSign = new NormalSign(sign, UUID.fromString(signSaver.getOwnerUuid()));
                normalSign.getXpFromSign();
                
            } else {
                Map<String, Object> sign = signSaver.serialize();
                XPStorage.getInstance().getLogger().log(Level.WARNING, "Could not generate a location from file: world={0}, x={1}, y={2}, z={3}", new Object[]{(String) sign.get("world"), (int) sign.get("x"), (int) sign.get("y"), (int) sign.get("z")});
            }
        }
        
        config.set("Signs", null);
    }
    
}
