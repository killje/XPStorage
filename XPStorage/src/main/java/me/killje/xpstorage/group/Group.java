package me.killje.xpstorage.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.desht.dhutils.ExperienceManager;
import me.killje.spigotgui.util.clsConfiguration;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.XpSignFacingBlock;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("ResultOfObjectAllocationIgnored")
/**
 * Serializable group object
 *
 * This group object handles the groups for group signs. It tracks the owner,
 * players connected to the group and the permissions as well as the xp, group
 * name and signs
 *
 * @author Patrick Beuks (killje) <code@beuks.net>
 */
public class Group implements ConfigurationSerializable {


    /*
     * ------------------------Static-variables--------------------------------
     */
    /**
     * List of all the groups created
     *
     * This is used to keep track for saving purposes
     */
    private final static Map<String, Group> GROUPS = new HashMap<>();

    /**
     *
     */
    private static final clsConfiguration GROUP_CONFIG
            = new clsConfiguration(XPStorage.getPlugin(), "groups.yml");

    /*
     * ------------------------Static-methodes---------------------------------
     */
    /**
     * Create the backup saver
     */
    static {
        new groupPeriodSaver().init();
    }

    /**
     * Saves the group every specified amount of time
     */
    private static class groupPeriodSaver implements Runnable {

        /**
         * Initializes the period saver
         */
        private void init() {
            int interval = XPStorage.getPluginUtil().getConfig()
                    .getInt("backupSaveIntervalMinutes") * 1200;

            if (interval == 0) {
                return;
            }

            XPStorage.getPluginUtil()
                    .runTaskTimerAsynchronously(this, interval, interval);
        }

        /**
         * Saves all the groups
         */
        @Override
        public void run() {
            Group.saveGroups();
        }

    }

    /**
     * Gets a group by the uuid of the group
     *
     * If the uuid is not found null will be returned
     *
     * @param groupUUID The uuid of the group you want to get
     *
     * @return The group with the groupUUID or null if not found
     */
    public static Group getGroupFromUUID(UUID groupUUID) {
        if (!GROUPS.containsKey(groupUUID.toString())) {
            return null;
        }
        return GROUPS.get(groupUUID.toString());
    }

    /**
     * Load groups from file
     */
    public static void loadGroups() {
        GROUP_CONFIG.GetConfig().getList("groups");
    }

    /*
     * ------------------------Static-functions--------------------------------
     */
    /**
     * Saves the currently defined groups to the group config
     */
    public static void saveGroups() {
        GROUP_CONFIG.GetConfig()
                .set("groups", new ArrayList<>(GROUPS.values()));
        GROUP_CONFIG.SaveConfig();
    }
    /**
     * Group icon of the group
     *
     * This mainly for Ender group storage
     */
    private ItemStack groupIcon;

    /**
     * The UUID of the group
     */
    private final UUID groupId;
    /**
     * Group name of the group
     *
     * This mainly for Ender group storage
     */
    private String groupName = null;

    /**
     * The owner of the group
     */
    private UUID owner;
    /*
     * ------------------------Object-final-variables--------------------------
     */
    /**
     * List of players in the group
     */
    private final Map<String, PlayerInformation> playerInformationMap
            = new HashMap<>();
    /**
     * List of signs connected to the group
     */
    private final List<AbstractGroupSign> signs = new ArrayList<>();
    /*
     * ------------------------Object-variables--------------------------------
     */
    /**
     * The xp stored in the group
     */
    private int xpStored = 0;

    /*
     * ------------------------Object-constructors-----------------------------
     */
    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Constructs a group without a group name
     *
     * @param owner The owner of the group
     */
    public Group(UUID owner) {
        groupId = UUID.randomUUID();
        GROUPS.put(groupId.toString(), this);
        addPlayerToGroup(owner);
        this.owner = owner;
    }

    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Constructs a group with a group name
     *
     * @param owner The owner of the group
     * @param groupName The group name for the group
     */
    public Group(UUID owner, String groupName) {
        groupId = UUID.randomUUID();
        GROUPS.put(groupId.toString(), this);
        addPlayerToGroup(owner);
        this.owner = owner;
        this.groupName = groupName;
        PlayerInformation.getPlayerInformation(owner)
                .getGroupRights(groupId)
                .addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
    }

    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Constructs a group from a map.
     *
     * This constructor should only be used when initialing the groups from
     * file. It should not be used for creating groups from code.
     *
     * @param group Group map with all the data for th group
     */
    public Group(Map<String, Object> group) {
        groupId = UUID.fromString((String) group.get("uuidGroup"));
        if (group.containsKey("amount")) {
            xpStored = (int) group.get("amount");
        } else {
            xpStored = 0;
        }
        List<String> players = (List<String>) group.get("players");
        for (String player : players) {
            addPlayerToGroup(UUID.fromString(player));
        }
        if (group.containsKey("groupName")) {
            this.groupName = (String) group.get("groupName");
        }
        if (group.containsKey("groupIcon")) {
            this.groupIcon = (ItemStack) group.get("groupIcon");
            this.groupIcon.setAmount(1);
        }
        this.owner = UUID.fromString((String) group.get("ownerUuid"));
        GROUPS.put(groupId.toString(), this);
    }


