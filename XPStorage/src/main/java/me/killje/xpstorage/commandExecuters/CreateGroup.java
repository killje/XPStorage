package me.killje.xpstorage.commandExecuters;

import me.killje.xpstorage.group.Group;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Zolder
 */
public class CreateGroup extends AbstractCommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (label.toLowerCase()) {
            case "createxpgroup":
                if (args.length != 1) {
                    return false;
                }
                if (!(sender instanceof Player)) {
			sender.sendMessage("This command can only be run by a player.");
                    return false;
                }
                
                new Group(((Player) sender).getUniqueId(), args[0]);
                return true;
        }
        return true;
    }
    
}
