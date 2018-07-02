package me.killje.util;

import org.bukkit.Bukkit;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class BukkitReflection {
    
    private final static String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";

    public static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String name = "net.minecraft.server." + VERSION + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }
    
    public static Class<?> getCBClass(String cbClassString) throws ClassNotFoundException {
        String name = "org.bukkit.craftbukkit." + VERSION + cbClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

}
