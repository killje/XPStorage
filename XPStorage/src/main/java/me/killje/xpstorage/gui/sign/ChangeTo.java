package me.killje.xpstorage.gui.sign;

import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeTo implements GuiElement{
    
    private AbstractXpSign xpSign;
    private final String guiSettingsName;
    private final Class<? extends AbstractXpSign> signClass;
    private final Player player;

    public ChangeTo(Player player, AbstractXpSign xpSign, Class<? extends AbstractXpSign> signClass, String guiSettingsName) {
        this.xpSign = xpSign;
        this.guiSettingsName = guiSettingsName;
        this.signClass = signClass;
        this.player = player;
    }
    
    

    @Override
    public ItemStack getItemStack() {
        if (xpSign.getClass().equals(signClass)) {
            return GuiSettingsFromFile.getItemStack("selected." + guiSettingsName);
        }
        return GuiSettingsFromFile.getItemStack("changeTo." + guiSettingsName);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        if (xpSign.getClass().equals(signClass)) {
            return;
        }
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)){
            return;
        }
        if (!xpSign.destroySign(player)) {
            return;
        }
        
        xpSign = AbstractXpSign.createSign(signClass, xpSign.getSign(), entity.getUniqueId());
        xpSign.changeSign();
        
        currentInventoryUtils.closeInventory(entity);
    }
    
}
