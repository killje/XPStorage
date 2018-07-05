package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.killje.xpstorage.util.PluginUtils;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author Patrick Beuks (killje)
 */
public class XpSignFacingBlock {
    
    private final static HashMap<Integer, HashMap<Integer, HashMap<Integer, ArrayList<XpSignFacingBlock>>>> FACING_BLOCK_LIST = new HashMap<>();

    private final Block facingBlock;
    private final AbstractXpSign sign;
    
    public XpSignFacingBlock(Block facingBlock, AbstractXpSign sign) {
        this.facingBlock = facingBlock;
        this.sign = sign;
        
        addFacingBlock(facingBlock, this);
    }

    public Block getFacingBlock() {
        return facingBlock;
    }

    public AbstractXpSign getSign() {
        return sign;
    }
    
    public static void removeFacingBlock(XpSignFacingBlock signFacingBlock) {
        Block facingBlock = signFacingBlock.getFacingBlock();
        
        int x = facingBlock.getX();
        int y = facingBlock.getY();
        int z = facingBlock.getZ();
        
        FACING_BLOCK_LIST.get(x).get(y).get(z).remove(signFacingBlock);
    }
    
    private static void addFacingBlock(Block facingBlock, XpSignFacingBlock signFacingBlock) {
        int x = facingBlock.getX();
        int y = facingBlock.getY();
        int z = facingBlock.getZ();
        
        if (!FACING_BLOCK_LIST.containsKey(x)) {
            FACING_BLOCK_LIST.put(x, new HashMap<>());
        }
        
        if (!FACING_BLOCK_LIST.get(x).containsKey(y)) {
            FACING_BLOCK_LIST.get(x).put(y, new HashMap<>());
        }
        
        if (!FACING_BLOCK_LIST.get(x).get(y).containsKey(z)) {
            ArrayList storageList = new ArrayList<>();
            FACING_BLOCK_LIST.get(x).get(y).put(z, storageList);
            facingBlock.setMetadata("XP_STORAGE_XPSIGNFACEBLOCK", new FixedMetadataValue(PluginUtils.getPlugin(), storageList));
        }
        
        FACING_BLOCK_LIST.get(x).get(y).get(z).add(signFacingBlock);
        
    }
    
    public static List<XpSignFacingBlock> getXpSignFacingBlock(Block facingBlock){
        
        int x = facingBlock.getX();
        int y = facingBlock.getY();
        int z = facingBlock.getZ();
        
        if (!FACING_BLOCK_LIST.containsKey(x)) {
            return null;
        }
        
        if (!FACING_BLOCK_LIST.get(x).containsKey(y)) {
            return null;
        }
        
        if (!FACING_BLOCK_LIST.get(x).get(y).containsKey(z)) {
            return null;
        }
        
        return FACING_BLOCK_LIST.get(x).get(y).get(z);
        
    }
    
    
}
