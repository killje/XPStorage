package me.killje.xpstorage.eventListeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.killje.xpstorage.XPStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 * Listener for leaves decaying events
 *
 * @author Patrick Beuks (killje) <code@beuks.net>
 */
public class OnLeavesDecay extends OnBlockDestory {

    /**
     * This is called when a leaf decays in the game
     *
     * This cancels the event if a block is protected by this plugin and makes
     * the leave non decayable.
     *
     * @param event The event that belongs to the leaf decaying
     */
    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!isDestroyable(event.getBlock(), null)) {
            event.setCancelled(true);

            Block block = event.getBlock();
            if (XPStorage.IS_LEGACY) {
                byte d = block.getData();
                try {
                    Method method_setData = block.getClass()
                            .getMethod("setData", byte.class);
                    method_setData.invoke(block, (byte) (d % 4));
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException
                        | SecurityException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, null, ex);
                }
            } else {
                Material leave = block.getType();

                Class<?> class_Leave;
                try {
                    class_Leave
                            = Class.forName(
                                    "org.bukkit.block.data.type.Leaves"
                            );
                    Class<?> class_BlockData
                            = Class.forName("org.bukkit.block.data.BlockData");

                    Method method_Material_createBlockData
                            = Material.class.getMethod("createBlockData");
                    Method method_Block_setBlockData
                            = Block.class.getMethod(
                                    "setBlockData", class_BlockData);
                    Method method_Leaves_setPersistent
                            = class_Leave.getMethod(
                                    "setPersistent", boolean.class);

                    Object leavesDecay
                            = method_Material_createBlockData.invoke(leave);
                    method_Leaves_setPersistent.invoke(leavesDecay, true);
                    method_Block_setBlockData.invoke(block, leavesDecay);
                } catch (ClassNotFoundException | NoSuchMethodException
                        | SecurityException | IllegalAccessException
                        | IllegalArgumentException
                        | InvocationTargetException ex) {
                    Logger.getLogger(OnLeavesDecay.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
