package com.lavendergoons.dndcharacter.Objects;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Base Character Object
 * To Hold all sub character objects
 */
public class Character implements Parcelable {
    public String name;
    public int level;

    public Character() {

    }

    public Character(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public Character(Parcel parcel) {
        this.name = parcel.readString();
        this.level = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(level);
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel parcel) {
            return new Character(parcel);
        }

        @Override
        public Character[] newArray(int i) {
            return new Character[i];
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
}
