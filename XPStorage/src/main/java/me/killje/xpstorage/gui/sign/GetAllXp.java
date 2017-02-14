package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zolder
 */
public class GetAllXp implements GuiElement{
    
    private final AbstractXpSign xpSign;

    public GetAllXp(AbstractXpSign xpSign) {
        this.xpSign = xpSign;
    }
    
    

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.MILK_BUCKET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Get all XP from sign");
        List<String> lore = new ArrayList<>();
        lore.add("Gives all the XP from the sign to you");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        xpSign.allXpOut((Player) entity);
        event.getView().close();
    }
    
}
