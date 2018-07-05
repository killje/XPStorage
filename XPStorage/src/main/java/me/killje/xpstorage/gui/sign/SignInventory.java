package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.Owner;
import me.killje.xpstorage.gui.customamount.CustomAmount;
import me.killje.xpstorage.gui.settings.Settings;
import me.killje.xpstorage.util.PluginUtils;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.NormalSign;
import me.killje.xpstorage.xpsign.PlayerSign;
import me.killje.xpstorage.xpsign.SharedSign;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SignInventory extends InventoryUtil {

    static {
        INTERACT_MATERIAL = Material.getMaterial(PluginUtils.getConfig().getString("interactMaterial"));
    }

    private final AbstractXpSign xpSign;
    private final Player player;
    public final static Material INTERACT_MATERIAL;

    public SignInventory(Player player, AbstractXpSign xpSign) {
        super(XPStorage.getGuiSettings());
        this.xpSign = xpSign;
        this.player = player;
    }

    @Override
    protected void initInventory() {

        this
                .setInventoryName(getGuiSettings().getText("signInventory"))
                .addGuiElement(new GetAllXp(this.xpSign))
                .addGuiElement(new PutAllXp(this.xpSign))
                .addGuiElement(new CustomAmount(player, xpSign))
                .addGuiElement(new Settings(xpSign, player), 6)
                .addGuiElement(new Owner(this.xpSign, player))
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

}
