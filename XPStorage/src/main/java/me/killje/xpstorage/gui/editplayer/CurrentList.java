package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import me.killje.xpstorage.gui.list.List;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.entity.Player;

/**
 *
 * @author Zolder
 */
public class CurrentList extends List {
    
    private final AbstractSharedSign sign;
    
    public CurrentList(Player currentPlayer, AbstractSharedSign sign) {
        super(currentPlayer);
        this.sign = sign;
    }
    
    @Override
    protected int initInventory(int startIndex, int stopIndex, int maxItemsOnPage) {
        
        this.nextRow();
        
        ArrayList<PlayerInformation> players = new ArrayList(sign.getGroup().getPlayers());

        int toIndex = stopIndex;
        if (toIndex > players.size()) {
            toIndex = players.size();
        }
        ArrayList<PlayerInformation> subArray = new ArrayList<>(players.subList(startIndex, toIndex));

        for (PlayerInformation playerInformation : subArray) {
            this.addGuiElement(new EditPlayer(playerInformation.getUUID(), sign));
        }
        
        return players.size();
        
    }

    @Override
    protected String getInventoryName() {
        return "Edit players";
    }
    
}
