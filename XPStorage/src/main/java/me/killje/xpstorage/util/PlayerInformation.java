package me.killje.xpstorage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import me.killje.spigotgui.util.clsConfiguration;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.group.GroupRights.Right;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.LocalPlayerSign;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.HumanEntity;

@SuppressWarnings("ResultOfObjectAllocationIgnored")
/**
 * This class stores information about the player and it preferences
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PlayerInformation implements ConfigurationSerializable {

    /**
     * Map containing the player information by uuid.toString()
     */
    private static final HashMap<String, PlayerInformation> PLAYER_INFORMATION
            = new HashMap<>();

    /**
     * Config file where player information is saved
     */
    private static final clsConfiguration PLAYER_INFORMATION_CONFIG
            = new clsConfiguration(XPStorage.getPluginUtil().getPlugin(),
                    "playerInformation.yml");

    /**
     * Create a periodic back save of the player information
     */
    static {
        new PlayerInformationPeriodSaver();
    }

    /**
     * This class saves all the information every once in a while.
     */
    private static class PlayerInformationPeriodSaver implements Runnable {

        @SuppressWarnings("LeakingThisInConstructor")
        /**
         * Creates a async task to save player information
         */
        public PlayerInformationPeriodSaver() {
            int Interval = 1200 * XPStorage.getPluginUtil().getConfig()
                    .getInt("backupSaveIntervalMinutes");
            XPStorage.getPluginUtil()
                    .runTaskTimerAsynchronously(this, Interval, Interval);
        }

        @Override
        /**
         * {@inheritDoc}
         */
        public void run() {
            PlayerInformation.savePlayerInformation();
        }

    }

    /**
     * Creates a new player information from file
     *
     * This should only be used as deserialization from file and never be called
     * from code
     *
     * @param player The playerinformation object
     *
     * @return The created player information
     */
    public static PlayerInformation deserialize(Map<String, Object> player) {
        if (!XPStorage.getPluginUtil().getPlugin().isEnabled()) {
            return null;
        }

        UUID playerUUID = UUID.fromString((String) player.get("player"));

        int xp = (int) player.get("xp");

        PlayerInformation playerInformation = getPlayerInformation(playerUUID);

        if (player.containsKey("groupRights")) {
            List<GroupRights> groupRightsList
                    = (List<GroupRights>) player.get("groupRights");

            for (GroupRights groupRights : groupRightsList) {
                playerInformation.addGroupRights(
                        groupRights.getGroupId(), groupRights
                );
            }
        }
        playerInformation.setPlayerXpAmount(xp);

        playerInformation.isMessage(
                (boolean) player.getOrDefault("getMessage", true)
        );

        try {
            Class<? extends AbstractXpSign> defaultSign
                    = (Class<? extends AbstractXpSign>) Class.forName(
                            (String) player.get("defaultSign")
                    );

            playerInformation.setDefaultSign(defaultSign);
        } catch (ClassNotFoundException ex) {
            XPStorage.getPluginUtil().getLogger().log(Level.SEVERE, null, ex);
            playerInformation.setDefaultSign(LocalPlayerSign.class);
        }

        return playerInformation;
    }

    /**
     * Get a player information object from a entity
     *
     * @param player The player to retrieve the information from
     *
     * @return The player information for the player
     */
    public static PlayerInformation getPlayerInformation(HumanEntity player) {
        return getPlayerInformation(player.getUniqueId());
    }

    /**
     * Get a player information object from a uuid
     *
     * @param player The uuid of the player to retrieve the information from
     *
     * @return The player information for the player
     */
    public static PlayerInformation getPlayerInformation(UUID player) {
        PlayerInformation playerInformation = null;
        if (PLAYER_INFORMATION.containsKey(player.toString())) {
            playerInformation = PLAYER_INFORMATION.get(player.toString());
        }

        if (playerInformation == null) {
            return new PlayerInformation(player);
        }

        return playerInformation;
    }

    /**
     * Load the player information from file
     */
    public static void loadPlayerInformation() {
        PLAYER_INFORMATION_CONFIG.GetConfig().getList("playerInformation");
    }

    /**
     * Save the player information to file
     */
    public static void savePlayerInformation() {
        PLAYER_INFORMATION_CONFIG.GetConfig().set("playerInformation",
                new ArrayList<>(PLAYER_INFORMATION.values()));
        PLAYER_INFORMATION_CONFIG.SaveConfig();
    }
    /**
     * The default sign class when creating new signs
     */
    private Class<? extends AbstractXpSign> defaultSign = LocalPlayerSign.class;
    /**
     * Whether or not to receive a message when creating a sign or interacting
     * with zero xp
     */
    private boolean getMessage = true;
    /**
     * The rights the player has in different groups
     */
    private final HashMap<UUID, GroupRights> groupRights = new HashMap<>();
    /**
     * The groups the player is part of
     */
    private final ArrayList<Group> groups = new ArrayList<>();
    /**
     * The uuid of the player
     */
    private final UUID player;
    /**
     * The xp stored in the players Ender player signs
     */
    private int xp = 0;

    /**
     * Creates a new player information for the player
     *
     * @param player The player you want to create a new player information for
     */
    public PlayerInformation(UUID player) {
        this.player = player;
        PLAYER_INFORMATION.put(player.toString(), this);
    }

    /**
     * Add a group to the player
     *
     * @param group The group to add
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Adds a right to the player for the given group
     *
     * @param groupId     The group you want to add rights to
     * @param groupRights The rights to add
     */
    public void addGroupRights(UUID groupId, GroupRights groupRights) {
        this.groupRights.put(groupId, groupRights);
    }

    /**
     * Gets the default sign a player to player wants to use when creating a new
     * sign
     *
     * @return The class of the default sign
     */
    public Class<? extends AbstractXpSign> getDefaultSign() {
        if (defaultSign == null) {
            defaultSign = LocalPlayerSign.class;
        }
        return defaultSign;
    }

    /**
     * Sets the default sign to use when a player creates a new sign
     *
     * @param defaultSign The sign class to use
     */
    public void setDefaultSign(Class<? extends AbstractXpSign> defaultSign) {
        this.defaultSign = defaultSign;
    }

    /**
     * Gets the rights the player has for the given group
     *
     * @param groupId The group you want to check the player rights for
     *
     * @return The rights the player has for the group
     */
    public GroupRights getGroupRights(UUID groupId) {
        if (!groupRights.containsKey(groupId)) {
            groupRights.put(groupId, new GroupRights(groupId));
        }
        return groupRights.get(groupId);
    }

    /**
     * Gets the groups the player has the given right in
     *
     * @param Right The right to check for in the groups
     *
     * @return The groups with the given right
     */
    public ArrayList<Group> getGroups(Right Right) {
        ArrayList<Group> returnGroups = new ArrayList<>();
        for (Group group : groups) {
            if (group == null) {
                continue;
            }
            if (getGroupRights(group.getGroupUuid()).hasRight(Right)) {
                returnGroups.add(group);
            }
        }
        return returnGroups;
    }

    /**
     * List of all the groups the player is in
     *
     * @return List of all groups
     */
    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * The Ender player xp currently stored
     *
     * @return The amount of xp stored
     */
    public int getPlayerXpAmount() {
        return xp;
    }

    /**
     * Set the xp of the Ender player storage to the given amount
     *
     * @param xp The xp to set the Ender player storage to
     */
    public void setPlayerXpAmount(int xp) {
        this.xp = xp;
    }

    /**
     * Gets the player uuid
     *
     * @return The uuid of the player
     */
    public UUID getUUID() {
        return player;
    }

    /**
     * Gets the player uuid as string
     *
     * @return The uuid as string
     */
    public String getUUIDAsString() {
        return player.toString();
    }

    /**
     * Gives back whether or not the player wants to receive messages
     *
     * @return True if the player want to receive message, false otherwise
     */
    public boolean isMessage() {
        return getMessage;
    }

    /**
     * Set whether or not the player wants to receive messages.
     *
     * @param getMessage True if the player wants to receive messages, false
     *                   otherwise
     */
    public void isMessage(boolean getMessage) {
        this.getMessage = getMessage;
    }

    /**
     * Removes group rights to a group
     *
     * @param groupId The group you want to remove rights to
     */
    public void removeGroupRights(UUID groupId) {
        if (groupRights.containsKey(groupId)) {
            groupRights.remove(groupId);
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> serialize() {
        HashMap<String, Object> playerInformation = new HashMap<>();
        playerInformation.put("xp", xp);
        playerInformation.put("player", this.player.toString());
        playerInformation.put("groupRights",
                new ArrayList<>(groupRights.values()));
        playerInformation.put("getMessage", getMessage);
        playerInformation.put("defaultSign", defaultSign.getCanonicalName());
        return playerInformation;
    }

}
