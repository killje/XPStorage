package me.killje.xpstorage.gui.customamount;

import me.killje.spigotgui.character.AmountStorage;
import me.killje.spigotgui.character.SetAmountButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.util.ExperienceManager;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SetAmount extends SetAmountButton {

    private AmountStorage amountStorage;
    private final Player player;
    private final AbstractXpSign xpSign;

    public SetAmount(Player player, AbstractXpSign xpSign) {
        this.player = player;
        this.xpSign = xpSign;
    }

    @Override
    public void setAmountStorage(AmountStorage amountStorage) {
        this.amountStorage = amountStorage;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        ExperienceManager experienceManager = new ExperienceManager(this.player);

        int totalXp = xpSign.getCurrentXp() + experienceManager.getTotalExperience();
        int maxLVL = experienceManager.getMaxLevel(totalXp);

        String characterToShow = "confirmCharacter";

        if (maxLVL < amountStorage.getCurrent()) {
            characterToShow = "confirmCharacterNotAvailable";
        }
        return guiSettings.getItemStack(characterToShow);
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {
        ExperienceManager experienceManager = new ExperienceManager(this.player);

        int totalXp = xpSign.getCurrentXp() + experienceManager.getTotalExperience();
        int maxLVL = experienceManager.getMaxLevel(totalXp);

        if (maxLVL < amountStorage.getCurrent()) {
            event.getWhoClicked().sendMessage(currentinventoryBase.getGuiSettings().getText("amounthNotAvaialable"));
            return;
        }

        int currentLevel = experienceManager.getLevel();
        int levelSelected = amountStorage.getCurrent();

        if (currentLevel < levelSelected) {
            xpSign.decreaseXp(player, levelSelected - currentLevel);
        } else {
            xpSign.increaseXp(player, currentLevel - levelSelected);
        }
        currentinventoryBase.closeInventory(player);
    }

}
