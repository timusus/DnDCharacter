package com.lavendergoons.dndcharacter.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Armor object
 */
public class Armor extends Item implements Parcelable {
    private String type;
    private int acBonus;
    private int maxDex;
    private int checkPenalty;
    private int spellFailure;
    private int speed;
    private String properties;

    public Armor(String name, String type, int acBonus, int maxDex, int checkPenalty, int spellFailure, int weight, int speed, String properties, int quantity) {
        super(name, weight, quantity);
        this.type = type;
        this.acBonus = acBonus;
        this.maxDex = maxDex;
        this.checkPenalty = checkPenalty;
        this.spellFailure = spellFailure;
        this.speed = speed;
        this.properties = properties;
    }

    public Armor(Parcel parcel) {
        super(parcel);
        this.type = parcel.readString();
        this.acBonus = parcel.readInt();
        this.maxDex = parcel.readInt();
        this.checkPenalty = parcel.readInt();
        this.spellFailure = parcel.readInt();
        this.speed = parcel.readInt();
        this.properties = parcel.readString();
    }

    public Armor(String name, int weight, int quantity) {
        super(name, weight, quantity);
    }

    public Armor() {
        super();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(type);
        parcel.writeInt(acBonus);
        parcel.writeInt(maxDex);
        parcel.writeInt(checkPenalty);
        parcel.writeInt(spellFailure);
        parcel.writeInt(speed);
        parcel.writeString(properties);
    }

    public static final Parcelable.Creator<Armor> CREATOR = new Parcelable.Creator<Armor>() {
        @Override
        public Armor createFromParcel(Parcel parcel) {
            return new Armor(parcel);
        }

        @Override
        public Armor[] newArray(int i) {
            return new Armor[i];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAcBonus() {
        return acBonus;
    }

    public void setAcBonus(int acBonus) {
        this.acBonus = acBonus;
    }

    public int getMaxDex() {
        return maxDex;
    }

    public void setMaxDex(int maxDex) {
        this.maxDex = maxDex;
    }

    public int getCheckPenalty() {
        return checkPenalty;
    }

    public void setCheckPenalty(int checkPenalty) {
        this.checkPenalty = checkPenalty;
    }

    public int getSpellFailure() {
        return spellFailure;
    }

    public void setSpellFailure(int spellFailure) {
        this.spellFailure = spellFailure;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
