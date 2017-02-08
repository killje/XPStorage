package me.killje.xpstorage.xpsign;

import java.util.Map;
import java.util.UUID;
import me.killje.xpstorage.group.Group;
import me.killje.xpstorage.gui.editplayer.EditPlayerOptions;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

/**
 *
 * @author Zolder
 */
public class SharedSign extends AbstractSharedSign {
    
    public SharedSign(Sign sign, UUID player) {
        super(sign, new Group(player));
    }
    
    public SharedSign(Map<String, Object> sign) {
        super(sign);
    }
    
    @Override
    protected void changeToSign() {
        this.getSign().setLine(3, ChatColor.DARK_PURPLE + "Shared");
    }

    @Override
    public String signType() {
        return "Shared sign";
    }

    @Override
    public boolean destroySign() {
        getGroup().destoryGroup();
        return super.destroySign();
    }

    @Override
    public EditPlayerOptions getEditList(UUID playerToEdit, UUID playerEditing) {
        return new EditPlayerOptions(this, playerToEdit, playerEditing);
    }
    
}