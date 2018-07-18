package me.killje.xpstorage.eventListeners;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import me.desht.dhutils.ExperienceManager;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.sign.SignInventory;
import me.killje.xpstorage.permission.Permission;
import me.killje.xpstorage.util.InteractTimeout;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

/**
 * Listener for players interacting events
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class OnPlayerInteract implements Listener {

    /**
     * This is called when a player interacts in the game
     *
     * @param event The event that belongs to the player interacting
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!event.getClickedBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
            return;
        }

        List<MetadataValue> metadataList = event.getClickedBlock()
                .getMetadata("XP_STORAGE_XPSIGN");

        if (metadataList.size() > 1) {
            event.getPlayer().sendMessage(
                    XPStorage.getGuiSettings().getText("metadataProblem")
            );
            XPStorage.getPluginUtil().getLogger().log(Level.SEVERE,
                    XPStorage.getGuiSettings().getText("metadataProblemAdmin"));
            return;
        }

        Object signObject = metadataList.get(0).value();

        AbstractXpSign xpSign = (AbstractXpSign) signObject;

        Sign sign = xpSign.getSign();

        final Player player = event.getPlayer();

        if (InteractTimeout.hasInteractTimeout(player.getUniqueId(), sign)) {
            return;
        }
        InteractTimeout.addInteractTimeout(player.getUniqueId(), sign);

        if (sign.getMetadata("XP_STORAGE_XPSIGN").isEmpty()) {
            return;
        }

        xpSign.updateSign();

        boolean openGuiOther = Permission.OPEN_GUI_OTHERS.hasPermission(player);
        boolean hasAccess = xpSign.hasAccess(player.getUniqueId());

        if (!hasAccess && !openGuiOther) {
            event.getPlayer().sendMessage(XPStorage.getGuiSettings()
                    .getText("notAuthorized"));

            return;
        }

        ItemStack interactItem = event.getItem();

        if (interactItem != null && interactItem.getType()
                .equals(SignInventory.INTERACT_MATERIAL)) {

            new SignInventory(player, xpSign).openInventory(player);
            event.setCancelled(true);
            player.updateInventory();
            return;
        }

        if (!hasAccess) {
            event.getPlayer().sendMessage(XPStorage.getGuiSettings()
                    .getText("notAuthorized"));

            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PlayerInformation playerInformation
                    = PlayerInformation.getPlayerInformation(
                            player.getUniqueId()
                    );

            if (playerInformation == null || playerInformation.isMessage()) {
                ExperienceManager expMan = new ExperienceManager(player);
                if (expMan.getCurrentExp() == 0) {

                    HashMap<String, String> replaceMap = new HashMap<>();

                    replaceMap.put("SIGN_INTERACT_MATERIAL",
                            SignInventory.INTERACT_MATERIAL.toString());

                    String message = XPStorage.getGuiSettings()
                            .getText("noXPLeft", replaceMap);

                    player.sendMessage(message);
                }
            }
            event.setCancelled(true);
            if (player.isSneaking()) {
                xpSign.increaseXp(player, 10);
            } else {
                xpSign.increaseXp(player);
            }
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                xpSign.decreaseXp(player, 10);
            } else {
                xpSign.decreaseXp(player);
            }
        }

    }
}
