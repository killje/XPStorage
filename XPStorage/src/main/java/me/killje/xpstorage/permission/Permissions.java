/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.killje.xpstorage.permission;

import org.bukkit.permissions.Permission;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public enum Permissions {
    
    CREATE_XP_GROUP(new Permission("xpStorage.createXpGroup")),
    OPEN_GUI_OTHERS(new Permission("xpStorage.openGuiOthers")),
    CHANGE_OWNER(new Permission("xpStorage.changeOwner"));
    
    
    private final Permission permission;

    private Permissions(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }
    
}
