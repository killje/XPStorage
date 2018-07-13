package me.killje.xpstorage.eventListeners;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.XpSignFacingBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Abstract class for listeners that deal with blocks that are destroyed in the
 * game
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class OnBlockDestory implements Listener {

    /**
     * Checks if there is a block in the list that is protected
     *
     * @param blocksBeingDestroyed The list of blocks that are destroyed
     * @param playerDestroying The player destroying the block if present
     * @return False if a block is protected by this plugin
     */
    protected boolean isDestroyable(
            List<Block> blocksBeingDestroyed, Player playerDestroying) {

        for (Block block : blocksBeingDestroyed) {
            if (!isDestroyable(block, playerDestroying)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the block can just be destroyed without a problem
     *
     * @param blockBeingDestroyed The block we want to check if it gives a
     * problem or not
     * @param playerDestroying The player destroying the block, if present
     * @return False if the block is protected by this plugin, true otherwise
     */
    protected boolean isDestroyable(
            Block blockBeingDestroyed, Player playerDestroying) {

        if (!blockBeingDestroyed.hasMetadata("XP_STORAGE_XPSIGN")) {

            if (!blockBeingDestroyed
                    .hasMetadata("XP_STORAGE_XPSIGNFACEBLOCK")) {
                return true;
            }
            if (playerDestroying == null) {
                return false;
            }

            ArrayList<XpSignFacingBlock> xpSign
                    = (ArrayList<XpSignFacingBlock>) blockBeingDestroyed
                            .getMetadata("XP_STORAGE_XPSIGNFACEBLOCK")
                            .get(0).value();

            for (XpSignFacingBlock xpSignFacingBlock : xpSign) {
                if (!xpSignFacingBlock.getSign()
                        .canDestroySign(playerDestroying)) {

                    return false;
                }
            }

            ArrayList<AbstractXpSign> signsToRemove = new ArrayList<>();
            for (XpSignFacingBlock xpSignFacingBlock : xpSign) {
                signsToRemove.add(xpSignFacingBlock.getSign());
            }

            for (AbstractXpSign abstractXpSign : signsToRemove) {
                abstractXpSign.destroySign(playerDestroying);
            }

            return true;
        }

        if (playerDestroying == null) {
            return false;
        }

        AbstractXpSign xpSign = (AbstractXpSign) blockBeingDestroyed
                .getMetadata("XP_STORAGE_XPSIGN").get(0).value();

        return xpSign.destroySign(playerDestroying);
    }
}
