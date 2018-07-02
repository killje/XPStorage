package me.killje.xpstorage.gui.groupsettings;

import me.killje.gui.InventoryUtils;
import me.killje.xpstorage.gui.characters.KeyBoard;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.xpsign.GroupSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (s2288842) <patrick.beuks@gmail.com>
 */
public class SetName extends KeyBoard implements GuiElement {
    
    private final Player player;

    public SetName(Player player, GroupSign groupSign) {
        super(player, new SetNameButton(groupSign));
        this.player = player;
    }

    @Override
    protected String getInventoryName() {
        return getKeyBoardStringStorage().getCurrent();
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("setName");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(player, this);
    }
    
}
