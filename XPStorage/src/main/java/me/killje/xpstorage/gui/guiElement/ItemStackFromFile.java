/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.killje.xpstorage.gui.guiElement;

import java.util.List;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.utils.HeadUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ItemStackFromFile {
    
    private static ItemStack createItemStack(Material material, String displayName, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    private static ItemStack createItemStack(Material material, String displayName, int damageValue, List<String> lore){
        ItemStack itemStack = new ItemStack(material, 1,(short) damageValue);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public static ItemStack getItemStack(String name, String displayName) {
        return getItemStack(name, displayName, null);
    }
    
    public static ItemStack getItemStack(String name, String displayName, List<String> lore) {
        
        ConfigurationSection configurationSection = XPStorage.getInstance().getConfig().getConfigurationSection("GUIElements");
        
        ConfigurationSection currentSection = configurationSection.getConfigurationSection(name);
        
        while (currentSection.contains("use")) {
            currentSection = configurationSection.getConfigurationSection(name);
            
            name = currentSection.getString("use");
            
        }   
        String type = currentSection.getString("type");
        String value= currentSection.getString("value");
        
        switch (type) {
            
            case "head":
                return HeadUtils.getTexturedHead(value, displayName, lore);
            case "dye":
                ItemStack returnValue = new Dye(DyeColor.valueOf(value)).toItemStack(1);
                ItemMeta itemMeta = returnValue.getItemMeta();
                itemMeta.setDisplayName(displayName);
                if (lore != null) {
                    itemMeta.setLore(lore);
                }
                System.out.println(returnValue);
                returnValue.setItemMeta(itemMeta);
                return returnValue;
            case "glassPane":
                return createItemStack(Material.STAINED_GLASS_PANE, displayName, DyeColor.valueOf(value).getWoolData(), lore);
            case "item":
                return createItemStack(Material.getMaterial(value), displayName, lore);
        }
        return null;
        
    }

}
