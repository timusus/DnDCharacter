package com.lavendergoons.dndcharacter.Objects;

/**
 * Created by rtas on 2017-01-13.
 */

/* Base Character Object
   To Hold all sub character objects
 */
public class Character {
    public String name;
    public int level;

    public Character() {

    }

    public Character(String name, int level) {
        this.name = name;
        this.level = level;
    }
}
