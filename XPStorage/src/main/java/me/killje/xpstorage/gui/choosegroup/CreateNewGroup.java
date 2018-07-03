package me.killje.xpstorage.gui.choosegroup;

import me.killje.gui.InventoryUtils;
import me.killje.gui.characters.KeyBoard;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class CreateNewGroup extends KeyBoard implements GuiElement {

    private final Player player;
    
    public CreateNewGroup(Player player, AbstractXpSign xpSign) {
        super(player, new SetNewGroupNameButton(player, xpSign));
        this.player = player;
    }
    
    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("newGroup");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(player, this);
        
    }

    @Override
    protected String getInventoryName() {
        return getKeyBoardStringStorage().getCurrent();
    }
    
}
