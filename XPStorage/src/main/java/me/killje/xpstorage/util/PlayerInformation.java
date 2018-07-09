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

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
@SuppressWarnings("ResultOfObjectAllocationIgnored")
public class PlayerInformation implements ConfigurationSerializable {

    static {
        new PlayerInformationPeriodSaver();
    }

    private static class PlayerInformationPeriodSaver implements Runnable {

        public PlayerInformationPeriodSaver() {
            XPStorage.getPluginUtil().runTaskTimerAsynchronously(this, XPStorage.getPluginUtil().getConfig().getInt("backupSaveInterfallMinutes") *  1200, XPStorage.getPluginUtil().getConfig().getInt("backupSaveInterfallMinutes") * 1200);
        }

        @Override
        public void run() {
            PlayerInformation.savePlayerInformation();
        }

    }

    private static final HashMap<String, PlayerInformation> playerInformationMap = new HashMap<>();

    private final UUID player;
    private int xp = 0;
    private final ArrayList<Group> groups = new ArrayList<>();
    private final HashMap<UUID, GroupRights> groupRights = new HashMap<>();
    private static final clsConfiguration PLAYER_INFORMATION_CONFIG = new clsConfiguration(XPStorage.getPluginUtil().getPlugin(), "playerInformation.yml");
    private boolean getMessage = true;
    private Class<? extends AbstractXpSign> defaultSign = LocalPlayerSign.class;

    public PlayerInformation(UUID player) {
        this.player = player;
        playerInformationMap.put(player.toString(), this);
    }

    public static PlayerInformation deserialize(Map<String, Object> player) {
        if (!XPStorage.getPluginUtil().getPlugin().isEnabled()) {
            return null;
        }

        UUID playerUUID = UUID.fromString((String) player.get("player"));

        int xp = (int) player.get("xp");

        PlayerInformation playerInformation = getPlayerInformation(playerUUID);

        if (player.containsKey("groupRights")) {
            List<GroupRights> groupRightsList = (List<GroupRights>) player.get("groupRights");
            for (GroupRights groupRights : groupRightsList) {
                playerInformation.addGroupRights(groupRights.getGroupId(), groupRights);
            }
        }
        playerInformation.setPlayerXpAmount(xp);

        playerInformation.isMessage((boolean) player.getOrDefault("getMessage", true));
        try {
            Class<? extends AbstractXpSign> defaultSign = (Class<? extends AbstractXpSign>) Class.forName((String) player.get("defaultSign"));
            playerInformation.setDefaultSign(defaultSign);
        } catch (ClassNotFoundException ex) {
            XPStorage.getPluginUtil().getLogger().log(Level.SEVERE, null, ex);
            playerInformation.setDefaultSign(LocalPlayerSign.class);
        }

        return playerInformation;
    }

    public void setPlayerXpAmount(int xp) {
        this.xp = xp;
    }

    public int getPlayerXpAmount() {
        return xp;
    }

    public boolean isMessage() {
        return getMessage;
    }

    public void isMessage(boolean getMessage) {
        this.getMessage = getMessage;
    }

    public void setDefaultSign(Class<? extends AbstractXpSign> defaultSign) {
        this.defaultSign = defaultSign;
    }

    public Class<? extends AbstractXpSign> getDefaultSign() {
        if (defaultSign == null) {
            defaultSign = LocalPlayerSign.class;
        }
        return defaultSign;
    }

    public String getUUIDAsString() {
        return player.toString();
    }

    public UUID getUUID() {
        return player;
    }

    public GroupRights getGroupRights(UUID groupId) {
        if (!groupRights.containsKey(groupId)) {
            groupRights.put(groupId, new GroupRights(groupId));
        }
        return groupRights.get(groupId);
    }

    public void addGroupRights(UUID groupId, GroupRights groupRights) {
        this.groupRights.put(groupId, groupRights);
    }

    public void removeGroupRights(UUID groupId) {
        if (groupRights.containsKey(groupId)) {
            groupRights.remove(groupId);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> playerInformation = new HashMap<>();
        playerInformation.put("xp", xp);
        playerInformation.put("player", this.player.toString());
        playerInformation.put("groupRights", new ArrayList<>(groupRights.values()));
        playerInformation.put("getMessage", getMessage);
        playerInformation.put("defaultSign", defaultSign.getCanonicalName());
        return playerInformation;
    }
    
    public static PlayerInformation getPlayerInformation(HumanEntity player) {
        return getPlayerInformation(player.getUniqueId());
    }

    public static PlayerInformation getPlayerInformation(UUID player) {
        PlayerInformation playerInformation = null;
        if (PlayerInformation.playerInformationMap.containsKey(player.toString())) {
            playerInformation = PlayerInformation.playerInformationMap.get(player.toString());
        }

        if (playerInformation == null) {
            return new PlayerInformation(player);
        }
        return playerInformation;
    }

    public static void loadPlayerInformation() {
        List<PlayerInformation> playerInformationList = (List<PlayerInformation>) PLAYER_INFORMATION_CONFIG.GetConfig().getList("playerInformation");
        if (playerInformationList == null) {
            return;
        }
        for (PlayerInformation playerInformationItem : playerInformationList) {
            playerInformationMap.put(playerInformationItem.getUUIDAsString(), playerInformationItem);
        }
    }

    public static void savePlayerInformation() {
        PLAYER_INFORMATION_CONFIG.GetConfig().set("playerInformation", new ArrayList<>(playerInformationMap.values()));
        PLAYER_INFORMATION_CONFIG.SaveConfig();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

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

    public ArrayList<Group> getGroups() {
        return groups;
    }

}
