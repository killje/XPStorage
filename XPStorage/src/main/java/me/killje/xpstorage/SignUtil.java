package me.killje.xpstorage;

import me.desht.dhutils.ExperienceManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje)
 */
public class SignUtil {

    private int xpInStorage;
    private final int xpPerTime;
    private final Sign sign;
    private final ExperienceManager expMan;
    private final Player player;

    public SignUtil(Sign sign, Player player) {
        this.player = player;
        expMan = new ExperienceManager(player);
        this.sign = sign;
        xpInStorage = Integer.parseInt(sign.getLine(1));
        String amountLine = sign.getLine(2);
        int startline = amountLine.indexOf("[");
        int endline = amountLine.indexOf("]");
        xpPerTime = Integer.parseInt(amountLine.substring(startline + 1, endline));
    }

    public void nextItem() {
        sign.setLine(2, nItem(xpPerTime));
    }

    public void prevItem() {
        sign.setLine(2, pItem(xpPerTime));
    }

    private String nItem(int currentSetting) {
        switch (currentSetting) {
            case 5:
                return "5 [20] 100 500";
            case 20:
                return "5 20 [100] 500";
            case 100:
                return "5 20 100 [500]";
            case 500:
                return "[5] 20 100 500";
            default:
                return "[5] 20 100 500";
        }
    }

    private String pItem(int currentSetting) {
        switch (currentSetting) {
            case 5:
                return "5 20 100 [500]";
            case 20:
                return "[5] 20 100 500";
            case 100:
                return "5 [20] 100 500";
            case 500:
                return "5 20 [100] 500";
            default:
                return "[5] 20 100 500";
        }
    }

    public void increaseXp() {
        if (expMan.getCurrentExp() < xpPerTime) {
            xpInStorage += expMan.getCurrentExp();
            expMan.setExp(0);
        } else if (xpInStorage + xpPerTime > 210000000) {
            player.sendMessage(ChatColor.DARK_RED + "Maximum of storage reached");
            return;
        } else {
            xpInStorage += xpPerTime;
            expMan.changeExp(0 - xpPerTime);
        }
        sign.setLine(1, xpInStorage + "");
    }

    public void decreaseXp() {
        if (xpInStorage < xpPerTime) {
            expMan.changeExp(xpInStorage);
            xpInStorage = 0;
        } else {
            expMan.changeExp(xpPerTime);
            xpInStorage -= xpPerTime;
        }
        sign.setLine(1, xpInStorage + "");
    }
}
