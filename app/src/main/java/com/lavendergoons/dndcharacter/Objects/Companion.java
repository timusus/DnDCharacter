package com.lavendergoons.dndcharacter.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Companion Object
 */
public class Companion implements Parcelable {

    private ArrayList<Attack> attacks = new ArrayList<>();
    private ArrayList<Feat> feats = new ArrayList<>();

    private String name, type;
    private int level, initiative, speed, hp, fort, reflex, will, grapple;

    // Saves Constants
    public static final int STR = 0;
    public static final int DEX = 1;
    public static final int CON = 2;
    public static final int INT = 3;
    public static final int WIS = 4;
    public static final int CHA = 5;
    public static final int SCORES = 6;

    // AC Constants
    public static final int AC_DEFAULT = 0;
    public static final int AC_DEX = 1;
    public static final int AC_SIZE = 2;
    public static final int AC_NATURAL = 3;
    public static final int AC_MISC = 4;
    public static final int AC_TOTAL = 5;
    public static final int AC = 6;

    private int[] score = new int[SCORES];
    private int[] mod = new int[SCORES];
    private int[] ac = new int[AC];

    public Companion(String name) {
        this.name = name;
    }

    public Companion(Parcel parcel) {
        this.name = parcel.readString();
        this.type = parcel.readString();

        this.level = parcel.readInt();
        this.initiative = parcel.readInt();
        this.speed = parcel.readInt();
        this.hp = parcel.readInt();
        this.fort = parcel.readInt();
        this.reflex = parcel.readInt();
        this.will = parcel.readInt();
        this.grapple = parcel.readInt();

        this.score = parcel.createIntArray();
        this.mod = parcel.createIntArray();
        this.ac = parcel.createIntArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);

        parcel.writeInt(level);
        parcel.writeInt(initiative);
        parcel.writeInt(speed);
        parcel.writeInt(hp);
        parcel.writeInt(fort);
        parcel.writeInt(reflex);
        parcel.writeInt(will);
        parcel.writeInt(grapple);

        parcel.writeIntArray(score);
        parcel.writeIntArray(mod);
        parcel.writeIntArray(ac);
    }

    public static final Parcelable.Creator<Companion> CREATOR = new Parcelable.Creator<Companion>() {
        @Override
        public Companion createFromParcel(Parcel parcel) {
            return new Companion(parcel);
        }

        @Override
        public Companion[] newArray(int i) {
            return new Companion[i];
        }
    };

    //**********************************************************
    // Lists
    //**********************************************************
    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        this.attacks = attacks;
    }

    public ArrayList<Feat> getFeats() {
        return feats;
    }

    public void setFeats(ArrayList<Feat> feats) {
        this.feats = feats;
    }

    //**********************************************************
    // General
    //**********************************************************
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getFort() {
        return fort;
    }

    public void setFort(int fort) {
        this.fort = fort;
    }

    public int getReflex() {
        return reflex;
    }

    public void setReflex(int reflex) {
        this.reflex = reflex;
    }

    public int getWill() {
        return will;
    }

    public void setWill(int will) {
        this.will = will;
    }

    public int getGrapple() {
        return grapple;
    }

    public void setGrapple(int grapple) {
        this.grapple = grapple;
    }

    //**********************************************************
    // Score & Mod
    //**********************************************************
    public int[] getScoreArray() {
        return score;
    }

    public void setScoreArray(int[] score) {
        this.score = score;
    }

    public int[] getModArray() {
        return mod;
    }

    public void setModArray(int[] mod) {
        this.mod = mod;
    }

    //**********************************************************
    // AC
    //**********************************************************
    public int[] getACArray() {
        return ac;
    }

    public void setACArray(int[] ac) {
        this.ac = ac;
    }


    @Override
    public String toString() {
        return "Companion{" +
                "attacks=" + attacks +
                ", feats=" + feats +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                ", initiative=" + initiative +
                ", speed=" + speed +
                ", hp=" + hp +
                ", fort=" + fort +
                ", reflex=" + reflex +
                ", will=" + will +
                ", grapple=" + grapple +
                ", score=" + Arrays.toString(score) +
                ", mod=" + Arrays.toString(mod) +
                ", ac=" + Arrays.toString(ac) +
                '}';
    }
}
