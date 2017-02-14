package me.killje.xpstorage.gui.sign;

import java.util.ArrayList;
import me.killje.xpstorage.gui.Exit;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.gui.InventoryUtils;
import me.killje.xpstorage.gui.Owner;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;


/**
 *
 * @author Zolder
 */
public class SignInventory extends InventoryUtils {
    
    private final AbstractXpSign xpSign;
    private final Player player;
    
    public SignInventory(Player player, AbstractXpSign xpSign) {
        super();
        this.xpSign = xpSign;
        this.player = player;
    }
    
    @Override
    protected void initInventory() {
        
        this
            .setInventoryName("XP Storage settings")
            .addGuiElement(new GetAllXp(this.xpSign))
            .addGuiElement(new PutAllXp(this.xpSign))
            .addGuiElement(new Owner(this.xpSign, player), 7)
            .addGuiElement(new Exit());
        if (this.xpSign.getOwner().equals(this.player.getUniqueId())) {
            this.addGuiElement(new ChangeToNormal(this.xpSign))
                .addGuiElement(new ChangeToEnder(this.xpSign))
                .addGuiElement(new ChangeToShared(this.xpSign))
                .addGuiElement(new ChangeToGroup(this.xpSign));
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
