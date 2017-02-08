package me.killje.xpstorage.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.utils.clsConfiguration;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 *
 * @author Zolder
 */
public class Group implements ConfigurationSerializable {
    static {
        new groupPeriodSaver();
    }

    private static class groupPeriodSaver implements Runnable {

        public groupPeriodSaver() {
            Bukkit.getScheduler().runTaskTimerAsynchronously(XPStorage.getInstance(), this, 100, 12000); //12000, 12000);
        }
        
        @Override
        public void run() {
            System.out.println("run group");
            Group.saveGroups();
        }

    }
    
    private final HashMap<String, PlayerInformation> players = new HashMap<>();
    private final ArrayList<AbstractSharedSign> signs = new ArrayList<>();
    private final static HashMap<String, Group> GROUPS = new HashMap<>();
    
    private int xpStored = 0;
    private final UUID groupId;
    private static final clsConfiguration GROUP_CONFIG = new clsConfiguration(XPStorage.getInstance(), "groups.yml");
    private UUID owner;
    private String groupName = null;

    public Group(UUID player) {
        groupId = UUID.randomUUID();
        GROUPS.put(groupId.toString(), this);
        addPlayerToGroup(player);
        this.owner = player;
        
    }
    
    public Group(UUID player, String groupName) {
        groupId = UUID.randomUUID();
        GROUPS.put(groupId.toString(), this);
        addPlayerToGroup(player);
        this.owner = player;
        this.groupName = groupName;
        PlayerInformation.getPlayerInformation(player).getGroupRights(groupId).addRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS);
    }
    
    public Group(Map<String, Object> group) {
        groupId = UUID.fromString((String) group.get("uuidGroup"));
        xpStored = (int) group.get("amounth");
        List<String> players = (List<String>) group.get("players");
        for (String player : players) {
            addPlayerToGroup(UUID.fromString(player));
        }
        if (group.containsKey("groupName")) {
            this.groupName = (String) group.get("groupName");
        }
        this.owner = UUID.fromString((String) group.get("ownerUuid"));
        GROUPS.put(groupId.toString(), this);
        
        if (this.owner == null) {
            return;
        }
    }

    public static void saveGroups() {
        GROUP_CONFIG.GetConfig().set("groups", new ArrayList<>(GROUPS.values()));
        GROUP_CONFIG.SaveConfig();
    }
    
    public static void loadGroups() {
        List<Group> groupList = (List<Group>) GROUP_CONFIG.GetConfig().getList("groups");
        if (groupList == null) {
            return;
        }
    }

    
    public static Group getGroupFromUUID(UUID groupUUID) {
        if (!GROUPS.containsKey(groupUUID.toString())) {
            return null;
        }
        return GROUPS.get(groupUUID.toString());
    }
    
    public void addPlayerToGroup (UUID player) {
        PlayerInformation playerInformation = PlayerInformation.getPlayerInformation(player);
        players.put(player.toString() ,playerInformation);
        playerInformation.addGroup(this);
    }
    
    public void removePlayerFromGroup(UUID player) {
        if (!players.containsKey(player.toString())) {
            return;
        }
        players.remove(player.toString());
    }
    
    public void addSignToGroup(AbstractSharedSign sign) {
        signs.add(sign);
    }
    
    public void removeSignFromGroup(AbstractSharedSign sign) {
        if (!signs.contains(sign)) {
            return;
        }
        signs.remove(sign);
    }
    
    public boolean hasPlayer(UUID player) {
        return players.containsKey(player.toString());
        
    }
    
    public Collection<PlayerInformation> getPlayers() {
        return players.values();
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("uuidGroup", groupId.toString());
        returnMap.put("ownerUuid", owner.toString());
        returnMap.put("amounth", xpStored);
        returnMap.put("players", new ArrayList<>(players.keySet()));
        if (this.groupName != null) {
            returnMap.put("groupName", this.groupName);
        }
        return returnMap;
    }

    public UUID getGroupUuid() {
        return groupId;
    }
    
    public void setXp(int xpAmount) {
        xpStored = xpAmount;
       
        for (AbstractSharedSign sign : signs) {
            if (sign.equals(this)) {
                continue;
            }
            sign.updateSign();
        }
    }
    
    public int getXp() {
        return xpStored;
    }
    
    public void destoryGroup() {
        GROUPS.remove(groupId.toString());
        
    }
    
    public void setOwner(UUID newOwner){
        this.owner = newOwner;
    }
    
    public UUID getOwner(){
        return this.owner;
    }

    public String getGroupName() {
        return groupName;
    }
    
    
}
