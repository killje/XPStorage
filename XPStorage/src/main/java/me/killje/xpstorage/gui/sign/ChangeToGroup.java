package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.choosegroup.ChooseGroup;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.guiElement.ItemStackFromFile;
import me.killje.xpstorage.gui.list.GroupList;
import me.killje.xpstorage.gui.list.GroupListGuiElement;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class ChangeToGroup implements GuiElement, GroupListGuiElement {
    
    private AbstractXpSign xpSign;
    private final boolean isSelected;
    private final boolean fromGroup;

    public ChangeToGroup(AbstractXpSign xpSign) {
        this(xpSign, false);
    }
    
    public ChangeToGroup(AbstractXpSign xpSign, boolean fromGroup) {
        this.xpSign = xpSign;
        this.isSelected = xpSign instanceof GroupSign;
        this.fromGroup = fromGroup;
    }
    
    @Override
    public ItemStack getItemStack() {
        
        List<String> lore = new ArrayList<>();
        if (fromGroup) {
            lore.add("Change this group sign to a diffrent group");
            return ItemStackFromFile.getItemStack("diffrentGroup", "Choose diffrent group", lore);
        }
        lore.add("Change the sign to a group sign.");
        lore.add("With this the XP will be available");
        lore.add("Within a selected group.");
        if (isSelected) {
            return ItemStackFromFile.getItemStack("selected.group", ChatColor.YELLOW + "Currently selected:" + ChatColor.WHITE + " Group sign", lore);
        }
        return ItemStackFromFile.getItemStack("changeToGroup", ChatColor.WHITE + "Group sign", lore);
    }

    @Override
    public void onGuiElementClickEvent(InventoryClickEvent event) {
        if (isSelected && !fromGroup) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)){
            event.setCancelled(true);
            return;
        }
        
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = new GroupList(player, this, xpSign).getInventory();
        
        event.getWhoClicked().openInventory(inventory);
        inventory.setItem(0, inventory.getItem(0));
        
        Bukkit.getScheduler().runTask(XPStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        });
    }
    
    @Override
    public GuiElement getGuiElement(UUID groupUUID, AbstractXpSign sign) {
        return new ChooseGroup(groupUUID, sign);
    }
    
}
