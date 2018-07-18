package me.killje.xpstorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import me.killje.xpstorage.util.PluginUtil;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.LocalPlayerSign;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * This updates the signs from the old format to the new storage signs
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class Update {

    /**
     * Updates the signs in the config to the new format
     *
     * @param config The config to load the old signs from
     */
    public Update(FileConfiguration config) {
        PluginUtil pluginUtil = XPStorage.getPluginUtil();
        List<SignSaver> signsList = (List<SignSaver>) config.getList("Signs");
        List<Map<?, ?>> failed = new ArrayList<>();
        int worldNotFound = 0;
        int signDoesntExist = 0;
        int xpSignAlreadyExists = 0;
        int failedToParse = 0;
        int locationFailed = 0;
        int signsCreated = 0;

        Map<String, Object> serialize;
        if (signsList == null) {
            signsList = new ArrayList<>();
        }
        for (SignSaver signSaver : signsList) {
            serialize = signSaver.serialize();
            if (signSaver.getWorld() == null) {
                serialize.put("Reason", "World not found");
                worldNotFound++;
                failed.add(serialize);
                pluginUtil.getLogger().log(Level.WARNING,
                        "World not found: world uuid: {0}", new Object[]{
                            serialize.get("world")});
                continue;
            }
            Location location = signSaver.getLocation();
            if (location != null) {
                Block block = location.getBlock();
                if (block == null || !AbstractXpSign.isSign(block.getType())) {
                    serialize.put("Reason", "Sign does not exist anymore");
                    signDoesntExist++;
                    failed.add(serialize);
                    pluginUtil.getLogger().log(Level.WARNING,
                            "Sign does not exist anymore at:"
                            + " world={0}, x={1}, y={2}, z={3}",
                            new Object[]{serialize.get("world"),
                                location.getX(), location.getY(),
                                location.getZ()});
                    continue;
                }
                if (block.hasMetadata("XP_STORAGE_XPSIGN")) {
                    serialize.put("Reason",
                            "XPSign was already created. Skipping");
                    xpSignAlreadyExists++;
                    failed.add(serialize);
                    pluginUtil.getLogger().log(Level.WARNING,
                            "XPSign was already created. Skipping");
                    continue;
                }
                try {
                    Sign sign = (Sign) block.getState();
                    int xpInStorage = Integer.parseInt(sign.getLine(1));
                    signsCreated++;

                    LocalPlayerSign localPlayerSign = new LocalPlayerSign(sign,
                            UUID.fromString(signSaver.getOwnerUuid()));
                    localPlayerSign.setXP(xpInStorage);
                } catch (NumberFormatException ex) {
                    failedToParse++;
                    serialize.put("Reason",
                            "Could not parse the xp amount on the sign");
                    failed.add(serialize);
                    pluginUtil.getLogger().log(Level.WARNING,
                            "Could not parse the xp amount on the sign");
                }

            } else {
                serialize.put("Reason",
                        "Could not generate a location from file");
                locationFailed++;
                failed.add(serialize);
                pluginUtil.getLogger().log(Level.WARNING,
                        "Could not generate a location from file:"
                        + " world={0}, x={1}, y={2}, z={3}",
                        new Object[]{(String) serialize.get("world"),
                            (int) serialize.get("x"), (int) serialize.get("y"),
                            (int) serialize.get("z")});
            }
        }

        pluginUtil.getLogger().log(Level.INFO,
                "Updating complete."
                + " Stats: Succesfull ({0}), "
                + "Location failed ({1}),"
                + " Sign does not exist ({2})",
                new Object[]{signsCreated, locationFailed, signDoesntExist});
        pluginUtil.getLogger().log(Level.INFO,
                "XPStorage already created ({0}),"
                + " Failed to parse xp ({1}),"
                + " World not found ({2})",
                new Object[]{
                    xpSignAlreadyExists,
                    failedToParse,
                    worldNotFound
                });

        config.set("Signs", null);
        if (!failed.isEmpty()) {
            config.set("Failed", failed);
        }
    }

}
