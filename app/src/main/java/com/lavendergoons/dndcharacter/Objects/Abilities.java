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

    // Saves Constants
    public static final int SAVE_BASE = 0;
    public static final int SAVE_ABILITY = 1;
    public static final int SAVE_MAGIC = 2;
    public static final int SAVE_MISC = 3;
    public static final int SAVE_TEMP = 4;
    public static final int SAVE_TOTAL = 5;
    public static final int SAVES = 6;

    // AC Constants
    public static final int AC_DEFAULT = 0;
    public static final int AC_ARMOR = 1;
    public static final int AC_SHIELD = 2;
    public static final int AC_DEX = 3;
    public static final int AC_SIZE = 4;
    public static final int AC_NATURAL = 5;
    public static final int AC_DEFLECTION = 6;
    public static final int AC_MISC = 7;
    public static final int AC_TOTAL = 8;
    public static final int AC = 9;

    // Grapple Constants
    public static final int GRAPPLE_BASE = 0;
    public static final int GRAPPLE_STR = 1;
    public static final int GRAPPLE_SIZE = 2;
    public static final int GRAPPLE_MISC = 3;
    public static final int GRAPPLE_TOTAL = 4;
    public static final int GRAPPLE = 5;

    private int[] score = new int[SCORES];
    private int[] mod = new int[SCORES];
    private int[] scoreTemp = new int[SCORES];
    private int[] modTemp = new int[SCORES];

    private int[] fort = new int[SAVES];
    private int[] reflex = new int[SAVES];
    private int[] will = new int[SAVES];

    private int[] ac = new int[AC];

    private int[] grapple = new int[GRAPPLE];

    private int hp, nonLethal, baseAtk, spellRes, initiative, speed;

    public Abilities() {
        ac[AC_DEFAULT] = Constants.AC_DEFAULT;
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

    public void setScoreArray(int[] array) {
        this.score = array;
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

    public void setModArray(int[] array) {
        this.mod = array;
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

    public int[] getFortArray() { return fort;}

    public void setFortArray(int[] array) {
        fort = array;
    }

    public void setReflex(int value, int which) {
        reflex[which] = value;
    }

    public int getReflex(int which) {
        return reflex[which];
    }

    public int[] getReflexArray() { return reflex;}

    public void setReflexArray(int[] array) {
        reflex = array;
    }

    public void setWill(int value, int which) {
        will[which] = value;
    }

    public int getWill(int which) {
        return will[which];
    }

    public int[] getWillArray() {return will;}

    public void setWillArray(int[] array) {
        will = array;
    }

    public void setAC(int value, int which) {
        ac[which] = value;
    }

    public int getAC(int which) {
        return ac[which];
    }

    public int getAcTouch() {
        return ac[AC_DEFAULT] + ac[AC_DEX] + ac[AC_DEFLECTION];
    }

    public int getAcFlatFoot() {
        int out = 0;
        for (int i=0;i<ac.length;i++) {
            if (i != AC_DEX && i != AC_TOTAL) {
                out+=ac[i];
            }
        }
        return out;
    }

    public void setGrapple(int value, int which) {grapple[which] = value;}

    public int getGrapple(int which) {return grapple[which];}

    public void setGrappleArray(int[] array) {
        this.grapple = array;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getNonLethal() {
        return nonLethal;
    }

    public void setNonLethal(int nonLethal) {
        this.nonLethal = nonLethal;
    }

    public int getBaseAtk() {
        return baseAtk;
    }

    public void setBaseAtk(int baseAtk) {
        this.baseAtk = baseAtk;
    }

    public int getSpellRes() {
        return spellRes;
    }

    public void setSpellRes(int spellRes) {
        this.spellRes = spellRes;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
