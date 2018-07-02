package me.killje.xpstorage.gui.customamount;

import java.util.HashMap;
import java.util.Map;
import me.desht.dhutils.ExperienceManager;
import me.killje.gui.InventoryUtils;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.characters.NumberBoard;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class CustomAmount extends NumberBoard implements GuiElement {

    private final Player player;
    private final AbstractXpSign xpSign;
    private final ExperienceManager experienceManager;
    
    public CustomAmount(Player player, AbstractXpSign xpSign) {
        super(player, new SetAmount(player, xpSign));
        this.player = player;
        this.xpSign = xpSign;
        this.experienceManager = new ExperienceManager(player);
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("customAmountXP");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(player, this);
    }

    @Override
    protected String getInventoryName() {
        
        int totalXp = xpSign.getCurrentXp() + experienceManager.getCurrentExp();
        int maxLVL = experienceManager.getLevelForExp(totalXp);
        
        Map<String, String> replaceList = new HashMap<>();
        
        replaceList.put("CURRENT_LEVEL", getAmountStorage().getCurrent() + "");
        replaceList.put("MAX_LEVEL", maxLVL + "");
        
        return GuiSettingsFromFile.getText("customAmount", replaceList);
    }

}
