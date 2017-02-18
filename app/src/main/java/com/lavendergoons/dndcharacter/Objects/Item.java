package com.lavendergoons.dndcharacter.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * General Item
 */
public class Item implements Parcelable{
    private String name;
    private int weight;
    private int quantity;

    //TODO Make weight a double for decimal values
    public Item(String name, int weight, int quantity) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
    }

    public Item(Parcel parcel) {
        this.name = parcel.readString();
        this.weight = parcel.readInt();
        this.quantity = parcel.readInt();
    }

    public Item() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(weight);
        parcel.writeInt(quantity);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        @Override
        public Item[] newArray(int i) {
            return new Item[i];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
