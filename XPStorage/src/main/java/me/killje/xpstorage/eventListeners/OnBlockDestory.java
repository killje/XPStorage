package me.killje.xpstorage.eventListeners;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.XpSignFacingBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public abstract class OnBlockDestory implements Listener {

    protected boolean isDestroyable(List<Block> blockBeingDestroyed, Player playerDestroying) {

        for (Block block : blockBeingDestroyed) {
            if (!isDestroyable(block, playerDestroying)) {
                return false;
            }
        }

        return true;
    }

    protected boolean isDestroyable(Block blockBeingDestroyed, Player playerDestroying) {
        if (!blockBeingDestroyed.hasMetadata("XP_STORAGE_XPSIGN")) {

            if (!blockBeingDestroyed.hasMetadata("XP_STORAGE_XPSIGNFACEBLOCK")) {
                return true;
            }
            if (playerDestroying == null) {
                return false;
            }

            ArrayList<XpSignFacingBlock> xpSign = (ArrayList<XpSignFacingBlock>) blockBeingDestroyed.getMetadata("XP_STORAGE_XPSIGNFACEBLOCK").get(0).value();

            for (XpSignFacingBlock xpSignFacingBlock : xpSign) {
                if (!xpSignFacingBlock.getSign().canDestroySign(playerDestroying)) {
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

        AbstractXpSign xpSign = (AbstractXpSign) blockBeingDestroyed.getMetadata("XP_STORAGE_XPSIGN").get(0).value();
        return xpSign.destroySign(playerDestroying);
    }
}
