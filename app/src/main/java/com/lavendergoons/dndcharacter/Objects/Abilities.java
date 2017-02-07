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

    private int[] score = new int[SCORES];
    private int[] mod = new int[SCORES];
    private int[] scoreTemp = new int[SCORES];
    private int[] modTemp = new int[SCORES];

    private int[] fort = new int[SAVES];
    private int[] reflex = new int[SAVES];
    private int[] will = new int[SAVES];

    private int[] ac = new int[AC];

    public Abilities() {
        ac[DEFAULT] = Constants.AC_DEFAULT;
    }

    public void setScore(int value, int which) {
        score[which] = value;
    }

    public int getScore(int which) {
        return score[which];
    }

    public int[] getScoreArray() {
        return score;
    }

    public void setScoreTemp(int value, int which) {
        scoreTemp[which] = value;
    }

    public int getScoreTemp(int which) {
        return scoreTemp[which];
    }

    public int[] getScoreTempArray(){
        return scoreTemp;
    }

    public void setMod(int value, int which) {
        mod[which] = value;
    }

    public int getMod(int which) {
        return mod[which];
    }

    public int[] getModArray() {
        return mod;
    }

    public void setModTemp(int value, int which) {
        modTemp[which] = value;
    }

    public int getModTemp(int which) {
        return modTemp[which];
    }

    public int[] getModTempArray() {
        return modTemp;
    }

    public void setFort(int value, int which) {
        fort[which] = value;
    }

    public int getFort(int which) {
        return fort[which];
    }

    public void setReflex(int value, int which) {
        reflex[which] = value;
    }

    public int getReflex(int which) {
        return reflex[which];
    }

    public void setWill(int value, int which) {
        will[which] = value;
    }

    public int getWill(int which) {
        return will[which];
    }

    public void setAC(int value, int which) {
        ac[which] = value;
    }

    public int getAC(int which) {
        return ac[which];
    }

}
