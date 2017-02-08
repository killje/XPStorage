package me.killje.xpstorage.gui.choosegroup;

import me.killje.xpstorage.gui.GuiElement;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class CreateNewGroup implements GuiElement {
    
    @Override
    public ItemStack getItemStack() {
        return createSimpleItemStack(Material.PAPER, "Create new group");
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        FancyMessage fancyMessage = new FancyMessage("Click here").color(ChatColor.RED).style(ChatColor.UNDERLINE).suggest("/createXpGroup ").
            then(" and enter the name of your group");
        fancyMessage.send(event.getWhoClicked());
        event.getWhoClicked().closeInventory();
        
    }
    
}
