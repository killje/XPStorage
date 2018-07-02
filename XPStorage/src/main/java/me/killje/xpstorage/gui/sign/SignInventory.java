package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import me.killje.xpstorage.XPStorage;
import me.killje.gui.Exit;
import me.killje.gui.guiElement.GuiElement;
import me.killje.gui.InventoryUtils;
import me.killje.xpstorage.gui.Owner;
import me.killje.xpstorage.gui.customamount.CustomAmount;
import me.killje.util.GuiSettingsFromFile;
import me.killje.xpstorage.gui.settings.Settings;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.NormalSign;
import me.killje.xpstorage.xpsign.PlayerSign;
import me.killje.xpstorage.xpsign.SharedSign;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SignInventory extends InventoryUtils {

    static {
        INTERACT_MATERIAL = Material.getMaterial(XPStorage.getInstance().getConfig().getString("interactMaterial"));
    }

    private final AbstractXpSign xpSign;
    private final Player player;
    public final static Material INTERACT_MATERIAL;

    public SignInventory(Player player, AbstractXpSign xpSign) {
        super();
        this.xpSign = xpSign;
        this.player = player;
    }

    @Override
    protected void initInventory() {

        this
                .setInventoryName(GuiSettingsFromFile.getText("signInventory"))
                .addGuiElement(new GetAllXp(this.xpSign))
                .addGuiElement(new PutAllXp(this.xpSign))
                .addGuiElement(new CustomAmount(player, xpSign))
                .addGuiElement(new Settings(xpSign, player), 6)
                .addGuiElement(new Owner(this.xpSign))
                .addGuiElement(new Exit());
        if (this.xpSign.getOwner().equals(this.player.getUniqueId())) {
            this
                    .addGuiElement(new ChangeTo(player, this.xpSign, NormalSign.class, "normal"))
                    .addGuiElement(new ChangeTo(player, this.xpSign, PlayerSign.class, "ender"))
                    .addGuiElement(new ChangeTo(player, this.xpSign, SharedSign.class, "shared"))
                    .addGuiElement(new ChangeToGroup(player, this.xpSign));
        }

        ArrayList<GuiElement> guiElements = xpSign.getAdditionalGuiElements(player);

        if (guiElements != null && guiElements.size() > 0) {
            this.nextRow(true);
            for (GuiElement guiElement : guiElements) {
                this.addGuiElement(guiElement);
            }
        }

    }

    @Override
    protected Plugin getInstance() {
        return XPStorage.getInstance();
    }

}
