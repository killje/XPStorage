package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.list.GuiElementList;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * This is a inventory showing a list of players in the group
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class CurrentList extends GuiElementList {

    /**
     * The players in this group
     */
    private final Map<String, GuiElement> elementMap = new HashMap<>();

    /**
     * Creates a inventory gui that shows a list of players currently in the
     * group
     *
     * @param currentPlayer
     * @param sign
     */
    public CurrentList(Player currentPlayer, AbstractGroupSign sign) {
        super(XPStorage.getGuiSettings(), currentPlayer);

        ArrayList<PlayerInformation> players
                = new ArrayList(sign.getGroup().getPlayers());

        for (PlayerInformation player : players) {

            OfflinePlayer offlinePlayer
                    = Bukkit.getOfflinePlayer(player.getUUID());

            elementMap.put(
                    offlinePlayer.getName(),
                    new EditPlayer(player.getUUID(), sign)
            );

        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String getInventoryName() {
        return getGuiSettings().getText("editPlayers");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Map<String, ? extends GuiElement> getElementMap() {
        return elementMap;
    }

}
