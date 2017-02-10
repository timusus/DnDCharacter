package com.lavendergoons.dndcharacter.Objects;

import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Test Character with test data
 * Will be deleted
 */

public class TestCharacter {

    private ArrayList<Skill> skills = new ArrayList<>();
    private ArrayList<Attribute> attributes = new ArrayList<>();
    private ArrayList<Armor> armor = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Attack> attacks = new ArrayList<>();
    private Abilities abilities;

    private String name = "Silian Mord";
    private int level = 10;
    Random random = new Random(1);

    public TestCharacter() {
        init();
    }

    private void init() {
        for (Constants.Skills s : Constants.Skills.values()) {
            int mod = random.nextInt(5) + 1;
            int rank = random.nextInt(8);
            int misc = random.nextInt(1);
            int total = mod + rank + misc;
            skills.add(new Skill(s.getName(), s.getMod(), s.getDefault(), total, mod, rank, misc));
        }

        for(String s : Constants.ATTRIBUTES) {
            attributes.add(new Attribute(s, s));
        }
        // name, type, acBonus, maxDex, checkPenalty, spellFailure, weight, speed, properties, quantity
        armor.add(new Armor("Studded Leather", "Light", 3, 5, -1, 15, 20, 30, "This is my test string, the quick brown fox jumped over the lazy dog", 1));
        armor.add(new Armor("Leather", "Light", 2, 4, -1, 15, 10, 30, "Some good thick leather for your needs", 1));
        armor.add(new Armor("Shield", "Wooden", 1, 0, -1, 5, 5, 0, "A shield to use for stuff", 1));
        abilities = new Abilities();

        for (int i=0;i<abilities.getScoreArray().length;i++) {
            int value = random.nextInt(5)+10;
            abilities.setScore(value, i);
            abilities.setMod(Utils.modValue(value), i);
            abilities.setScoreTemp(0, i);
            abilities.setModTemp(0, i);
        }

        items.add(new Item("Bedroll", 2, 1));
        items.add(new Item("Flint/Steel", 4, 1));
        items.add(new Item("Health Potion", 1, 5));
        items.add(new Item("Lantern", 7, 1));

        //String attack, String attackBonus, String damage, String critical, int range, String type, int ammo
        attacks.add(new Attack("Longsword", "+4", "2d6", "19-20x2", 0, "S", 0, ""));
        attacks.add(new Attack("Dagger", "+4", "1d4", "19-20x2", 0, "S", 0, ""));
        attacks.add(new Attack("Longbow", "+2", "1d6", "18-20x2", 110, "P", 21, ""));
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public ArrayList<Armor> getArmor() {
        return armor;
    }

    public Abilities getAbilities() {
        return abilities;
    }

    public ArrayList<Item> getItems() {return items;}

    public ArrayList<Attack> getAttacks() {return attacks;}
}
