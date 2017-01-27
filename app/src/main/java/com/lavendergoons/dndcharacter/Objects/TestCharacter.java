package com.lavendergoons.dndcharacter.Objects;

import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by rtas on 2017-01-22.
 */

public class TestCharacter {
    private ArrayList<Skill> skills = new ArrayList<>();
    private ArrayList<Attribute> attributes = new ArrayList<>();
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
            skills.add(new Skill(s.getName(), s.getMod(), random.nextBoolean(), total, mod, rank, misc));
        }

        for(String s : Constants.ATTRIBUTES) {
            attributes.add(new Attribute(s, s));
        }
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }
}
