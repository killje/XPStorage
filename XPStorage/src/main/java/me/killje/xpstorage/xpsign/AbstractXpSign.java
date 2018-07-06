package me.killje.xpstorage.xpsign;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.clsConfiguration;
import me.killje.xpstorage.Update;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.util.ExperienceManager;
import me.killje.xpstorage.util.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class AbstractXpSign implements ConfigurationSerializable {

    /**
     *
     * Inner class in charge of periodically updating the save file
     */
    private static class AbstractXpSignSaver implements Runnable {

        private static BukkitTask lastTask = null;

        /**
         * Making sure only one task is running
         */
        public AbstractXpSignSaver() {
            if (lastTask != null) {
                lastTask.cancel();
            }
            lastTask = XPStorage.getPluginUtil().runTaskAsynchronously(this);
        }

        /**
         * Save the signs to the config
         */
        @Override
        public void run() {
            XP_SIGN_CONFIG.GetConfig().set("xpSigns", signsList);
            XP_SIGN_CONFIG.SaveConfig();
        }

    }

    public static AbstractXpSign createSign(Class<? extends AbstractXpSign> signClass, Sign sign, UUID player) {
        try {
            Constructor<? extends AbstractXpSign> constructor = signClass.getConstructor(Sign.class, UUID.class);
            return constructor.newInstance(sign, player);
        } catch (NoSuchMethodException
                | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            XPStorage.getPluginUtil().getLogger().log(Level.SEVERE, null, ex);
        }
        return new NormalSign(sign, player);
    }

    /**
     * Enum for load errors
     */
    public enum LoadError {
        NONE,
        NO_SIGN,
        BAD_LOCATION,
        NO_PLAYER,
        NO_GROUP;
    }

    /**
     * Storing load error state
     */
    protected LoadError loadError;

    /**
     * List of all signs for saving purposes
     */
    private static List<AbstractXpSign> signsList = new Stack<>();

    /**
     * Current sign
     */
    private final Sign sign;

    /**
     * Current sign
     */
    private final XpSignFacingBlock signFacingBlock;

    /**
     * Sign config
     */
    private static final clsConfiguration XP_SIGN_CONFIG = new clsConfiguration(XPStorage.getPluginUtil().getPlugin(), "xpSigns.yml");

    /**
     * Location of the sign in the world
     */
    private Location location;

    /**
     * Load information, only used for debug purposes when sign could not be
     * loaded
     */
    private final Map<String, Object> loadInformation;

    /**
     * Creating a sign from scratch
     *
     * @param sign The sign block that has been created
     */
    public AbstractXpSign(Sign sign) {

        this.sign = sign;
        this.loadInformation = null;
        // Check if the sign is real
        if (sign == null) {
            loadError = LoadError.NO_SIGN;
            this.signFacingBlock = null;
            return;
        }
        BlockFace facingDirection = ((org.bukkit.material.Sign) sign.getData()).getAttachedFace();
        this.signFacingBlock = new XpSignFacingBlock(sign.getBlock().getRelative(facingDirection), this);

        loadError = LoadError.NONE;
        // Dubble check if the block has not already initiated a XPSign that has not been removed properly
        if (sign.getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
            sign.getBlock().removeMetadata("XP_STORAGE_XPSIGN", XPStorage.getPluginUtil().getPlugin());
        }
        // Set metadata of the sign
        sign.getBlock().setMetadata("XP_STORAGE_XPSIGN", new FixedMetadataValue(XPStorage.getPluginUtil().getPlugin(), this));

        // Add sign to list of signs
        addSign(this);
    }

    /**
     * Initiate sign from storage file
     *
     * Not for creating new signs!
     *
     * @param sign The sign information
     */
    public AbstractXpSign(Map<String, Object> sign) {
        // Try to get world
        World world = Bukkit.getWorld(UUID.fromString((String) sign.get("world")));
        PluginUtil pluginUtil = XPStorage.getPluginUtil();
        this.loadInformation = sign;
        if (world == null) {
            this.loadError = LoadError.BAD_LOCATION;
            this.sign = null;
            this.signFacingBlock = null;
            return;
        }
        // Try to get location
        Location location = new Location(world, (int) sign.get("x"), (int) sign.get("y"), (int) sign.get("z"));
        if (location.getBlock() == null || !isSign(location.getBlock().getType())) {
            if (location.getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
                location.getBlock().removeMetadata("XP_STORAGE_XPSIGN", pluginUtil.getPlugin());
            }
            this.loadError = LoadError.NO_SIGN;
            this.sign = null;
            this.location = location;
            this.signFacingBlock = null;
            return;
        }
        // Try to get player and sign
        Sign signBlock = (Sign) location.getBlock().getState();

        BlockFace facingDirection = ((org.bukkit.material.Sign) signBlock.getData()).getAttachedFace();
        this.signFacingBlock = new XpSignFacingBlock(signBlock.getBlock().getRelative(facingDirection), this);

        this.sign = signBlock;
        this.loadError = LoadError.NONE;
        // Dubble check if the block has not already initiated a XPSign that has not been removed properly
        if (signBlock.getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
            signBlock.getBlock().removeMetadata("XP_STORAGE_XPSIGN", pluginUtil.getPlugin());
        }
        // Set metadata of the sign
        signBlock.getBlock().setMetadata("XP_STORAGE_XPSIGN", new FixedMetadataValue(pluginUtil.getPlugin(), this));
        // Add sign to list of signs
        addSign(this);
    }

    /**
     * Add sign to list of signs
     *
     * @param xpSign The created xpSign
     */
    private static void addSign(AbstractXpSign xpSign) {
        signsList.add(xpSign);
        // Save config
        saveSigns();
    }

    /**
     * Remove the specified xpSign
     *
     * @param xpSign
     */
    public static void removeSign(AbstractXpSign xpSign) {
        signsList.remove(xpSign);
        // Save config
        saveSigns();
    }

    /**
     * Location of the sign block in the world
     *
     * @return The location
     */
    private Location getLocation() {
        return location;
    }

    /**
     * Save the signs, when initializing the signs will not be directly saved
     *
     * @param overrideInitCheck When initializing the signs are saved once, this
     * boolean allows that, should not be used in normal saving
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void saveSigns(boolean overrideInitCheck) {
        if (!overrideInitCheck && XPStorage.getPluginUtil().getPlugin().isEnabled()) {
            return;
        }
        if (overrideInitCheck) {
            XP_SIGN_CONFIG.GetConfig().set("xpSigns", signsList);
            XP_SIGN_CONFIG.SaveConfig();
        } else {
            new AbstractXpSignSaver();
        }

    }

    /**
     * Saving signs to config
     */
    public static void saveSigns() {
        saveSigns(false);
    }

    /**
     * Load signs from config. Should only be done while initializing the plugin
     */
    public static void loadSigns() {
        Logger logger = XPStorage.getPluginUtil().getLogger();
        // Boolean to keep track if all signs have been converted
        boolean dirty = false;

        // Check if there is a signs file (old format)
        if (XPStorage.getSignConfig().GetConfig().contains("Signs")) {

            if (!XP_SIGN_CONFIG.GetConfig().contains("xpSigns")) {
                // Update to new format
                new Update(XPStorage.getSignConfig().GetConfig());
                XPStorage.getSignConfig().SaveConfig();
                // Save file after completion
                dirty = true;
            } else {
                List<?> oldSigns = XPStorage.getSignConfig().GetConfig().getList("Signs");
                if (oldSigns != null && !oldSigns.isEmpty()) {
                    XPStorage.getSignConfig().GetConfig().set("skiped", oldSigns);
                }
                // Unset the old signs file
                XPStorage.getSignConfig().GetConfig().set("Signs", null);
                // Load the signs
                signsList = (List<AbstractXpSign>) XP_SIGN_CONFIG.GetConfig().getList("xpSigns");
            }
        } else {
            // Load the signs
            signsList = (List<AbstractXpSign>) XP_SIGN_CONFIG.GetConfig().getList("xpSigns");
        }

        List<Map<?, ?>> failedSigns = (List<Map<?, ?>>) XP_SIGN_CONFIG.GetConfig().getMapList("failedSigns");
        if (failedSigns == null) {
            failedSigns = new ArrayList<>();
        }

        if (signsList == null) {
            signsList = new ArrayList<>();
        }
        // Go over the signs to see if they all have been loaded properly
        for (Iterator<AbstractXpSign> it = signsList.iterator(); it.hasNext();) {
            AbstractXpSign xpSign = it.next();
            // SIGNS HAVE TO BE INITIALIZED.
            // It it is not, then there is somthing wrong in the file or code,
            // report imidialty
            if (xpSign == null) {
                logger.log(Level.SEVERE, "\u001B[31mCould not parse sign. THIS IS A SEVERE ERROR. This plugin will disable itself to prevent it from destroying itself. Pleas read the console to find out what caused this bug.\u001b[m");
                try {
                    // Proper unloading of the plugin
                    XPStorage.getPluginUtil().unloadPlugin();
                } catch (NoSuchFieldException | IllegalAccessException | NullPointerException ex) {
                    // Backup if something fails
                    Bukkit.getPluginManager().disablePlugin(XPStorage.getPluginUtil().getPlugin());
                    Logger.getLogger(AbstractXpSign.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
            // Get the error
            LoadError failed = xpSign.getError();
            // Check the errors and print out relevent information
            switch (failed) {
                default:
                case NONE:
                    // No error found, break
                    break;
                case NO_SIGN:
                    // No sign found on location, report location
                    Location location = xpSign.getLocation();
                    logger.log(Level.WARNING, "Sign does not exsist anymore at: x={0}, y={1}, z={2}", new Object[]{location.getX(), location.getY(), location.getZ()});
                    it.remove();
                    failedSigns.add(xpSign.getLoadInformation());
                    dirty = true;
                    break;
                case NO_GROUP:
                    // No group found on uuid
                    logger.log(Level.WARNING, "Sign contains a group that does not exsists");
                    it.remove();
                    failedSigns.add(xpSign.getLoadInformation());
                    dirty = true;
                    break;
                case BAD_LOCATION:
                    // The location could not be found (is the proper world loaded?)
                    Map<String, Object> loadInformation = xpSign.getLoadInformation();
                    logger.log(Level.WARNING, "Could not generate a location from file: world={0}, x={1}, y={2}, z={3}", new Object[]{(String) loadInformation.get("world"), (int) loadInformation.get("x"), (int) loadInformation.get("y"), (int) loadInformation.get("z")});
                    it.remove();
                    failedSigns.add(xpSign.getLoadInformation());
                    dirty = true;
                    break;
                case NO_PLAYER:
                    // Could not assign player, it does not exsist anymore?
                    Map<String, Object> sign = xpSign.serialize();
                    logger.log(Level.WARNING, "Could not retrive player: uuid={0}", new Object[]{(String) sign.get("ownerUuid")});
                    it.remove();
                    failedSigns.add(xpSign.getLoadInformation());
                    dirty = true;
                    break;
            }
        }

        if (dirty) {
            if (!failedSigns.isEmpty()) {
                XP_SIGN_CONFIG.GetConfig().set("failedSigns", failedSigns);
                XP_SIGN_CONFIG.SaveConfig();
            }
            // Save signs if changes have been found
            saveSigns(true);
        }
    }

    /**
     * Get the name for the signs, max length is 15.
     *
     * @param player The player to get the name from
     * @return The signs ready name
     */
    public static String getSaveName(Player player) {
        return getSaveName(player.getName());
    }

    /**
     * Get the name for the signs, max length is 15.
     *
     * @param name
     * @return
     */
    public static String getSaveName(String name) {
        if (name == null) {
            name = "";
        }
        if (name.length() > 15) {
            return name.substring(0, 15);
        } else {
            return name;
        }
    }

    /**
     * Returns if a material is a sign
     *
     * @param material
     * @return True if it is a sign
     */
    public static boolean isSign(Material material) {
        return material == Material.WALL_SIGN
                || material == Material.SIGN_POST
                || material == Material.SIGN;
    }

    public static void destroyMetaDatas() {
        for (AbstractXpSign abstractXpSign : signsList) {
            if (abstractXpSign.getSign().getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
                abstractXpSign.getSign().getBlock().removeMetadata("XP_STORAGE_XPSIGN", XPStorage.getPluginUtil().getPlugin());
            }
            if (abstractXpSign.getSignFacingBlock().getFacingBlock().hasMetadata("XP_STORAGE_XPSIGNFACEBLOCK")) {
                abstractXpSign.getSign().getBlock().removeMetadata("XP_STORAGE_XPSIGNFACEBLOCK", XPStorage.getPluginUtil().getPlugin());
            }
        }
    }

    /**
     * Function to save this class to YAML
     *
     * @return
     */
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> serialized = new HashMap<>();
        serialized.put("x", sign.getX());
        serialized.put("y", sign.getY());
        serialized.put("z", sign.getZ());
        serialized.put("world", sign.getWorld().getUID().toString());
        serialized.put("ownerUuid", getOwner().toString());
        return serialized;
    }

    /**
     * This function should be implemented to save the amount of xp on the sign
     *
     * @param xpInStorage The new XP amount it has to be set to
     */
    protected abstract void setNewXp(int xpInStorage);

    /**
     * The function should return the total amount of XP on the sign
     *
     * @return The amount of XP
     */
    public abstract int getCurrentXp();

    /**
     * Retrieves the text second line of text for the sign
     *
     * @return The text to display on the sign
     */
    protected String getSecondLine() {
        return "";
    }

    ;

    /**
     * Retrieves the text for the sign
     * 
     * @return The text to display on the sign
     */
    protected abstract String getSignText();

    /**
     * A human readable name for the sign.
     *
     * @return
     */
    public abstract String signType();

    /**
     * Checks if the given player can add/remove xp from the sign
     *
     * @param player
     * @return
     */
    public abstract boolean hasAccess(UUID player);

    /**
     * Returns the owner of the sign
     *
     * @return The uuid of the owner
     */
    public abstract UUID getOwner();

    /**
     * Sets the owner of the sign
     *
     * @param newOwner
     */
    public abstract void setOwner(UUID newOwner);

    /**
     * Whether or not this is a group sign. This is so the AbstractSharedSign
     * can add additional functions
     *
     * @return
     */
    public boolean isGroupSign() {
        return false;
    }

    /**
     * Updates a destroyed XP sign to this sign.
     */
    public void changeSign() {
        updateSign();
    }

    /**
     * Increases the XP on the sign with the current selected amount
     *
     * @param player The current player increasing the XP of the sign
     */
    public void increaseXp(Player player) {
        increaseXp(player, 1);
    }

    /**
     * Increases the XP on the sign with the current selected amount
     *
     * @param player The current player increasing the XP of the sign
     * @param levelsToIncrease The amount of levels to increase
     */
    public void increaseXp(Player player, int levelsToIncrease) {
        ExperienceManager experienceManager = new ExperienceManager(player);
        boolean levelCheck = experienceManager.getTotalExperience() == player.getExpToLevel();

        int levelToCompare = player.getLevel();
        if (levelCheck && levelToCompare > 0) {
            levelToCompare--;
        }

        levelToCompare -= levelsToIncrease - 1;

        if (levelToCompare < 0) {
            levelToCompare = 0;
        }

        int xpToIncrease = experienceManager.getTotalExperience() - experienceManager.getExperienceForLevel(levelToCompare);

        increaseXpSign(player, xpToIncrease);
    }

    /**
     * Increases the XP on the sign with the current selected amount
     *
     * @param player The current player increasing the XP of the sign
     * @param xpToIncrease The amount of player levels to increase the sign with
     */
    private void increaseXpSign(Player player, int xpToIncrease) {
        // Use the experience manager to accuratly update the amount of XP
        ExperienceManager experienceManager = new ExperienceManager(player);
        int currentXp = getCurrentXp();
        if (experienceManager.getTotalExperience()< xpToIncrease) {
            setNewXp(currentXp + experienceManager.getTotalExperience());
            experienceManager.setExperience(0);
        } else if (currentXp + xpToIncrease > 210000000) {
            player.sendMessage(XPStorage.getGuiSettings().getText("maximumStorageMessage"));
            return;
        } else {
            setNewXp(currentXp + xpToIncrease);
            experienceManager.changeExperience(0 - xpToIncrease);
        }
        // Update the text on the sign
        updateSign();
    }

    /**
     * Decreases the XP on the sign with the current selected amount
     *
     * @param player The current player decreasing the XP of the sign
     */
    public void decreaseXp(Player player) {
        decreaseXp(player, 1);
    }

    /**
     * Decreases the XP on the sign with the current selected amount
     *
     * @param player The current player decreasing the XP of the sign
     * @param levelsToDecrease The amount of player levels to decrease the sign
     * with
     */
    public void decreaseXp(Player player, int levelsToDecrease) {

        ExperienceManager experienceManager = new ExperienceManager(player);
        int xpToDecrease = experienceManager.getExperienceForLevel(player.getLevel() + levelsToDecrease) - experienceManager.getTotalExperience();

        decreaseXpSign(player, xpToDecrease);
    }

    /**
     * Decreases the XP on the sign with the current selected amount
     *
     * @param player The current player decreasing the XP of the sign
     * @param xpToDecrease The amount of xp to decrease
     */
    private void decreaseXpSign(Player player, int xpToDecrease) {
        // Use the experience manager to accuratly update the amount of XP
        ExperienceManager experienceManager = new ExperienceManager(player);
        int currentXp = getCurrentXp();
        if (currentXp < xpToDecrease) {
            experienceManager.changeExperience(currentXp);
            setNewXp(0);
        } else {
            experienceManager.changeExperience(xpToDecrease);
            setNewXp(currentXp - xpToDecrease);
        }
        // Update the text on the sign
        updateSign();
    }

    /**
     * Remove all the experience from the sign
     *
     * @param player
     */
    public void allXpOut(Player player) {
        decreaseXpSign(player, getCurrentXp());
    }

    /**
     * Add all the player experience to the sign
     *
     * @param player
     */
    public void allXpIn(Player player) {
        ExperienceManager experienceManager = new ExperienceManager(player);
        increaseXpSign(player, experienceManager.getTotalExperience());
    }

    /**
     * Get the sign block
     *
     * @return The sign block
     */
    public Sign getSign() {
        return sign;
    }

    /**
     * Get the sign block
     *
     * @return The sign block
     */
    public XpSignFacingBlock getSignFacingBlock() {
        return signFacingBlock;
    }

    /**
     * Update the amount of XP displayed
     */
    public final void updateSign() {
        sign.setLine(0, XPStorage.getGuiSettings().getText("xpStorageTag"));
        sign.setLine(1, getSecondLine());
        sign.setLine(2, getCurrentXp() + "");
        sign.setLine(3, getSignText());
        sign.update();
    }

    /**
     * The load error for this sign
     *
     * @return
     */
    public final LoadError getError() {
        return loadError;
    }

    /**
     * Remove the sign form the list
     *
     * @param playerWhoDestroys The player destroying the sign
     * @return
     */
    public boolean destroySign(Player playerWhoDestroys) {
        if (!canDestroySign(playerWhoDestroys)) {
            return false;
        }
        removeSign(this);
        if (sign.getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
            sign.getBlock().removeMetadata("XP_STORAGE_XPSIGN", XPStorage.getPluginUtil().getPlugin());
        }
        XpSignFacingBlock.removeFacingBlock(signFacingBlock);
        return true;
    }

    /**
     * Check if can be removed form the list
     *
     * @param playerWhoDestroys The player who is destroying the sign
     * @return
     */
    public boolean canDestroySign(Player playerWhoDestroys) {
        if (playerWhoDestroys == null) {
            return false;
        }
        return playerWhoDestroys.getUniqueId().equals(getOwner());
    }

    /**
     * Return debug information, only for printing errors
     *
     * @return
     */
    Map<String, Object> getLoadInformation() {
        return this.loadInformation;
    }

    public ArrayList<GuiElement> getAdditionalGuiElements(Player player) {
        return new ArrayList<>(0);
    }

}
