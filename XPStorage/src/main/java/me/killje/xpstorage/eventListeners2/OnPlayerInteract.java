package me.killje.xpstorage.eventListeners2;

import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.sign.SignInventory;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.NormalSign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author Zolder
 */
public class OnPlayerInteract implements Listener {
    
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getClickedBlock().hasMetadata("XP_STORAGE_XPSIGN")) {
            return;
        }
        
        Object signObject = event.getClickedBlock().getMetadata("XP_STORAGE_XPSIGN").get(0).value();
        AbstractXpSign xpSign = (AbstractXpSign) signObject;
        
        Sign sign = xpSign.getSign();
        final Player player = event.getPlayer();
        if (sign.getMetadata("XP_STORAGE_XPSIGN").isEmpty()) {
            if (AbstractXpSign.getSaveName(player).equals(sign.getLine(3))) {
                NormalSign normalSign = new NormalSign(sign, player.getUniqueId());
                normalSign.getXpFromSign();
            } else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are not authorized to access someone else's xp Storage");
                return;
            }
        }
        if (!xpSign.hasAccess(player.getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You are not authorized to access someone else's xp Storage");
            return;
        }

        if (event.getItem() != null && event.getItem().getType().equals(Material.getMaterial(XPStorage.getInstance().getConfig().getString("interactMaterial")))) {
            player.openInventory(new SignInventory(player, xpSign).getInventory());
            event.setCancelled(true);
            player.updateInventory();
            return;
        }
        
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
            if (player.isSneaking()) {
                xpSign.decreaseXp(player);
            } else {
                xpSign.increaseXp(player);
            }
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                xpSign.prevItem();
            } else {
                xpSign.nextItem();
            }
        }

        
    }
}
