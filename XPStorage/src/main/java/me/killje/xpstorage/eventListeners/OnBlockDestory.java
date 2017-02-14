package me.killje.xpstorage.eventListeners;

import java.util.ArrayList;
import java.util.List;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import me.killje.xpstorage.xpsign.XpSignFacingBlock;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

/**
 *
 * @author Zolder
 */
public abstract class OnBlockDestory implements Listener {
    
    
    protected boolean isDestroyable(List<Block> blockBeingDestroyed) {
        
        for (Block block : blockBeingDestroyed) {
            if (!isDestroyable(block)) {
                return false;
            }
        }
        
        return true;
    }
    
    protected boolean isDestroyable(Block blockBeingDestroyed) {
        if (!blockBeingDestroyed.hasMetadata("XP_STORAGE_XPSIGN")) {
            if (!blockBeingDestroyed.hasMetadata("XP_STORAGE_XPSIGNFACEBLOCK")) {
                return true;
            }
            
            System.out.println("block destroy");
            ArrayList<XpSignFacingBlock> xpSign = (ArrayList<XpSignFacingBlock>) blockBeingDestroyed.getMetadata("XP_STORAGE_XPSIGNFACEBLOCK").get(0).value();
            for (XpSignFacingBlock xpSignFacingBlock : xpSign) {
                System.out.println(xpSignFacingBlock.getSign().getSign().getBlock());
            }
            for (XpSignFacingBlock xpSignFacingBlock : xpSign) {
                if (!xpSignFacingBlock.getSign().canDestroySign()) {
                    return false;
                }
            }
            ArrayList<AbstractXpSign> signsToRemove = new ArrayList<>();
            for (XpSignFacingBlock xpSignFacingBlock : xpSign) {
                signsToRemove.add(xpSignFacingBlock.getSign());
            }
            for (AbstractXpSign abstractXpSign : signsToRemove) {
                abstractXpSign.destroySign();
            }
            return true;
        }
        System.out.println("sign destroy");
        AbstractXpSign xpSign = (AbstractXpSign) blockBeingDestroyed.getMetadata("XP_STORAGE_XPSIGN").get(0).value();
        return xpSign.destroySign();
    }
}
