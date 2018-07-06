package me.killje.xpstorage.util;

import org.bukkit.entity.Player;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ExperienceManager {

    private class xpPart {

        private final int level;
        private final int xp;

        public xpPart(int level, int xp) {
            this.level = level;
            this.xp = xp;
        }

        public xpPart(int xp) {
            this.level = getMaxLevel(xp);
            this.xp = xp - LOOKUP_TOTAL[this.level];
        }

        public xpPart(int level, double fraction) {
            this.level = level;
            this.xp = (int) Math.round(fraction * LOOKUP_LEVEL[level]);
        }

        public xpPart(Player player) {
            this.level = player.getLevel();
            this.xp = (int) Math.round(player.getExpToLevel() * player.getExp());
        }

        public int getLevel() {
            return level;
        }

        public int getXp() {
            return xp;
        }

        public int getTotalXp() {
            return LOOKUP_TOTAL[level] + xp;
        }

    }

    // 100000 Is about 200 KB
    private final static int MAX_XP_LEVEL = 100000;
    /**
     * Lookup table index is <u><b>FROM</b></u> which level it calculates so
     * From index -> index + 1
     */
    private final static int[] LOOKUP_TOTAL = new int[MAX_XP_LEVEL];
    private final static int[] LOOKUP_LEVEL = new int[MAX_XP_LEVEL];

    static {
        LOOKUP_TOTAL[0] = 7;
        LOOKUP_LEVEL[0] = 7;
        for (int i = 1; i < MAX_XP_LEVEL; i++) {
            if (i <= 15) {
                LOOKUP_TOTAL[i] = 2 * i + 7 + LOOKUP_TOTAL[i - 1];
                LOOKUP_LEVEL[i] = 2 * i + 7;
            } else if (i <= 30) {
                LOOKUP_TOTAL[i] = 5 * i - 38 + LOOKUP_TOTAL[i - 1];
                LOOKUP_LEVEL[i] = 5 * i - 38;
            } else {
                LOOKUP_TOTAL[i] = 9 * i - 158 + LOOKUP_TOTAL[i - 1];
                LOOKUP_LEVEL[i] = 9 * i - 158;
            }
            if (LOOKUP_TOTAL[i] < 0) {
                System.out.println("OVERFLOW!");
            }
        }
    }

    private final Player player;

    public ExperienceManager(Player player) {
        this.player = player;
    }

    public void changeExperience(int experience) {
        setExperience(experience + new xpPart(player).getTotalXp());
    }

    public void setExperience(int experience) {
        int level = getMaxLevel(experience);
        long fraction = (experience - LOOKUP_TOTAL[level]) / LOOKUP_LEVEL[level];
        player.setLevel(level);
        player.setExp(fraction);
    }

    public int getTotalExperience() {
        return new xpPart(player).getTotalXp();
    }

    public int getExperienceNeedeForNextLevel() {
        return player.getExpToLevel() - (int) Math.round(player.getExpToLevel() * player.getExp());
    }
    
    public int getExperienceForLevel(int level) {
        if (level <= 0) {
            return 0;
        }
        return LOOKUP_LEVEL[level - 1];
    }

    public int getCurrentExperience() {
        return (int) Math.round(player.getExpToLevel() * player.getExp());
    }

    public int getLevel() {
        return player.getLevel();
    }

    public int getMaxLevel(int xp) {
        int lookupLevel = 0;
        // Loop stops at the current xp level since lookup table counts towards
        // the level.
        while (LOOKUP_TOTAL[lookupLevel] < xp) {
            lookupLevel++;
        }
        return lookupLevel;
    }

}
