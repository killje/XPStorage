package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.UUID;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.gui.guiElement.GuiElement;
import me.killje.xpstorage.utils.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractSharedSign;

/**
 *
 * @author Zolder
 */
public class EditPlayerOptionsGroup extends EditPlayerOptions {
    
    private final AbstractSharedSign sign;
    private final UUID playerToEdit;
    private final UUID playerEditing;
    
    public EditPlayerOptionsGroup(AbstractSharedSign sign, UUID playerToEdit, UUID playerEditing) {
        super(sign, playerToEdit, playerEditing);
        this.sign = sign;
        this.playerToEdit = playerToEdit;
        this.playerEditing = playerEditing;
    }

    @Override
    protected ArrayList<GuiElement> getElements() {
        ArrayList<GuiElement> guiElements = super.getElements();
        
        if (playerToEdit.equals(sign.getOwner()) || playerToEdit.equals(playerEditing)) {
            return guiElements;
        }
        
        PlayerInformation playerInformationPlayerEditing = PlayerInformation.getPlayerInformation(playerEditing);
        
        if (playerEditing.equals(sign.getOwner()) ||
            (playerInformationPlayerEditing != null &&
            playerInformationPlayerEditing.getGroupRights(sign.getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_EDIT_PLAYERS)&&
            playerInformationPlayerEditing.getGroupRights(sign.getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS))) {
            if (PlayerInformation.getPlayerInformation(playerToEdit).getGroupRights(sign.getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS)) {
                this.addGuiElement(new RemovePlayerGroupCreateRights(playerToEdit, sign));
            }
            else {
                this.addGuiElement(new AddPlayerGroupCreateRights(playerToEdit, sign));
            }
        }

        
        
        return guiElements;
    }
    
    
    
}
