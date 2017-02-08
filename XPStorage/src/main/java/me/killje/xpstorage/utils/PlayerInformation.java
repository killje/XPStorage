package me.killje.xpstorage.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.group.GroupRights.Right;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author Zolder
 */
public class PlayerInformation implements ConfigurationSerializable {
    static {
        new PlayerInformationPeriodSaver();
    }

    private static class PlayerInformationPeriodSaver implements Runnable {

        public PlayerInformationPeriodSaver() {
            Bukkit.getScheduler().runTaskTimerAsynchronously(XPStorage.getInstance(), this, 100, 12000); //12000, 12000);
        }
        
        @Override
        public void run() {
            System.out.println("run player");
            PlayerInformation.savePlayerInformation();
        }

    }
    
    private static HashMap<String, PlayerInformation> playerInformation = new HashMap<>();
    
    private final UUID player;
    private int xp = 0;
    private ArrayList<Group> groups = new ArrayList<>();
    private HashMap<UUID, GroupRights> groupRights = new HashMap<>();
    private static final clsConfiguration PLAYER_INFORMATION_CONFIG = new clsConfiguration(XPStorage.getInstance(), "playerInformation.yml");
    
    public PlayerInformation(UUID player) {
        System.out.println("Create player: " + player.toString());
        this.player = player;
        playerInformation.put(player.toString(), this);
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
        playerInformation.setPlayerXpAmounth(xp);
        return playerInformation;
    }
    
    public void setPlayerXpAmounth (int xp) {
        this.xp = xp;
    }
    
    public int getPlayerXpAmounth() {
        return xp;
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
        HashMap<String, Object> player = new HashMap<>();
        player.put("xp", xp);
        player.put("player", this.player.toString());
        player.put("groupRights", new ArrayList<>(groupRights.values()));
        return player;
    }
    
    public static PlayerInformation getPlayerInformation (UUID player) {
        PlayerInformation playerInformation = null;
        if (PlayerInformation.playerInformation.containsKey(player.toString())) {
            playerInformation = PlayerInformation.playerInformation.get(player.toString());
        }
        
        if (playerInformation == null) {
            return new PlayerInformation(player);
        }
        return playerInformation;
    }
    
    
    public static void loadPlayerInformation() {
        System.out.println("loadConfig#1");
        List<PlayerInformation> playerInformationList = (List<PlayerInformation>) PLAYER_INFORMATION_CONFIG.GetConfig().getList("playerInformation");
        System.out.println("loadConfig#2");
        if (playerInformationList == null) {
            return;
        }
        for (PlayerInformation playerInformationItem : playerInformationList) {
            playerInformation.put(playerInformationItem.getUUIDAsString(), playerInformationItem);
        }
    }
    
    public static void savePlayerInformation () {
        PLAYER_INFORMATION_CONFIG.GetConfig().set("playerInformation", new ArrayList<>(playerInformation.values()));
        PLAYER_INFORMATION_CONFIG.SaveConfig();
    }
    
    public void addGroup(Group group) {
        groups.add(group);
    }

    public ArrayList<Group> getGroups(Right Right) {
        ArrayList<Group> returnGroups = new ArrayList<>();
        for (Group group : groups) {
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
