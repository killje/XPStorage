package me.killje.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.ChatColor;
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
public class GuiSettingsFromFile {

    private final static clsConfiguration guiFile = new clsConfiguration(PluginUtils.getPlugin(), "GUI.yml", true);
    private final static Map<String, String> chatColors = new HashMap<>();

    static {
        ChatColor[] chatColorList = ChatColor.values();
        for (ChatColor chatColor : chatColorList) {
            chatColors.put(chatColor.name(), chatColor.toString());
        }
    }

    private static ItemStack createItemStack(Material material, String displayName, List<String> lore) {
        return createItemStack(material, displayName, lore,(short) -1);
    }
    
    private static ItemStack createItemStack(Material material, String displayName, List<String> lore, short damage) {
        if (material == null) {
            return null;
        }
        ItemStack itemStack;
        if (damage != -1) {
            itemStack = new ItemStack(material, 1, damage);
        } else {
            itemStack = new ItemStack(material);
        }
        setDataOnItemStack(itemStack, displayName, lore);
        return itemStack;
    }
    
    private static ItemStack createDyeItemStack(String displayName, List<String> lore) {
        return createDyeItemStack(displayName, lore, null);
    }
    
    private static ItemStack createDyeItemStack(String displayName, List<String> lore, DyeColor color) {
        ItemStack itemStack;
        if (color!= null) {
            itemStack = new Dye(color).toItemStack(1);
        } else {
            itemStack = new Dye().toItemStack(1);
        }
        setDataOnItemStack(itemStack, displayName, lore);
        return itemStack;
    }
    
    private static void setDataOnItemStack(ItemStack itemStack, String displayName, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
    }

    public static ItemStack getItemStack(String name) {
        return getItemStack(name, new HashMap<>());
    }

    public static void reloadConfig() {
        guiFile.ReloadConfig();
    }
    
    public static ItemStack getItemStack(String name, Map<String, String> replaceMap) {

        GUIElementInformation elementInformation = new GUIElementInformation(name);


        String type = elementInformation.getType();
        
        if (type == null) {
            return null;
        }
        
        String value = elementInformation.getValue();
        String displayName = elementInformation.getDisplayName(replaceMap);
        List<String> lore = elementInformation.getLore();
        String color = elementInformation.getColor();
        
        switch (type) {

            case "head":
                return HeadUtils.getTexturedHead(value, displayName, lore);
            case "item":
                ItemStack itemStack;
                switch (value) {
                    // Because dye is not a material stack in bukkit -.-
                    case "DYE":
                        if (color!= null) {
                            itemStack = createDyeItemStack(displayName, lore, DyeColor.valueOf(color));
                        } else {
                            itemStack = createDyeItemStack(displayName, lore);
                        }
                        break;
                    default:
                        Material material = Material.getMaterial(value);
                        if (color != null) {
                            itemStack = createItemStack(material, displayName, lore, DyeColor.valueOf(color).getWoolData());
                        } else {
                            itemStack = createItemStack(material, displayName, lore);
                        }
                        break;
                }
                return itemStack;
            default:
                return null;
        }

    }

    public static String getText(String name) {
        return getText(name, new HashMap<>());
    }

    public static String getText(String name, Map<String, String> replaceMap) {
        ConfigurationSection configurationSection = guiFile.GetConfig().getConfigurationSection("texts");
        String text = configurationSection.getString(name);

        for (Map.Entry<String, String> replaceItem : chatColors.entrySet()) {
            String replaceFrom = replaceItem.getKey();
            String replaceTo = replaceItem.getValue();
            text = text.replaceAll("&" + replaceFrom, replaceTo);
        }
        for (Map.Entry<String, String> replaceItem : replaceMap.entrySet()) {
            String replaceFrom = replaceItem.getKey();
            String replaceTo = replaceItem.getValue();
            text = text.replaceAll("%" + replaceFrom, replaceTo);
        }
        return text;
    }

    private static class GUIElementInformation {

        private final ConfigurationSection configurationSection = guiFile.GetConfig().getConfigurationSection("GUIElements");
        private String displayName;
        private String type;
        private String color = null;
        private String value;
        private List<String> lore;

        public GUIElementInformation(String name) {
            extractInformation(name);
        }

        private void extractInformation(String name) {

            ConfigurationSection section = configurationSection.getConfigurationSection(name);
            
            if (section == null) {
                PluginUtils.getLogger().log(Level.SEVERE, "Could not find GUI element with name ''{0}''", name);
                return;
            }
            
            if (type == null && section.contains("type")) {
                type = section.getString("type");
            }

            if (value == null && section.contains("value")) {
                value = section.getString("value");
            }
            
            if (color == null && section.contains("color")) {
                color = section.getString("color");
            }

            if (displayName == null && section.contains("displayName")) {
                displayName = section.getString("displayName");
            }

            if (lore == null && section.contains("lore")) {
                lore = section.getStringList("lore");
            }

            if (section.contains("use")) {
                if (section.isList("use")) {
                    for (String sectionUse : section.getStringList("use")) {
                        extractInformation(sectionUse);
                    }
                } else {
                    extractInformation(section.getString("use"));
                }
            }
        }

        public String getDisplayName(Map<String, String> replaceMap) {

            String finalDisplayName = displayName;
            for (Map.Entry<String, String> replaceItem : chatColors.entrySet()) {
                String replaceFrom = replaceItem.getKey();
                String replaceTo = replaceItem.getValue();
                finalDisplayName = finalDisplayName.replaceAll("&" + replaceFrom, replaceTo);
            }
            for (Map.Entry<String, String> replaceItem : replaceMap.entrySet()) {
                String replaceFrom = replaceItem.getKey();
                String replaceTo = replaceItem.getValue();
                finalDisplayName = finalDisplayName.replaceAll("%" + replaceFrom, replaceTo);
            }
            return finalDisplayName;
        }

        public List<String> getLore() {
            return lore;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
        
        public String getColor() {
            return color;
        }
    }

}
