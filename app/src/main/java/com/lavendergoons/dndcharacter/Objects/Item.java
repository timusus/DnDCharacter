package com.lavendergoons.dndcharacter.Objects;

/**
 * General Item
 */
public class Item {
    private String name;
    private int weight;
    private int quantity;

    //TODO Make weight a double for decimal values
    public Item(String name, int weight, int quantity) {
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
