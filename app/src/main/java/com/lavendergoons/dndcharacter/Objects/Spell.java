package com.lavendergoons.dndcharacter.Objects;

/**
 * Spell Object
 */

public class Spell {
    private String name, type, component, castTime, range, area, duration, savingThrow, notes;
    private boolean spellRes;
    private int level;

    public Spell() {

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
