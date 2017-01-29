package com.lavendergoons.dndcharacter.Objects;

/**
 * Created by rtas on 2017-01-13.
 */
public class Attack {
    private String attack;
    private String attackBonus;
    private String damage;
    private String critical;
    private String range;
    private String type;
    private String ammo;

    public Attack(String attack, String attackBonus, String damage, String critical, String range, String type, String ammo) {
        this.attack = attack;
        this.attackBonus = attackBonus;
        this.damage = damage;
        this.critical = critical;
        this.range = range;
        this.type = type;
        this.ammo = ammo;
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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmmo() {
        return ammo;
    }

    public void setAmmo(String ammo) {
        this.ammo = ammo;
    }
}
