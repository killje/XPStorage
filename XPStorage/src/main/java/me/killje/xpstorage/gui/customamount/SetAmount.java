package me.killje.xpstorage.gui.customamount;

import me.desht.dhutils.ExperienceManager;
import me.killje.spigotgui.character.AmountStorage;
import me.killje.spigotgui.character.SetAmountButton;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
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

        int totalXp = xpSign.getCurrentXp() + experienceManager.getCurrentExp();
        int maxLVL = experienceManager.getLevelForExp(totalXp);

        String characterToShow = "confirmCharacter";

        if (maxLVL < amountStorage.getCurrent()) {
            characterToShow = "confirmCharacterNotAvailable";
        }
        return guiSettings.getItemStack(characterToShow);
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        ExperienceManager experienceManager = new ExperienceManager(this.player);

        int totalXp = xpSign.getCurrentXp() + experienceManager.getCurrentExp();
        int maxLVL = experienceManager.getLevelForExp(totalXp);

        if (maxLVL < amountStorage.getCurrent()) {
            event.getWhoClicked().sendMessage(currentInventoryUtils.getGuiSettings().getText("amounthNotAvaialable"));
            return;
        }

        int currentLevel = experienceManager.getLevelForExp(experienceManager.getCurrentExp());
        int levelSelected = amountStorage.getCurrent();

        if (currentLevel < levelSelected) {
            xpSign.decreaseXp(player, levelSelected - currentLevel);
        } else {
            xpSign.increaseXp(player, currentLevel - levelSelected);
        }
        currentInventoryUtils.closeInventory(player);
    }

}
