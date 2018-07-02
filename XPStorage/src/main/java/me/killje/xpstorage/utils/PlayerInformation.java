package me.killje.xpstorage.utils;

import me.killje.util.clsConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.group.GroupRights.Right;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.NormalSign;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PlayerInformation implements ConfigurationSerializable {

    static {
        new PlayerInformationPeriodSaver();
    }

    private static class PlayerInformationPeriodSaver implements Runnable {

        public PlayerInformationPeriodSaver() {
            Bukkit.getScheduler().runTaskTimerAsynchronously(XPStorage.getInstance(), this, 100, 12000);
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
    private static final clsConfiguration PLAYER_INFORMATION_CONFIG = new clsConfiguration(XPStorage.getInstance(), "playerInformation.yml");
    private boolean getMessage = true;
    private Class<? extends AbstractXpSign> defaultSign = NormalSign.class;

    public PlayerInformation(UUID player) {
        this.player = player;
        playerInformationMap.put(player.toString(), this);
    }

    public static PlayerInformation deserialize(Map<String, Object> player) {
        if (!XPStorage.getInstance().isInit()) {
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
            Class<? extends AbstractXpSign> defaultSign = (Class<? extends AbstractXpSign>) Class.forName((String) player.getOrDefault("defaultSign", "me.killje.xpstorage.xpsign.NormalSign"));
            playerInformation.setDefaultSign(defaultSign);
        } catch (ClassNotFoundException ex) {
            XPStorage.getInstance().getLogger().log(Level.SEVERE, null, ex);
            playerInformation.setDefaultSign(NormalSign.class);
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
