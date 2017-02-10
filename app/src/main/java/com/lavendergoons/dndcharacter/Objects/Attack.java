package com.lavendergoons.dndcharacter.Objects;

/**
 * Attack Object
 */
public class Attack {
    private String attack;
    private String attackBonus;
    private String damage;
    private String critical;
    private int range;
    private String type;
    private int ammo;
    private String notes;

    public Attack(String attack, String attackBonus, String damage, String critical, int range, String type, int ammo, String notes) {
        this.attack = attack;
        this.attackBonus = attackBonus;
        this.damage = damage;
        this.critical = critical;
        this.range = range;
        this.type = type;
        this.ammo = ammo;
        this.notes = notes;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(String attackBonus) {
        this.attackBonus = attackBonus;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
