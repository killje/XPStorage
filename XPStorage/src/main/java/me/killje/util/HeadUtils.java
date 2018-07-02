package me.killje.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import me.killje.xpstorage.XPStorage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class HeadUtils {

    private final static HashMap<String, String> TEXTURES = new HashMap<>();

    private final static Constructor<?> NMS_NBTTagCompound_Constructor;
    private final static Method NMS_NBTTagCompound_set;
    private final static Method NMS_NBTTagCompound_setString;

    private final static Method NMS_ItemStack_getTag;
    private final static Method NMS_ItemStack_setTag;

    private final static Class<?> CB_CraftItemStack;
    private final static Method CB_CraftItemStack_asNMSCopy;
    private final static Method CB_CraftItemStack_asBukkitCopy;

    private final static Constructor<?> NMS_NBTTagList_Constructor;
    private final static Method NMS_NBTTagList_add;

    private final static Constructor<?> NMS_NBTTagString_Constructor_String;

    static {

        Constructor<?> NMS_NBTTagCompound_Constructor_Assignment;
        Method NMS_NBTTagCompound_set_Assignment;
        Method NMS_NBTTagCompound_setString_Assignment;

        Method NMS_ItemStack_getTag_Assignment;
        Method NMS_ItemStack_setTag_Assignment;

        Class<?> CB_CraftItemStack_Assignment;
        Method CB_CraftItemStack_asNMSCopy_Assignment;
        Method CB_CraftItemStack_asBukkitCopy_Assignment;

        Constructor<?> NMS_NBTTagList_Constructor_Assignment;
        Method NMS_NBTTagList_add_Assignment;

        Constructor<?> NMS_NBTTagString_Constructor_String_Assignment;

        try {
            Class<?> NMS_NBTBase = BukkitReflection.getNMSClass("NBTBase");

            Class<?> NMS_NBTTagCompound = BukkitReflection.getNMSClass("NBTTagCompound");
            NMS_NBTTagCompound_Constructor_Assignment = NMS_NBTTagCompound.getConstructor();
            NMS_NBTTagCompound_set_Assignment = NMS_NBTTagCompound.getMethod("set", String.class, NMS_NBTBase);
            NMS_NBTTagCompound_setString_Assignment = NMS_NBTTagCompound.getMethod("setString", String.class, String.class);

            Class<?> NMS_ItemStack = BukkitReflection.getNMSClass("ItemStack");
            NMS_ItemStack_getTag_Assignment = NMS_ItemStack.getMethod("getTag");
            NMS_ItemStack_setTag_Assignment = NMS_ItemStack.getMethod("setTag", NMS_NBTTagCompound);

            CB_CraftItemStack_Assignment = BukkitReflection.getCBClass("inventory.CraftItemStack");
            CB_CraftItemStack_asNMSCopy_Assignment = CB_CraftItemStack_Assignment.getMethod("asNMSCopy", ItemStack.class);
            CB_CraftItemStack_asBukkitCopy_Assignment = CB_CraftItemStack_Assignment.getMethod("asBukkitCopy", NMS_ItemStack);

            Class<?> NMS_NBTTagList = BukkitReflection.getNMSClass("NBTTagList");
            NMS_NBTTagList_Constructor_Assignment = NMS_NBTTagList.getConstructor();
            NMS_NBTTagList_add_Assignment = NMS_NBTTagList.getMethod("add", NMS_NBTBase);

            Class<?> NMS_NBTTagString = BukkitReflection.getNMSClass("NBTTagString");
            NMS_NBTTagString_Constructor_String_Assignment = NMS_NBTTagString.getConstructor(String.class);

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            XPStorage.getInstance().getLogger().log(Level.SEVERE, null, ex);

            NMS_NBTTagCompound_Constructor_Assignment = null;
            NMS_NBTTagCompound_set_Assignment = null;
            NMS_NBTTagCompound_setString_Assignment = null;

            NMS_ItemStack_getTag_Assignment = null;
            NMS_ItemStack_setTag_Assignment = null;

            CB_CraftItemStack_Assignment = null;
            CB_CraftItemStack_asNMSCopy_Assignment = null;
            CB_CraftItemStack_asBukkitCopy_Assignment = null;

            NMS_NBTTagList_Constructor_Assignment = null;
            NMS_NBTTagList_add_Assignment = null;

            NMS_NBTTagString_Constructor_String_Assignment = null;

        }

        NMS_NBTTagCompound_Constructor = NMS_NBTTagCompound_Constructor_Assignment;
        NMS_NBTTagCompound_set = NMS_NBTTagCompound_set_Assignment;
        NMS_NBTTagCompound_setString = NMS_NBTTagCompound_setString_Assignment;

        NMS_ItemStack_getTag = NMS_ItemStack_getTag_Assignment;
        NMS_ItemStack_setTag = NMS_ItemStack_setTag_Assignment;

        CB_CraftItemStack = CB_CraftItemStack_Assignment;
        CB_CraftItemStack_asNMSCopy = CB_CraftItemStack_asNMSCopy_Assignment;
        CB_CraftItemStack_asBukkitCopy = CB_CraftItemStack_asBukkitCopy_Assignment;

        NMS_NBTTagList_Constructor = NMS_NBTTagList_Constructor_Assignment;
        NMS_NBTTagList_add = NMS_NBTTagList_add_Assignment;

        NMS_NBTTagString_Constructor_String = NMS_NBTTagString_Constructor_String_Assignment;
    }

    public static ItemStack getPlayerHead(OfflinePlayer player) {
        try {
            Object NMS_NBTTagCompound_skullOwner = NMS_NBTTagCompound_Constructor.newInstance();

            NMS_NBTTagCompound_setString.invoke(NMS_NBTTagCompound_skullOwner, "Id", player.getUniqueId().toString());

            ItemStack head = getHead(NMS_NBTTagCompound_skullOwner);

            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwner(player.getName());
            head.setItemMeta(meta);

            return head;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex) {
            XPStorage.getInstance().getLogger().log(Level.SEVERE, null, ex);
        }
        return getBaseHead();
    }

    public static ItemStack getTexturedHead(String texture, String displayName) {
        return getTexturedHead(texture, displayName, null);
    }

    public static ItemStack getTexturedHead(String texture, String displayName, List<String> lore) {
        try {
            Object NMS_NBTTagCompound_skullOwner = NMS_NBTTagCompound_Constructor.newInstance();
            Object NMS_NBTTagCompound_properties = NMS_NBTTagCompound_Constructor.newInstance();
            Object NMS_NBTTagCompound_valueCompound = NMS_NBTTagCompound_Constructor.newInstance();
            
            Object NMS_NBTTagList_textures = NMS_NBTTagList_Constructor.newInstance();

            Object NMS_NBTTagString_value = NMS_NBTTagString_Constructor_String.newInstance(texture);

            NMS_NBTTagCompound_set.invoke(NMS_NBTTagCompound_valueCompound, "Value", NMS_NBTTagString_value);

            NMS_NBTTagList_add.invoke(NMS_NBTTagList_textures, NMS_NBTTagCompound_valueCompound);

            NMS_NBTTagCompound_set.invoke(NMS_NBTTagCompound_properties, "textures", NMS_NBTTagList_textures);

            NMS_NBTTagCompound_set.invoke(NMS_NBTTagCompound_skullOwner, "Properties", NMS_NBTTagCompound_properties);

            String uuid;
            if (TEXTURES.containsKey(texture)) {
                uuid = TEXTURES.get(texture);
            } else {
                uuid = UUID.randomUUID().toString();
                TEXTURES.put(texture, uuid);
            }

            NMS_NBTTagCompound_setString.invoke(NMS_NBTTagCompound_skullOwner, "Id", uuid);

            ItemStack head = getHead(NMS_NBTTagCompound_skullOwner);

            ItemMeta itemMeta = head.getItemMeta();
            itemMeta.setDisplayName(displayName);
            if (lore != null) {
                itemMeta.setLore(lore);
            }
            head.setItemMeta(itemMeta);

            return head;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex) {
            XPStorage.getInstance().getLogger().log(Level.SEVERE, null, ex);
        }
        return getBaseHead();
    }

    private static ItemStack getHead(Object NMS_NBTTagCompound_skullOwner) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        ItemStack head = getBaseHead();

        Object NMS_ItemStack_itemStack = CB_CraftItemStack_asNMSCopy.invoke(CB_CraftItemStack, head);

        Object NMS_NBTTagCompound_comp = NMS_ItemStack_getTag.invoke(NMS_ItemStack_itemStack);

        if (NMS_NBTTagCompound_comp == null) {
            NMS_NBTTagCompound_comp = NMS_NBTTagCompound_Constructor.newInstance();
        }

        NMS_NBTTagCompound_set.invoke(NMS_NBTTagCompound_comp, "SkullOwner", NMS_NBTTagCompound_skullOwner);

        NMS_ItemStack_setTag.invoke(NMS_ItemStack_itemStack, NMS_NBTTagCompound_comp);

        return (ItemStack) CB_CraftItemStack_asBukkitCopy.invoke(CB_CraftItemStack, NMS_ItemStack_itemStack);
    }
    
    private static ItemStack getBaseHead() {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    }
}
