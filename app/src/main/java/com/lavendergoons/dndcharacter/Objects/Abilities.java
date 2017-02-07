package com.lavendergoons.dndcharacter.Objects;

import com.lavendergoons.dndcharacter.Utils.Constants;

/**
 * Object to Scores, Mod, Saves, AC, HP, etc.
 */

public class Abilities {

    public static final int STR = 0;
    public static final int DEX = 1;
    public static final int CON = 2;
    public static final int INT = 3;
    public static final int WIS = 4;
    public static final int CHA = 5;
    public static final int SCORES = 6;

    public static final int BASE = 0;
    public static final int ABILITY = 1;
    public static final int MAGIC = 2;
    public static final int SAVE_MISC = 3;
    public static final int TEMP = 4;
    public static final int SAVE_TOTAL = 5;
    public static final int SAVES = 6;

    public static final int DEFAULT = 0;
    public static final int ARMOR = 1;
    public static final int SHEILD = 2;
    public static final int AC_DEX = 3;
    public static final int SIZE = 4;
    public static final int NATURAL = 5;
    public static final int DEFLECTION = 6;
    public static final int AC_MISC = 7;
    public static final int AC_TOTAL = 8;
    public static final int AC = 8;

    private long[] score = new long[SCORES];
    private long[] mod = new long[SCORES];
    private long[] scoreTemp = new long[SCORES];
    private long[] modTemp = new long[SCORES];

    private long[] fort = new long[SAVES];
    private long[] reflex = new long[SAVES];
    private long[] will = new long[SAVES];

    private long[] ac = new long[AC];

    public Abilities() {
        ac[DEFAULT] = Constants.AC_DEFAULT;
    }

    public void setScore(long value, int which) {
        score[which] = value;
    }

    public long getScore(int which) {
        return score[which];
    }

    public void setScoreTemp(long value, int which) {
        scoreTemp[which] = value;
    }

    public long getScoreTemp(int which) {
        return scoreTemp[which];
    }

    public void setMod(long value, int which) {
        mod[which] = value;
    }

    public long getMod(int which) {
        return mod[which];
    }

    public void setModTemp(long value, int which) {
        modTemp[which] = value;
    }

    public long getModTemp(int which) {
        return modTemp[which];
    }

    public void setFort(long value, int which) {
        fort[which] = value;
    }

    public long getFort(int which) {
        return fort[which];
    }

    public void setReflex(long value, int which) {
        reflex[which] = value;
    }

    public long getReflex(int which) {
        return reflex[which];
    }

    public void setWill(long value, int which) {
        will[which] = value;
    }

    public long getWill(int which) {
        return will[which];
    }

    public void setAC(long value, int which) {
        ac[which] = value;
    }

    public long getAC(int which) {
        return ac[which];
    }

}
