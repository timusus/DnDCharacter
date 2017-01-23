package com.lavendergoons.dndcharacter.Utils;

/**
 * Created by t00530282 on 1/14/2017.
 */
// ??
public final class Constants {

    final static int LONG_CLICK_VIBRATION = 100;

    //Fragments
    final static String FRAGMENT_SKILLS = "SKILLS";

    // Arrays
    final static String[] SKILLS = {
            "Appraise",
            "Balance",
            "Bluff",
            "Climb",
            "Concentration",
            "Craft",
            "Decipher Script",
            "Diplomacy",
            "Disable Device",
            "Disguise",
            "Escape Artist",
            "Forgery",
            "Gather Info",
            "Handle Animal",
            "Heal",
            "Hide",
            "Intimidate",
            "Jump",
            "Knowledge",
            "Listen",
            "Move Silently",
            "Open Lock",
            "Perform",
            "Profession",
            "Ride",
            "Search",
            "Sense Motive",
            "Sleight of Hand",
            "Spellcraft",
            "Spot",
            "Survival",
            "Swim",
            "Tumble",
            "Use Magic Device",
            "Use Rope"
    };

    // Enums
    public enum Skills {
        APPRAISE        ("Appraise", "INT"),
        BALANCE         ("Balance", "DEX"),
        BLUFF           ("Bluff", "CHA"),
        CLIMB           ("Climb", "STR"),
        CONCENTRATION   ("Concentration", "CON"),
        CRAFT1          ("Craft", "INT"),
        CRAFT2          ("Craft", "INT"),
        CRAFT3          ("Craft", "INT"),
        DECIPHER_SCRIPT ("Decipher Script", "INT"),
        DIPLOMACY       ("Diplomacy", "CHA"),
        DISABLE_DEVICE  ("Disable Device", "INT"),
        DISGUISE        ("Disguise", "CHA"),
        ESCAPE_ARTIST   ("Escape Artist", "DEX"),
        FORGERY         ("Forgery", "INT"),
        GATHER_INFO     ("Gather Info", "CHA"),
        HANDLE_ANIMAL   ("Handle Animal", "CHA"),
        HEAL            ("Heal", "WIS"),
        HIDE            ("Hide", "DEX"),
        INITIMIDATE     ("Intimidate", "CHA"),
        JUMP            ("Jump", "STR"),
        KNOWLEDGE1      ("Knowledge", "INT"),
        KNOWLEDGE2      ("Knowledge", "INT"),
        KNOWLEDGE3      ("Knowledge", "INT"),
        KNOWLEDGE4      ("Knowledge", "INT"),
        LISTEN          ("Listen", "WIS"),
        MOVE_SILENTLY   ("Move Silently", "DEX"),
        OPEN_LOCK       ("Open Lock", "DEX"),
        PERFORM1        ("Perform", "CHA"),
        PERFORM2        ("Perform", "CHA"),
        PERFORM3        ("Perform", "CHA"),
        PROFESSION      ("Profession", "WIS"),
        RIDE            ("Ride", "DEX"),
        SEARCH          ("Search", "INT"),
        SENSE_MOTIVE    ("Sense Motive", "WIS"),
        SLEIGHT_OF_HAND ("Sleight of Hand", "DEX"),
        SPELLCRAFT      ("Spellcraft", "INT"),
        SPOT            ("Spot", "WIS"),
        SURVIVAL        ("Survival", "WIS"),
        SWIM            ("Swim", "STR"),
        TUMBLE          ("Tumble", "DEX"),
        MAGIC_DEVICE    ("Use Magic Device", "CHA"),
        USE_ROPE        ("Use Rope", "DEX");

        private final String mName;
        private final String mMod;
        private static final int mSize = 42;

        Skills(String name, String mod) {
            this.mName = name;
            this.mMod = mod;
        }

        public String getName() { return mName;}
        public String getMod() { return mMod;}
        public int getSize() { return mSize;}
    }
}
