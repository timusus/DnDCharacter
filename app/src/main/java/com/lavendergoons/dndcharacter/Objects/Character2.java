package com.lavendergoons.dndcharacter.Objects;

import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;

/**
 * Contains all info about character
 */

public class Character2 {

    private Abilities abilities;
    private ArrayList<Armor> armorList = new ArrayList<>();
    private ArrayList<Attack> attackList = new ArrayList<>();
    private ArrayList<String> attributesList = new ArrayList<>(Constants.ATTRIBUTES.length);
    private ArrayList<Item> itemList = new ArrayList<>();
    private ArrayList<Note> notesList = new ArrayList<>();
    private ArrayList<Skill> skillsList = new ArrayList<>();
    private ArrayList<Spell> spellList = new ArrayList<>();

    public Abilities getAbilities() {
        return abilities;
    }

    public void setAbilities(Abilities abilities) {
        this.abilities = abilities;
    }

    public ArrayList<Armor> getArmorList() {
        return armorList;
    }

    public void setArmorList(ArrayList<Armor> armorList) {
        this.armorList = armorList;
    }

    public ArrayList<Attack> getAttackList() {
        return attackList;
    }

    public void setAttackList(ArrayList<Attack> attackList) {
        this.attackList = attackList;
    }

    public ArrayList<String> getAttributesList() {
        return attributesList;
    }

    public void setAttributesList(ArrayList<String> attributesList) {
        this.attributesList = attributesList;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public ArrayList<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(ArrayList<Note> notesList) {
        this.notesList = notesList;
    }

    public ArrayList<Skill> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(ArrayList<Skill> skillsList) {
        this.skillsList = skillsList;
    }

    public ArrayList<Spell> getSpellList() {
        return spellList;
    }

    public void setSpellList(ArrayList<Spell> spellList) {
        this.spellList = spellList;
    }
}
