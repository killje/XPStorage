/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.killje.xpstorage.gui.list;

import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.Exit;
import me.killje.xpstorage.gui.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class List extends InventoryUtils {
    
    private int page = 0;
    private final Player player;
    
    public List(Player currentPlayer) {
        super(6);
        this.player = currentPlayer;
    }

    @Override
    protected void initInventory() {
        int maxItemsOnPage = 45; // 4 * 9
        
        int startIndex = page * maxItemsOnPage;
        int stopIndex = (page + 1) * maxItemsOnPage;
        
        int totalSize = initInventory(startIndex, stopIndex, maxItemsOnPage);
        
        
        if (page != 0) {
            this.addGuiElement(new PrevPage(this), 3);
        }
        System.out.println(totalSize);
        System.out.println(stopIndex);
        if (totalSize > stopIndex) {
            this.addGuiElement(new NextPage(this), 5);
        }
        this.addGuiElement(new Exit(), 8);
        
        this.setInventoryName(getInventoryName() + " | Page " + (page + 1) + "/" + ((totalSize - 1)/maxItemsOnPage + 1));
        
    }
    
    protected abstract int initInventory(int startIndex, int stopIndex, int maxItemsOnPage);
    protected abstract String getInventoryName();
    
    
    public void nextPage() {
        page++;
        player.openInventory(this.getInventory());
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
    public void previousPage() {
        page--;
        player.openInventory(this.getInventory());
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
}
