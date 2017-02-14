/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.killje.xpstorage.gui.list;

import java.util.ArrayList;
import java.util.Arrays;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import me.killje.xpstorage.gui.guiElement.SimpleGuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class PlayerList extends List {
    
    private final AbstractXpSign sign;
    private final PlayerListGuiElement playerListGuiElement;
    
    public PlayerList(Player currentPlayer, AbstractXpSign sign, PlayerListGuiElement guiElement) {
        super(currentPlayer);
        this.playerListGuiElement = guiElement;
        this.sign = sign;
    }
    
    
    
    @Override
    protected int initInventory(int startIndex, int stopIndex, int maxItemsOnPage) {
        
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        
        
        for (int i = 0; i < 60; i++) {
            onlinePlayers.add(onlinePlayers.get(0));
        }
        
        ArrayList<OfflinePlayer> offlinePlayers = new ArrayList<>(Arrays.asList(Bukkit.getOfflinePlayers()));

        for (int i = 0; i < 59; i++) {
            offlinePlayers.add(offlinePlayers.get(0));
        }
        
        int onlineInventorySize = onlinePlayers.isEmpty() ? 0 : onlinePlayers.size() + 9 + (8 - (onlinePlayers.size() - 1) % 9);
        
        int totalSize = offlinePlayers.size() + onlineInventorySize;
        
        int offlineStartLocation = startIndex - onlineInventorySize;
        if (offlineStartLocation < 0) {
            offlineStartLocation = 0;
        }
        
        int offlineStopLocation = stopIndex - onlineInventorySize;
        if (offlineStopLocation < 0) {
            offlineStopLocation = 0;
        }
        if (offlineStopLocation > offlinePlayers.size()) {
            offlineStopLocation = offlinePlayers.size();
        }
        
        if (onlinePlayers.size() < startIndex) {
            this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerList", "All players")), 4);
            this.nextRow();
            
            for (OfflinePlayer offlinePlayer : offlinePlayers.subList(offlineStartLocation, offlineStopLocation)) {
                this.addGuiElement(playerListGuiElement.getGuiElement(offlinePlayer.getUniqueId(), sign));
            }
        }
        else {
            this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("onlinePlayerList", "Online players")), 4);
            this.nextRow();
            
            int toIndex = stopIndex;
            if (toIndex > onlinePlayers.size()) {
                toIndex = onlinePlayers.size();
            }
            
            for (OfflinePlayer offlinePlayer : onlinePlayers.subList(startIndex, toIndex)) {
                this.addGuiElement(playerListGuiElement.getGuiElement(offlinePlayer.getUniqueId(), sign));
            }
            
            if (stopIndex - onlineInventorySize > 9) {
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage));
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage) + 1);
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage) + 2);
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage) + 3);
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerList", "All players")), (onlineInventorySize % maxItemsOnPage) + 4);
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage) + 5);
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage) + 6);
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage) + 7);
                this.addGuiElement(new SimpleGuiElement(ItemStackFromFile.getItemStack("allPlayerFiller", "All players")), (onlineInventorySize % maxItemsOnPage) + 8);
                
                
                for (OfflinePlayer offlinePlayer : offlinePlayers.subList(offlineStartLocation, offlineStopLocation - offlineStartLocation)) {
                    this.addGuiElement(playerListGuiElement.getGuiElement(offlinePlayer.getUniqueId(), sign));
                }
                
            }
        }
        
        return totalSize;
        
    }

    @Override
    protected String getInventoryName() {
        return "Add player";
    }
    
}