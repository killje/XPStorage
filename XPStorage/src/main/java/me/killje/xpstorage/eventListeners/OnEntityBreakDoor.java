/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.killje.xpstorage.eventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreakDoorEvent;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnEntityBreakDoor extends OnBlockBreak{
    
    @EventHandler
    public void onEntityBreakDoor (EntityBreakDoorEvent event) {
        event.setCancelled(!isDestroyable(event.getBlock()));
    }
    
}
