package me.killje.xpstorage.gui.settings;

import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryUtil;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.xpsign.NormalSign;
import me.killje.xpstorage.xpsign.PlayerSign;
import me.killje.xpstorage.xpsign.SharedSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class DefaultSign extends InventoryUtil implements GuiElement {

    private final Player player;

    public DefaultSign(Player player) {
        super(XPStorage.getGuiSettings());
        this.player = player;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("defaultSign");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtil currentInventoryUtils, InventoryClickEvent event) {
        currentInventoryUtils.openNewInventory(event.getWhoClicked(), this);
    }

    @Override
    protected void initInventory() {
        this
                .setInventoryName(getGuiSettings().getText("pickDefaultSign"))
                .addGuiElement(new DefaultSignPicker(player, NormalSign.class, "normal"))
                .addGuiElement(new DefaultSignPicker(player, PlayerSign.class, "ender"))
                .addGuiElement(new DefaultSignPicker(player, SharedSign.class, "shared"))
                .addGuiElement(new Exit(), 8);
    }

}
