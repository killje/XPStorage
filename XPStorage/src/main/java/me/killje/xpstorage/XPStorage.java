package me.killje.xpstorage;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Patrick Beuks (killje)
 */
public class XPStorage extends JavaPlugin implements Listener {

    private final clsConfiguration signs = new clsConfiguration(this, "Signs.yml");
    private List<SignSaver> signsList;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        ConfigurationSerialization.registerClass(SignSaver.class);
        signsList = (List<SignSaver>) signs.GetConfig().getList("Signs");
        if (signsList == null) {
            signsList = new Stack<>();
        }
        boolean dirty = false;
        for (Iterator<SignSaver> it = signsList.iterator(); it.hasNext();) {
            SignSaver signSaver = it.next();
            Block block = signSaver.getLocation().getBlock();
            if (!isSign(block.getType())) {
                it.remove();
                dirty = true;
                continue;
            }
            block.setMetadata("OwnerUUID", new FixedMetadataValue(this, signSaver.getOwnerUuid()));
        }
        if (dirty) {
            signs.GetConfig().set("Signs", signsList);
            signs.SaveConfig();
        }
    }

    @EventHandler
    public void onSignWrite(SignChangeEvent event) {
        if (event.getLine(0).toLowerCase().equals("[xp storage]") || event.getLine(0).toLowerCase().equals("[xp]")) {
            event.setLine(0, ChatColor.BLUE + "[XP Storage]");
            event.setLine(1, "0");
            event.setLine(2, "[5] 20 100 500");
            String playername = getSaveName(event.getPlayer());
            event.setLine(3, playername);
            Block block = event.getBlock();
            String uuid = event.getPlayer().getUniqueId().toString();
            block.setMetadata("OwnerUUID", new FixedMetadataValue(this, uuid));
            signsList.add(new SignSaver(block, uuid));
            signs.GetConfig().set("Signs", signsList);
            signs.SaveConfig();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (isSign(event.getClickedBlock().getType())) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            if (sign.getLine(0).equals(ChatColor.BLUE + "[XP Storage]")) {
                final Player player = event.getPlayer();
                if (sign.getMetadata("OwnerUUID").isEmpty()) {
                    if (getSaveName(player).equals(sign.getLine(3))) {
                        String uuid = player.getUniqueId().toString();
                        sign.setMetadata("OwnerUUID", new FixedMetadataValue(this, uuid));
                        signsList.add(new SignSaver(sign.getBlock(), uuid));
                        signs.GetConfig().set("Signs", signsList);
                        signs.SaveConfig();
                    } else {
                        event.getPlayer().sendMessage(ChatColor.DARK_RED + "You are not authorized to acces someone else his/her xp Storage");
                        return;
                    }
                }
                if (!player.getUniqueId().toString().equals(sign.getMetadata("OwnerUUID").get(0).asString())) {
                    event.getPlayer().sendMessage(ChatColor.DARK_RED + "You are not authorized to acces someone else his/her xp Storage");
                    return;
                }
                SignUtil su = new SignUtil(sign, player);
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.setCancelled(true);
                    if (player.isSneaking()) {
                        su.decreaseXp();
                    } else {
                        su.increaseXp();
                    }
                } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (player.isSneaking()) {
                        su.prevItem();
                    } else {
                        su.nextItem();
                    }
                }
                sign.update();
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        destroyEvent(event);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        destroyEvent(event);
    }

    private void destroyEvent(BlockEvent event) {
        if (isSign(event.getBlock().getType())) {
            Sign sign = (Sign) event.getBlock().getState();
            if (sign.getLine(0).equals(ChatColor.BLUE + "[XP Storage]")) {
                for (Iterator<SignSaver> it = signsList.iterator(); it.hasNext();) {
                    SignSaver signSaver = it.next();
                    if (signSaver.getLocation().equals(event.getBlock().getLocation())) {
                        it.remove();
                        signs.GetConfig().set("Signs", signsList);
                        signs.SaveConfig();
                        return;
                    }
                }
            }
        }
    }

    private String getSaveName(Player player) {
        if (player.getName().length() > 15) {
            return player.getName().substring(0, 15);
        } else {
            return player.getName();
        }
    }

    private boolean isSign(Material material) {
        return material == Material.WALL_SIGN
                || material == Material.SIGN_POST
                || material == Material.SIGN;
    }
}
