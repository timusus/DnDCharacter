package com.lavendergoons.dndcharacter.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Switch;

/**
 * Spell Object
 */

public class Spell implements Parcelable{
    private String name, type, component, castTime, range, area, duration, savingThrow, notes;
    private boolean spellRes;
    private int level;

    public Spell() {

    }

    public Spell(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public Spell(String name, int level, String type, String castTime, String range, String duration) {
        this.name = name;
        this.level = level;
        this.type = type;
        this.range = range;
        this.castTime = castTime;
        this.duration = duration;
    }

    public Spell(String name, int level, String type, String component, String castTime,
                 String range, String area, String duration, String savingThrow, boolean spellRes, String notes) {
        this.name = name;
        this.level = level;
        this.type = type;
        this.component = component;
        this.castTime = castTime;
        this.range = range;
        this.area = area;
        this.duration = duration;
        this.savingThrow = savingThrow;
        this.spellRes = spellRes;
        this.notes = notes;
    }

    public Spell(Parcel parcel) {
        this.name = parcel.readString();
        this.level = parcel.readInt();
        this.type = parcel.readString();
        this.component = parcel.readString();
        this.castTime = parcel.readString();
        this.range = parcel.readString();
        this.area = parcel.readString();
        this.duration = parcel.readString();
        this.savingThrow = parcel.readString();
        this.spellRes = parcel.readByte() != 0;
        this.notes = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(component);
        parcel.writeString(castTime);
        parcel.writeString(range);
        parcel.writeString(area);
        parcel.writeString(duration);
        parcel.writeString(savingThrow);
        parcel.writeString(notes);
        parcel.writeInt(level);
        parcel.writeByte((byte)(spellRes?1:0));
    }

    public static final Parcelable.Creator<Spell> CREATOR = new Parcelable.Creator<Spell>() {
        @Override
        public Spell createFromParcel(Parcel parcel) {
            return new Spell(parcel);
        }

        @Override
        public Spell[] newArray(int i) {
            return new Spell[i];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getCastTime() {
        return castTime;
    }

    public void setCastTime(String castTime) {
        this.castTime = castTime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSavingThrow() {
        return savingThrow;
    }

    public void setSavingThrow(String savingThrow) {
        this.savingThrow = savingThrow;
    }

    public boolean isSpellRes() {
        return spellRes;
    }

    public void setSpellRes(boolean spellRes) {
        this.spellRes = spellRes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
