package me.killje.xpstorage.gui.customamount;

import me.desht.dhutils.ExperienceManager;
import me.killje.spigotgui.character.AmountStorage;
import me.killje.spigotgui.character.SetAmountButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Icon for when a amount of xp has been set will be set to the player
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetAmount extends SetAmountButton {

    /**
     * The storage that contains the set level of xp
     */
    private AmountStorage amountStorage;
    /**
     * The player setting the xp level
     */
    private final Player player;
    /**
     * The sign to use for the xp
     */
    private final AbstractXpSign xpSign;

    /**
     * Icon for setting the level of xp on a player
     *
     * @param player The player to set the level of
     * @param xpSign The sign of which to use the xp of
     */
    public SetAmount(Player player, AbstractXpSign xpSign) {
        this.player = player;
        this.xpSign = xpSign;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void setAmountStorage(AmountStorage amountStorage) {
        this.amountStorage = amountStorage;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public ItemStack getItemStack(GuiSetting guiSettings) {
        ExperienceManager experienceManager
                = new ExperienceManager(this.player);

        int totalXp = xpSign.getCurrentXp() + experienceManager.getCurrentExp();
        int maxLVL = experienceManager.getLevelForExp(totalXp);

        String characterToShow = "confirmCharacter";

        if (maxLVL < amountStorage.getCurrent()) {
            characterToShow = "confirmCharacterNotAvailable";
        }
        return guiSettings.getItemStack(characterToShow);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void onInventoryClickEvent(
            InventoryBase currentInventoryBase, InventoryClickEvent event) {

        ExperienceManager experienceManager
                = new ExperienceManager(this.player);

        int totalXp = xpSign.getCurrentXp() + experienceManager.getCurrentExp();
        int maxLVL = experienceManager.getLevelForExp(totalXp);

        if (maxLVL < amountStorage.getCurrent()) {
            event.getWhoClicked().sendMessage(currentInventoryBase
                    .getGuiSettings().getText("amounthNotAvaialable"));
            return;
        }

        int currentLevel = experienceManager.getLevelForExp(
                experienceManager.getCurrentExp());

        int levelSelected = amountStorage.getCurrent();

        if (currentLevel < levelSelected) {
            xpSign.decreaseXp(player, levelSelected - currentLevel);
        } else {
            xpSign.increaseXp(player, currentLevel - levelSelected);
        }
        currentInventoryBase.closeInventory(player);
    }

}
