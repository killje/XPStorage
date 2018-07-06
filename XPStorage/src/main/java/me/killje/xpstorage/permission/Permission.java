package me.killje.xpstorage.permission;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public enum Permission {

    CREATE_XP_GROUP(new org.bukkit.permissions.Permission("xpStorage.createXpGroup")),
    OPEN_GUI_OTHERS(new org.bukkit.permissions.Permission("xpStorage.openGuiOthers")),
    CHANGE_OWNER(new org.bukkit.permissions.Permission("xpStorage.changeOwner"));

    private final org.bukkit.permissions.Permission permission;

    private Permission(org.bukkit.permissions.Permission permission) {
        this.permission = permission;
    }

    public org.bukkit.permissions.Permission getPermission() {
        return permission;
    }

}
