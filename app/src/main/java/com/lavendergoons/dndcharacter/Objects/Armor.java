package com.lavendergoons.dndcharacter.Objects;

/**
 * Created by rtas on 2017-01-13.
 */
public class Armor extends Item {
    private String type;
    private long acBonus;
    private long maxDex;
    private long checkPenalty;
    private long spellFailure;
    private long speed;
    private String properties;

    public Armor(String name, String type, long acBonus, long maxDex, long checkPenalty, long spellFailure, long weight, long speed, String properties, long quantity) {
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

    public long getAcBonus() {
        return acBonus;
    }

    public void setAcBonus(long acBonus) {
        this.acBonus = acBonus;
    }

    public long getMaxDex() {
        return maxDex;
    }

    public void setMaxDex(long maxDex) {
        this.maxDex = maxDex;
    }

    public long getCheckPenalty() {
        return checkPenalty;
    }

    public void setCheckPenalty(long checkPenalty) {
        this.checkPenalty = checkPenalty;
    }

    public long getSpellFailure() {
        return spellFailure;
    }

    public void setSpellFailure(long spellFailure) {
        this.spellFailure = spellFailure;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
