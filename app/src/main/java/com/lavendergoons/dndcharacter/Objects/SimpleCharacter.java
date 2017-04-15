package com.lavendergoons.dndcharacter.Objects;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Base SimpleCharacter Object
 * To Hold all sub character objects
 */
public class SimpleCharacter implements Parcelable {
    public String name;
    public int level;

    public SimpleCharacter() {

    }

    public SimpleCharacter(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public SimpleCharacter(Parcel parcel) {
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

    public static final Parcelable.Creator<SimpleCharacter> CREATOR = new Parcelable.Creator<SimpleCharacter>() {
        @Override
        public SimpleCharacter createFromParcel(Parcel parcel) {
            return new SimpleCharacter(parcel);
        }

        @Override
        public SimpleCharacter[] newArray(int i) {
            return new SimpleCharacter[i];
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

    @Override
    public String toString() {
        return "SimpleCharacter{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
