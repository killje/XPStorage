package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.Exit;
import me.killje.xpstorage.gui.GuiElement;
import me.killje.xpstorage.gui.InventoryUtils;
import me.killje.xpstorage.gui.SimpleGuiElement;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Zolder
 */
public class EditPlayerOptions extends InventoryUtils {
    
    private final AbstractSharedSign sign;
    private final UUID playerToEdit;
    private final UUID playerEditing;
    
    public EditPlayerOptions(AbstractSharedSign sign, UUID playerToEdit, UUID playerEditing) {
        super();
        this.sign = sign;
        this.playerToEdit = playerToEdit;
        this.playerEditing = playerEditing;
    }
    
    protected ArrayList<GuiElement> getElements(){
        ArrayList<GuiElement> guiElements = new ArrayList<>();
        
        if (playerToEdit.equals(sign.getOwner())) {
            ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
            this.addGuiElement(new SimpleGuiElement(itemStack.getData(), "You can't edit the owner"));
        }
        else if (playerToEdit.equals(playerEditing)) {
            ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
            this.addGuiElement(new SimpleGuiElement(itemStack.getData(), "You can't edit yourself"));
        }
        else {
            PlayerInformation playerInformationPlayerEditing = PlayerInformation.getPlayerInformation(playerEditing);
            if (playerEditing.equals(sign.getOwner())) {
                this.addGuiElement(new SetNewOwner(playerToEdit, sign));
            }
            if (playerEditing.equals(sign.getOwner()) ||
                (playerInformationPlayerEditing != null &&
                playerInformationPlayerEditing.getGroupRights(sign.getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_EDIT_PLAYERS))) {
                if (PlayerInformation.getPlayerInformation(playerToEdit).getGroupRights(sign.getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_EDIT_PLAYERS)) {
                    this.addGuiElement(new RemovePlayerEditRights(playerToEdit, sign));
                }
                else {
                    this.addGuiElement(new AddPlayerEditRights(playerToEdit, sign));
                }
            }
            
        }
        
        return guiElements;
    }
    
    @Override
    protected void initInventory() {
        
        ArrayList<GuiElement> guiElements = getElements();
        
        if (guiElements != null && guiElements.size() > 0) {
            for (GuiElement guiElement : guiElements) {
                this.addGuiElement(guiElement);
            }
        }
        
        if (!playerToEdit.equals(sign.getOwner()) && !playerToEdit.equals(playerEditing)) {
            this.addGuiElement(new RemovePlayer(playerToEdit, sign));
        }
        
        this.addGuiElement(new Exit(), 8);
        
        this.setInventoryName("Edit player | " + Bukkit.getOfflinePlayer(playerToEdit).getName());
        
    }
    
}
