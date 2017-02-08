package me.killje.xpstorage.gui.addplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import me.killje.xpstorage.gui.Exit;
import me.killje.xpstorage.gui.InventoryUtils;
import me.killje.xpstorage.gui.SimpleGuiElement;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Dye;

/**
 *
 * @author Zolder
 */
public class PlayerList extends InventoryUtils {
    
    private final int page;
    private final AbstractSharedSign sign;
    
    public PlayerList(AbstractSharedSign sign) {
        this(sign, 0);
    }
    
    public PlayerList(AbstractSharedSign sign, int page) {
        super(6);
        this.page = page;
        this.sign = sign;
    }
    
    
    
    @Override
    protected void initInventory() {
        // First line is always a label and last line is always prev/next page
        int maxItemsOnPage = 45; // 4 * 9
        
        int startIndex = page * maxItemsOnPage;
        int stopIndex = (page + 1) * maxItemsOnPage;
        
        //Player[] onlinePlayersArray = new Player[45];
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        
        ArrayList<OfflinePlayer> offlinePlayers = new ArrayList<>(Arrays.asList(Bukkit.getOfflinePlayers()));
        
//        int testAmounth = 167;
//        int offlineTestAmounth = 107;
//        for (int i = 0; i < testAmounth; i++) {
//            onlinePlayers.add(onlinePlayers.get(0));
//        }
//        
//        for (int i = 0; i < offlineTestAmounth; i++) {
//            offlinePlayers.add(offlinePlayers.get(i % 2));
//        }
//        
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
            this.addGuiElement(new SimpleGuiElement(new Dye(DyeColor.MAGENTA), "All players"), 4);
            this.nextRow();
            
            List<OfflinePlayer> subList = offlinePlayers.subList(offlineStartLocation, offlineStopLocation);
            for (OfflinePlayer offlinePlayer : subList) {
                this.addGuiElement(new AddPlayer(offlinePlayer, sign));
            }
        }
        else {
            this.addGuiElement(new SimpleGuiElement(new Dye(DyeColor.LIME), "Online players"), 4);
            this.nextRow();
            
            int toIndex = stopIndex;
            if (toIndex > onlinePlayers.size()) {
                toIndex = onlinePlayers.size();
            }
            
            List<Player> subArray = onlinePlayers.subList(startIndex, toIndex);
            
            for (OfflinePlayer offlinePlayer : subArray) {
                this.addGuiElement(new AddPlayer(offlinePlayer, sign));
            }
            
            if (stopIndex - onlineInventorySize > 8) {
                this.addGuiElement(new SimpleGuiElement(new Dye(DyeColor.MAGENTA), "All players"), (onlineInventorySize % maxItemsOnPage) + 4);
                this.nextRow();
                
                List<OfflinePlayer> subList = offlinePlayers.subList(offlineStartLocation, offlineStopLocation - offlineStartLocation);
                for (OfflinePlayer offlinePlayer : subList) {
                    this.addGuiElement(new AddPlayer(offlinePlayer, sign));
                }
                
            }
        }
        
        if (page != 0) {
            this.addGuiElement(new PrevPage(page, sign), 3);
        }
        
        if (totalSize > stopIndex) {
            this.addGuiElement(new NextPage(page, sign), 5);
        }
        this.addGuiElement(new Exit(), 8);
        
        
        this.setInventoryName("Add player | Page " + (page + 1) + "/" + (totalSize/maxItemsOnPage + 1));
        
    }
    
}
