package me.killje.xpstorage.xpsign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.killje.xpstorage.XPStorage;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * This class is to protects blocks from being destroyed if a xp sign is
 * attached
 *
 * @author Patrick Beuks (killje)
 */
public class XpSignFacingBlock {

    /**
     * List of all protected blocks order by x, y and z coordinate
     */
    private final static Map<Integer, Map<Integer, Map<Integer, //
            List<XpSignFacingBlock>>>> FACING_BLOCK_LIST = new HashMap<>();

    /**
     * Add a protection for a block
     *
     * @param facingBlock     The block to protect
     * @param signFacingBlock The protection
     */
    private static void addFacingBlock(Block facingBlock,
            XpSignFacingBlock signFacingBlock) {
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

            // Remove old definition of metadata
            if (facingBlock.hasMetadata("XP_STORAGE_XPSIGNFACEBLOCK")) {
                facingBlock.removeMetadata("XP_STORAGE_XPSIGNFACEBLOCK",
                        XPStorage.getPlugin());
            }

            facingBlock.setMetadata(
                    "XP_STORAGE_XPSIGNFACEBLOCK", new FixedMetadataValue(
                            XPStorage.getPluginUtil().getPlugin(), storageList
                    )
            );
        }

        FACING_BLOCK_LIST.get(x).get(y).get(z).add(signFacingBlock);

    }

    public static List<XpSignFacingBlock> getXpSignFacingBlock(Block facingBlock) {

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

    /**
     * Removes a facing block from the list of protections
     *
     * @param signFacingBlock The facing block to remove
     */
    public static void removeFacingBlock(XpSignFacingBlock signFacingBlock) {
        Block facingBlock = signFacingBlock.getFacingBlock();

        int x = facingBlock.getX();
        int y = facingBlock.getY();
        int z = facingBlock.getZ();

        FACING_BLOCK_LIST.get(x).get(y).get(z).remove(signFacingBlock);
    }

    /**
     * The block to protect
     */
    private final Block facingBlock;
    /**
     * The sign that is attached
     */
    private final AbstractXpSign sign;

    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Creates a new protection for the block the sign is attached to
     *
     * @param facingBlock The block that the sign is attached on
     * @param sign        The sign that is attached to the block
     */
    public XpSignFacingBlock(Block facingBlock, AbstractXpSign sign) {
        this.facingBlock = facingBlock;
        this.sign = sign;

        addFacingBlock(facingBlock, this);
    }

    /**
     * Gets the block that is protected
     *
     * @return protected block
     */
    public Block getFacingBlock() {
        return facingBlock;
    }

    /**
     * Gets the sign that is attached on the block
     *
     * @return The sign
     */
    public AbstractXpSign getSign() {
        return sign;
    }

}
