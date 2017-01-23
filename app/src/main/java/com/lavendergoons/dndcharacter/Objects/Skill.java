package com.lavendergoons.dndcharacter.Objects;

/**
 * Created by rtas on 2017-01-13.
 */
public class Skill {
    private String name;
    private boolean isTrained;
    private String modType;
    private int total;
    private int mod;
    private int rank;
    private int misc;

    public Skill(String name, String modType, boolean isTrained, int total, int mod, int rank, int misc) {
        this.name = name;
        this.modType = modType;
        this.isTrained = isTrained;
        this.total = total;
        this.mod = mod;
        this.rank = rank;
        this.misc = misc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModType() {
        return modType;
    }

    public void setModType(String modType) {
        this.modType = modType;
    }

    public boolean isTrained() {
        return isTrained;
    }

    public void setTrained(boolean trained) {
        isTrained = trained;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMod() {
        return mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMisc() {
        return misc;
    }

    public void setMisc(int misc) {
        this.misc = misc;
    }
}
