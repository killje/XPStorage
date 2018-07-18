package me.killje.xpstorage.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * This class is for permissions players have in groups. This can differ per
 * group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class GroupRights implements ConfigurationSerializable {

    /**
     * Deserialize's the given map
     *
     * This is only used for loading from a config file
     *
     * @param groupRightsMap The group right information
     *
     * @return The constructed group right
     */
    public static GroupRights deserialize(Map<String, Object> groupRightsMap) {

        UUID groupId
                = UUID.fromString((String) groupRightsMap.get("uuidGroup"));

        GroupRights groupRights = new GroupRights(groupId);

        List<String> rights = (List<String>) groupRightsMap.get("rights");

        for (String rightName : rights) {

            Right right = Right.getRightsByName(rightName);

            if (right != null) {
                groupRights.addRight(right);
            }
        }

        return groupRights;
    }

    /**
     * The permissions available
     */
    public enum Right {

        /**
         * The player can add, remove and edit players
         */
        CAN_EDIT_PLAYERS("CAN_EDIT_PLAYERS"),
        /**
         * The player can create and destroy group signs
         */
        CAN_CREATE_GROUP_SIGNS("CAN_CREATE_GROUP_SIGNS");

        /**
         * The name used in config storage for serializing
         */
        private final String storageName;

        /**
         * Constructs a right for groups
         *
         * @param storageName The storage name used in configs for serializing
         */
        private Right(String storageName) {
            this.storageName = storageName;
        }

        /**
         * Gets the name to be used in storage
         *
         * @return The storage name
         */
        public String getStorageName() {
            return this.storageName;
        }

        /**
         * Gets the right by the storage name given
         *
         * If not found this will return null
         *
         * @param storageName The name to search for
         *
         * @return The right associated by the name
         */
        static Right getRightsByName(String storageName) {
            Right[] rights = values();

            for (Right right : rights) {
                if (right.getStorageName().equals(storageName)) {
                    return right;
                }
            }

            return null;
        }
    }

    /**
     * The UUID the group right belongs
     */
    private final UUID groupId;

    /**
     * The rights that the group has
     */
    private final Set<Right> rights = new HashSet<>();

    /**
     * Creates a new group right
     *
     * @param groupId The group UUID this right belongs
     */
    public GroupRights(UUID groupId) {
        this.groupId = groupId;
    }

    /**
     * Add a rigth to the group right
     *
     * @param right The right to add
     */
    public void addRight(Right right) {
        if (this.rights.contains(right)) {
            return;
        }
        this.rights.add(right);
    }

    /**
     * Gets the group UUID that these rights belong to
     *
     * @return The UUID
     */
    public UUID getGroupId() {
        return groupId;
    }

    /**
     * Checks if this right is present
     *
     * @param right The right to check for
     *
     * @return True if this contains the right, false otherwise
     */
    public boolean hasRight(Right right) {
        return this.rights.contains(right);
    }

    /**
     * Removes a group right to the rights
     *
     * @param right The right to remove
     */
    public void removeRight(Right right) {
        if (!this.rights.contains(right)) {
            return;
        }
        this.rights.remove(right);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> serialize() {
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("uuidGroup", groupId.toString());
        ArrayList<String> rightStrings = new ArrayList<>();
        for (Right right : rights) {
            rightStrings.add(right.getStorageName());
        }
        returnMap.put("rights", rightStrings);
        return returnMap;
    }
}