    /*
     * ------------------------private-methodes--------------------------------
     */
    /**
     * Adds a player to the group
     *
     * @param player The player to add
     */
    private void addPlayerToGroup(UUID player) {
        PlayerInformation playerInformation
                = PlayerInformation.getPlayerInformation(player);

        playerInformationMap.put(player.toString(), playerInformation);
        playerInformation.addGroup(this);
    }

    /*
     * ------------------------private-methodes--------------------------------
     */
    /**
     * Adds a player to the group
     *
     * @param player The player to add
     */
    public void addPlayerToGroup(OfflinePlayer player) {
        this.addPlayerToGroup(player.getUniqueId());
    }

    /**
     * Adds a sign to the group
     *
     * @param sign The sign to add to the group
     */
    public void addSignToGroup(AbstractGroupSign sign) {
        signs.add(sign);
    }

    /**
     * Tries to destroy this group.
     *
     * If it fails it will not notify you
     *
     * @param playerWhoDestroys The player destroying the group
     */
    public void destoryGroup(Player playerWhoDestroys) {

        for (AbstractGroupSign sign : signs) {
            if (!sign.canDestroySign(playerWhoDestroys)) {
                return;
            }
        }
        for (PlayerInformation player : getPlayers()) {
            player.removeGroupRights(groupId);
        }

        ExperienceManager experienceManager
                = new ExperienceManager(playerWhoDestroys);
        experienceManager.changeExp(getXp());
        setXp(0);

        playerInformationMap.clear();

        for (AbstractGroupSign xpSign : signs) {
            AbstractXpSign.removeSign(xpSign);
            Sign sign = xpSign.getSign();

            if (xpSign.getSign().getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
                xpSign.getSign().getBlock()
                        .removeMetadata(
                                "XP_STORAGE_XPSIGN",
                                XPStorage.getPlugin()
                        );
            }

            XpSignFacingBlock.removeFacingBlock(xpSign.getSignFacingBlock());
            sign.setLine(0, "");
            sign.setLine(1, "");
            sign.setLine(2, "");
            sign.setLine(3, "");
            sign.update();
        }
        signs.clear();
        GROUPS.remove(groupId.toString());
    }

    /**
     * Gets the group icon for the group
     *
     * @return The group icon for the group
     */
    public ItemStack getGroupIcon() {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("GROUP_NAME", getGroupName());

        ItemStack parsedIcon;

        if (groupIcon != null) {
            parsedIcon = groupIcon;
        } else {
            parsedIcon = XPStorage.getGuiSettings()
                    .getItemStack("groupDefault", replaceMap);
        }

        return parsedIcon;
    }

    /**
     * Sets the group icon for the group
     *
     * @param groupIcon The group icon for the group
     */
    public void setGroupIcon(ItemStack groupIcon) {
        this.groupIcon = groupIcon;
    }

    /**
     * Gets the group name of the group
     *
     * @return The group name for the group
     */
    public String getGroupName() {
        return groupName == null
                ? "{ERROR: Unable to load group name}"
                : groupName;
    }

    /**
     * Sets the group name for the group
     *
     * @param groupName The group name for the group
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Gets the UUID for the group
     *
     * @return The UUID
     */
    public UUID getGroupUuid() {
        return groupId;
    }

    /**
     * Gets the owner for the group
     *
     * @return The owner for the group
     */
    public UUID getOwner() {
        return this.owner;
    }

    /**
     * Sets a new owner for the group
     *
     * @param newOwner The new owner for the group
     */
    public void setOwner(UUID newOwner) {
        this.owner = newOwner;
    }

    /**
     * Gets the players in this group
     *
     * @return The player in this group as PlayerInformation
     */
    public Collection<PlayerInformation> getPlayers() {
        return playerInformationMap.values();
    }

    /**
     * Gets the xp stored in this group
     *
     * @return The amount of xp
     */
    public int getXp() {
        return xpStored;
    }

    /**
     * Sets the amount of xp in this group
     *
     * @param xpAmount The xp this group should have
     */
    public void setXp(int xpAmount) {
        xpStored = xpAmount;

        for (AbstractGroupSign sign : signs) {
            sign.updateSign();
        }
    }

    /**
     * Returns if this group contains the player
     *
     * @param player The player you want to check
     *
     * @return True if this group contains the player, false otherwise.
     */
    public boolean hasPlayer(UUID player) {
        return playerInformationMap.containsKey(player.toString());
    }

    /**
     * Removes a player from the group
     *
     * @param player The player to remove
     */
    public void removePlayerFromGroup(UUID player) {
        if (!playerInformationMap.containsKey(player.toString())) {
            return;
        }
        playerInformationMap.remove(player.toString());
    }

    /**
     * Removes a sign from the group
     *
     * @param sign The sign to remove from the group
     */
    public void removeSignFromGroup(AbstractGroupSign sign) {
        if (!signs.contains(sign)) {
            return;
        }
        signs.remove(sign);
    }

    /*
     * ------------------------Implemented-methodes----------------------------
     */
    @Override
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> serialize() {
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("uuidGroup", groupId.toString());
        returnMap.put("ownerUuid", owner.toString());
        returnMap.put("amount", xpStored);
        returnMap.put("players",
                new ArrayList<>(playerInformationMap.keySet()));
        if (this.groupName != null) {
            returnMap.put("groupName", this.groupName);
        }
        if (this.groupIcon != null) {
            returnMap.put("groupIcon", groupIcon);
        }
        return returnMap;
    }

}
