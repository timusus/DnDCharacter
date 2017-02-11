package com.lavendergoons.dndcharacter.Objects;

/**
 * Spell Object
 */

public class Spell {
    private String name;
    private String type;
    private String component;
    private String castTime;
    private String time;
    private String range;
    private String area;
    private String duration;
    private String savingThrow;
    private boolean spellRes;
    private String notes;

    public Spell(String name, String type, String component, String castTime, String time,
                 String range, String area, String duration, String savingThrow, boolean spellRes, String notes) {
        this.name = name;
        this.type = type;
        this.component = component;
        this.castTime = castTime;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
