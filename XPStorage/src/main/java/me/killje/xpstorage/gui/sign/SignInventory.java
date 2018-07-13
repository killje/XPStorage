package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.gui.Owner;
import me.killje.xpstorage.gui.customamount.CustomAmount;
import me.killje.xpstorage.gui.settings.Settings;
import me.killje.xpstorage.permission.Permission;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.LocalPlayerSign;
import me.killje.xpstorage.xpsign.EnderPlayerSign;
import me.killje.xpstorage.xpsign.LocalGroupSign;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * First inventory, opened when clicking with the interact material on a
 * XPStorage sign
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class SignInventory extends InventoryBase {

    /**
     * Getting the material type to interact with from the config file
     */
    static {
        INTERACT_MATERIAL = Material.getMaterial(
                XPStorage.getPluginUtil().getConfig()
                        .getString("interactMaterial")
        );
    }

    /**
     * The material used to interact with XPStorage signs
     */
    public final static Material INTERACT_MATERIAL;

    /**
     * The sign clicked on
     */
    private final AbstractXpSign xpSign;
    /**
     * The player that clicked on the sign
     */
    private final Player player;

    /**
     * Inventory for a sign
     *
     * @param player The player that clicked on the sign
     * @param xpSign The sign being edited
     */
    public SignInventory(Player player, AbstractXpSign xpSign) {
        super(XPStorage.getGuiSettings());
        this.xpSign = xpSign;
        this.player = player;
    }

    @Override
    /**
     * {@inheritDoc}
     */
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
            if (Permission.CREATE_LOCAL_PLAYER.hasPermission(player)) {
                this.addGuiElement(new ChangeTo(player, this.xpSign,
                        LocalPlayerSign.class, "localPlayer"));
            }
            if (Permission.CREATE_ENDER_PLAYER.hasPermission(player)) {
                this.addGuiElement(new ChangeTo(player, this.xpSign,
                        EnderPlayerSign.class, "enderPlayer"));
            }
            if (Permission.CREATE_LOCAL_GROUP.hasPermission(player)) {
                this.addGuiElement(new ChangeTo(player, this.xpSign,
                        LocalGroupSign.class, "localGroup"));
            }
            if (Permission.CREATE_ENDER_GROUP.hasPermission(player)) {
                this.addGuiElement(new ChangeToGroup(player, this.xpSign));
            }
        }

        ArrayList<GuiElement> guiElements
                = xpSign.getAdditionalGuiElements(player);

        if (guiElements != null && guiElements.size() > 0) {
            this.nextRow(true);
            for (GuiElement guiElement : guiElements) {
                this.addGuiElement(guiElement);
            }
        }

    }

}
