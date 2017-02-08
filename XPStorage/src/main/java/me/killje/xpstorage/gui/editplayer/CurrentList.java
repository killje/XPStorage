package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import me.killje.xpstorage.gui.Exit;
import me.killje.xpstorage.gui.InventoryUtils;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;

/**
 *
 * @author Zolder
 */
public class CurrentList extends InventoryUtils {
    
    private final int page;
    private final AbstractSharedSign sign;
    
    public CurrentList(AbstractSharedSign sign) {
        this(sign, 0);
    }
    
    public CurrentList(AbstractSharedSign sign, int page) {
        super(6);
        this.page = page;
        this.sign = sign;
    }
    
    @Override
    protected void initInventory() {
        
        int maxItemsOnPage = 45; // 4 * 9
        
        int startIndex = page * maxItemsOnPage;
        int stopIndex = (page + 1) * maxItemsOnPage;
        this.nextRow();
        //Player[] onlinePlayersArray = new Player[45];
        ArrayList<PlayerInformation> players = new ArrayList(sign.getGroup().getPlayers());

        int toIndex = stopIndex;
        if (toIndex > players.size()) {
            toIndex = players.size();
        }
        ArrayList<PlayerInformation> subArray = new ArrayList<>(players.subList(startIndex, toIndex));

        for (PlayerInformation playerInformation : subArray) {
            this.addGuiElement(new EditPlayer(playerInformation.getUUID(), sign));
        }
        
        if (page != 0) {
            this.addGuiElement(new PrevPage(page, sign), 3);
        }
        if (players.size() > stopIndex) {
            this.addGuiElement(new NextPage(page, sign), 5);
        }
        
        
        this.addGuiElement(new Exit(), 8);
        
        this.setInventoryName("Edit players | Page " + (page + 1) + "/" + (players.size()/maxItemsOnPage + 1));
        
    }
    
}
