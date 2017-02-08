package me.killje.xpstorage.utils;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagString;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;

/**
 *
 * @author Zolder
 */
public class HeadUtils {
    
    private final static HashMap<String, String> TEXTURES = new HashMap<>();
    
    public static ItemStack getPlayerHead(String player) {
        
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(player);
        head.setItemMeta(meta);
        return CraftItemStack.asBukkitCopy(CraftItemStack.asNMSCopy(head));
    }
    
    public static ItemStack getPlayerHead(OfflinePlayer player) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        net.minecraft.server.v1_11_R1.ItemStack itemstack = CraftItemStack.asNMSCopy(head);
        NBTTagCompound comp = itemstack.getTag();
        if(comp == null) {
            comp = new NBTTagCompound();
        }
        NBTTagCompound SkullOwner = new NBTTagCompound();
        SkullOwner.setString("Id", player.getUniqueId().toString());
       
        comp.set("SkullOwner", SkullOwner);
        
        itemstack.setTag(comp);
        head = CraftItemStack.asBukkitCopy(itemstack);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(player.getName());
        head.setItemMeta(meta);
        return head;
    }
    
    public static ItemStack getTexturedHead(String texture) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        net.minecraft.server.v1_11_R1.ItemStack itemstack = CraftItemStack.asNMSCopy(head);
        NBTTagCompound comp = itemstack.getTag();
        if(comp == null) {
            comp = new NBTTagCompound();
        }
        NBTTagCompound SkullOwner = new NBTTagCompound();
        NBTTagCompound Properties = new NBTTagCompound();
        NBTTagList textures = new NBTTagList();
        NBTTagString Value = new NBTTagString(texture);
        NBTTagCompound ValueCompund = new NBTTagCompound();
        ValueCompund.set("Value", Value);
        textures.add(ValueCompund);
        Properties.set("textures", textures);
        SkullOwner.set("Properties", Properties);
        if (TEXTURES.containsKey(texture)) {
            SkullOwner.setString("Id", TEXTURES.get(texture));
        } else {
            String randomUUID = UUID.randomUUID().toString();
            TEXTURES.put(texture, randomUUID);
            SkullOwner.setString("Id", randomUUID);
        }
        comp.set("SkullOwner", SkullOwner);
        
        itemstack.setTag(comp);
        head = CraftItemStack.asBukkitCopy(itemstack);
        return head;
    }
}
