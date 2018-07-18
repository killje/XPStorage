package me.killje.xpstorage.permission;

import org.bukkit.permissions.Permissible;

/**
 * Permission base for XPStorage extending bukkit behavior
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public enum Permission {

    CREATE_LOCAL_PLAYER(
            new org.bukkit.permissions.Permission(
                    "xpstorage.create.localplayer"
            )
    ),
    CREATE_ENDER_PLAYER(
            new org.bukkit.permissions.Permission(
                    "xpstorage.create.enderplayer"
            )
    ),
    CREATE_LOCAL_GROUP(
            new org.bukkit.permissions.Permission(
                    "xpstorage.create.localgroup"
            )
    ),
    CREATE_ENDER_GROUP(
            new org.bukkit.permissions.Permission(
                    "xpstorage.create.endergroup"
            )
    ),
    CREATE_NEW_GROUP(
            new org.bukkit.permissions.Permission(
                    "xpstorage.createnewgroup"
            )
    ),
    OPEN_GUI_OTHERS(
            new org.bukkit.permissions.Permission(
                    "xpstorage.openguiothers"
            )
    ),
    CHANGE_OWNER(
            new org.bukkit.permissions.Permission(
                    "xpstorage.changeowner"
            )
    ),
    SHOW_UUID(
            new org.bukkit.permissions.Permission(
                    "xpstorage.showuuid"
            )
    );

    /**
     * Original bukkit permission
     */
    private final org.bukkit.permissions.Permission permission;

    /**
     * Creates a XPStorage permission
     *
     * @param permission The original bukkit permission
     */
    private Permission(org.bukkit.permissions.Permission permission) {
        this.permission = permission;
    }

    /**
     * Returns the original permission
     *
     * @return The bukkit permission
     */
    public org.bukkit.permissions.Permission getPermission() {
        return permission;
    }

    /**
     * Check if the permissible entity has the permission
     *
     * @param permissible The entity to check
     * @return True if the permissible has the permission, false otherwise
     */
    public boolean hasPermission(Permissible permissible) {
        if (permissible == null) {
            return false;
        }
        return permissible.hasPermission(permission);
    }

    /**
     * Checks if any permission given is true for the permissable
     *
     * @param permissible The permissible to check the permissions for
     * @param permissions The permissions to check
     * @return True if any permission is true for the permissible, false
     * otherwise
     */
    public static boolean hasAnyPermission(Permissible permissible,
            Permission... permissions) {

        for (Permission permission : permissions) {
            if (permissible.hasPermission(permission.getPermission())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any permission given is false for the permissable
     *
     * @param permissible The permissible to check the permissions for
     * @param permissions The permissions to check
     * @return False if any permission is false for the permissible, true
     * otherwise
     */
    public static boolean hasAllPermissions(Permissible permissible,
            Permission... permissions) {

        for (Permission permission : permissions) {
            if (!permissible.hasPermission(permission.getPermission())) {
                return false;
            }
        }
        return true;
    }

}
