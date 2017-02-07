package com.lavendergoons.dndcharacter.Objects;

/**
 * Armor object
 */
public class Armor extends Item {
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
