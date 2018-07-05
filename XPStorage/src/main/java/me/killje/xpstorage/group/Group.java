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
import me.killje.xpstorage.util.PluginUtils;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.XpSignFacingBlock;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
@SuppressWarnings("ResultOfObjectAllocationIgnored")
public class Group implements ConfigurationSerializable {
    static {
        new groupPeriodSaver();
    }


    private static class groupPeriodSaver implements Runnable {

        public groupPeriodSaver() {
            PluginUtils.runTaskTimerAsynchronously(this, 100, 12000);
        }
        
        @Override
        public void run() {
            Group.saveGroups();
        }

    }
    
    private final HashMap<String, PlayerInformation> playerInformationMap = new HashMap<>();
    private final ArrayList<AbstractSharedSign> signs = new ArrayList<>();
    private final static HashMap<String, Group> GROUPS = new HashMap<>();
    
    private int xpStored = 0;
    private final UUID groupId;
    private static final clsConfiguration GROUP_CONFIG = new clsConfiguration(PluginUtils.getPlugin(), "groups.yml");
    private UUID owner;
    private String groupName = null;
    private Material groupIcon;

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
            this.groupIcon = Material.getMaterial((String) group.get("groupIcon"));
        }
        this.owner = UUID.fromString((String) group.get("ownerUuid"));
        GROUPS.put(groupId.toString(), this);
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
        playerInformationMap.put(player.toString() ,playerInformation);
        playerInformation.addGroup(this);
    }
    
    public void removePlayerFromGroup(UUID player) {
        if (!playerInformationMap.containsKey(player.toString())) {
            return;
        }
        playerInformationMap.remove(player.toString());
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
        return playerInformationMap.containsKey(player.toString());
        
    }
    
    public Collection<PlayerInformation> getPlayers() {
        return playerInformationMap.values();
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("uuidGroup", groupId.toString());
        returnMap.put("ownerUuid", owner.toString());
        returnMap.put("amount", xpStored);
        returnMap.put("players", new ArrayList<>(playerInformationMap.keySet()));
        if (this.groupName != null) {
            returnMap.put("groupName", this.groupName);
        }
        if (this.groupIcon != null) {
            returnMap.put("groupIcon", groupIcon.name());
        }
        return returnMap;
    }

    public UUID getGroupUuid() {
        return groupId;
    }
    
    public void setXp(int xpAmount) {
        xpStored = xpAmount;
       
        for (AbstractSharedSign sign : signs) {
            sign.updateSign();
        }
    }
    
    public int getXp() {
        return xpStored;
    }
    
    public void destoryGroup(Player playerWhoDestroys) {
        
        for (AbstractSharedSign sign : signs) {
            if (!sign.canDestroySign(playerWhoDestroys)) {
                return;
            }
        }
        for (PlayerInformation player : getPlayers()) {
            player.removeGroupRights(groupId);
        }
        
        
        ExperienceManager experienceManager = new ExperienceManager(playerWhoDestroys);
        experienceManager.changeExp(getXp());
        setXp(0);
        
        playerInformationMap.clear();
        
        for (AbstractSharedSign xpSign : signs) {
            AbstractXpSign.removeSign(xpSign);
            Sign sign = xpSign.getSign();
            if (xpSign.getSign().getBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
                xpSign.getSign().getBlock().removeMetadata("XP_STORAGE_XPSIGN", PluginUtils.getPlugin());
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
    
    public void setOwner(UUID newOwner){
        this.owner = newOwner;
    }
    
    public UUID getOwner(){
        return this.owner;
    }

    public String getGroupName() {
        return groupName == null ? "{ERROR: Unable to load group name}" : groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public ItemStack getGroupIcon() {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("GROUP_NAME", getGroupName());
        ItemStack parsedIcon = XPStorage.getGuiSettings().getItemStack("groupDefault", replaceMap);
        if (groupIcon != null) {
            parsedIcon.setType(groupIcon);
        }
        return parsedIcon;
    }

    public void setGroupIcon(Material groupIcon) {
        this.groupIcon = groupIcon;
    }
    
    
}
