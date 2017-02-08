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
 *
 * @author Zolder
 */
public class GroupRights implements ConfigurationSerializable{
    
    public enum Right {
        CAN_EDIT_PLAYERS("CAN_EDIT_PLAYERS"),
        CAN_CREATE_GROUP_SIGNS("CAN_CREATE_GROUP_SIGNS");

        private final String storageName;

        private Right(String storageName) {
            this.storageName = storageName;
        }
        
        public String getStorageName() {
            return this.storageName;
        }
        
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
    
    private final UUID groupId;
    private final Set<Right> rights = new HashSet<>() ;

    public GroupRights(UUID groupId) {
        this.groupId = groupId;
    }

    @Override
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
    
    public static GroupRights deserialize(Map<String, Object> groupRightsMap) {
        
        UUID groupId = UUID.fromString((String) groupRightsMap.get("uuidGroup"));
        
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
    
    public void addRight(Right right) {
        if (this.rights.contains(right)) {
            return;
        }
        this.rights.add(right);
    }
    
    public void removeRight(Right right) {
        if (!this.rights.contains(right)) {
            return;
        }
        this.rights.remove(right);
    }
    
    public boolean hasRight(Right right) {
        return this.rights.contains(right);
    }
    
    public UUID getGroupId() {
        return groupId;
    }
}
