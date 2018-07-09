package me.killje.xpstorage.permission;

import org.bukkit.permissions.Permissible;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public enum Permission {

    CREATE_LOCAL_PLAYER(new org.bukkit.permissions.Permission("xpstorage.create.localplayer")),
    CREATE_ENDER_PLAYER(new org.bukkit.permissions.Permission("xpstorage.create.enderplayer")),
    CREATE_LOCAL_GROUP(new org.bukkit.permissions.Permission("xpstorage.create.localgroup")),
    CREATE_ENDER_GROUP(new org.bukkit.permissions.Permission("xpstorage.create.endergroup")),
    CREATE_NEW_GROUP(new org.bukkit.permissions.Permission("xpstorage.createnewgroup")),
    OPEN_GUI_OTHERS(new org.bukkit.permissions.Permission("xpstorage.openguiothers")),
    CHANGE_OWNER(new org.bukkit.permissions.Permission("xpstorage.changeowner")),
    SHOW_UUID(new org.bukkit.permissions.Permission("xpstorage.showuuid"));

    private final org.bukkit.permissions.Permission permission;

    private Permission(org.bukkit.permissions.Permission permission) {
        this.permission = permission;
    }

    public org.bukkit.permissions.Permission getPermission() {
        return permission;
    }
    
    public boolean hasPermission(Permissible permissible) {
        if (permissible == null) {
            return false;
        }
        return permissible.hasPermission(permission);
    }
    
    public static boolean hasAnyPermission(Permissible permissible, Permission ... permissions) {
        for (Permission permission : permissions) {
            if (permissible.hasPermission(permission.getPermission())) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasAllPermissions(Permissible permissible, Permission ... permissions) {
        for (Permission permission : permissions) {
            if (!permissible.hasPermission(permission.getPermission())) {
                return false;
            }
        }
        return true;
    }

}
