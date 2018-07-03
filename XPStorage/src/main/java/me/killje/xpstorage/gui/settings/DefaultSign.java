package me.killje.xpstorage.gui.settings;

import me.killje.gui.Exit;
import me.killje.gui.InventoryUtils;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.xpsign.NormalSign;
import me.killje.xpstorage.xpsign.PlayerSign;
import me.killje.xpstorage.xpsign.SharedSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DefaultSign extends InventoryUtils implements GuiElement {
    
    private final Player player;

    public DefaultSign(Player player) {
        this.player = player;
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("defaultSign");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(event.getWhoClicked(), this);
    }
    
    @Override
    protected void initInventory() {
        this
                .setInventoryName(GuiSettingsFromFile.getText("pickDefaultSign"))
                .addGuiElement(new DefaultSignPicker(player, NormalSign.class, "normal"))
                .addGuiElement(new DefaultSignPicker(player, PlayerSign.class, "ender"))
                .addGuiElement(new DefaultSignPicker(player, SharedSign.class, "shared"))
                .addGuiElement(new Exit(), 8);
    }
    
}
