package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.UUID;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;

/**
 * Option panel for ender group signs extended from the normal player options
 *
 * This is to have a few extra options only available for ender groups
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EditPlayerOptionsGroup extends EditPlayerOptions {

    /**
     * The person opening the options
     */
    private final UUID playerEditing;
    /**
     * The player of who the options are shown for
     */
    private final UUID playerToEdit;
    /**
     * The sign being edited
     */
    private final AbstractGroupSign sign;

    /**
     * Creates a new inventory that shows a list of options for players from
     * ender group signs
     *
     * @param sign          The sign being edited
     * @param playerToEdit  The player selected
     * @param playerEditing The player editing the other player
     */
    public EditPlayerOptionsGroup(
            AbstractGroupSign sign, UUID playerToEdit, UUID playerEditing) {

        super(sign, playerToEdit, playerEditing);
        this.sign = sign;
        this.playerToEdit = playerToEdit;
        this.playerEditing = playerEditing;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected ArrayList<GuiElement> getElements() {
        ArrayList<GuiElement> guiElements = super.getElements();

        if (playerToEdit.equals(sign.getOwner())
                || playerToEdit.equals(playerEditing)) {
            return guiElements;
        }

        PlayerInformation playerInformationPlayerEditing
                = PlayerInformation.getPlayerInformation(playerEditing);

        if (playerEditing.equals(sign.getOwner())
                || (playerInformationPlayerEditing != null
                && playerInformationPlayerEditing.getGroupRights(
                        sign.getGroup().getGroupUuid()
                ).hasRight(GroupRights.Right.CAN_EDIT_PLAYERS)
                && playerInformationPlayerEditing.getGroupRights(
                        sign.getGroup().getGroupUuid()
                ).hasRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS))) {

            if (PlayerInformation.getPlayerInformation(playerToEdit)
                    .getGroupRights(sign.getGroup().getGroupUuid())
                    .hasRight(GroupRights.Right.CAN_CREATE_GROUP_SIGNS)) {

                this.addGuiElement(
                        new RemovePlayerGroupCreateRights(playerToEdit, sign)
                );

            } else {

                this.addGuiElement(
                        new AddPlayerGroupCreateRights(playerToEdit, sign)
                );

            }
        }

        return guiElements;
    }

}
