package com.lavendergoons.dndcharacter.Objects;

/**
 * Created by rtas on 2017-01-13.
 */
public class Item {
    private String name;
    private long weight;
    private long quantity;

    public Item(String name, long weight, long quantity) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
