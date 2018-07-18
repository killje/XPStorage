package me.killje.xpstorage.gui.customamount;

import java.util.HashMap;
import java.util.Map;
import me.desht.dhutils.ExperienceManager;
import me.killje.spigotgui.character.NumberBoard;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Sets the level to the specified amount typed in on a number board
 *
 * This is both a icon and a inventory. The icon will open the inventory of this
 * class
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class CustomAmount extends NumberBoard implements GuiElement {

    /**
     * The manager that does the xp calculations
     */
    private final ExperienceManager experienceManager;

    /**
     * The player settings its level
     */
    private final Player player;

    /**
     * The sign the xp will be used from
     */
    private final AbstractXpSign xpSign;

    /**
     * Creates a Icon that when clicked on will open a number board to set your
     * xp level
     *
     * @param player The player that is setting it's xp level
     * @param xpSign The sign of which the xp is used or set to
     */
    public CustomAmount(Player player, AbstractXpSign xpSign) {
        super(XPStorage.getGuiSettings(),
                player, new SetAmount(player, xpSign));
        this.player = player;
        this.xpSign = xpSign;
        this.experienceManager = new ExperienceManager(player);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("customAmountXP");
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {
        currentInventoryBase.openNewInventory(player, this);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected String getInventoryName() {

        int totalXp = xpSign.getCurrentXp() + experienceManager.getCurrentExp();
        int maxLVL = experienceManager.getLevelForExp(totalXp);

        Map<String, String> replaceList = new HashMap<>();

        replaceList.put("CURRENT_LEVEL", getAmountStorage().getCurrent() + "");
        replaceList.put("MAX_LEVEL", maxLVL + "");

        return getGuiSettings().getText("customAmount", replaceList);
    }

}
