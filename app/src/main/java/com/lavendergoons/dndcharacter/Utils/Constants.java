package com.lavendergoons.dndcharacter.Utils;

/**
 * Constants
 */
// ??
public final class Constants {

    public final static int LONG_CLICK_VIBRATION = 100;
    public final static int ATTRIBUTES_GRID_SPAN = 2;
    public final static int AC_DEFAULT = 10;

    public final static int SPINNER_ARMOR = 0;
    public final static int SPINNER_ITEM = 1;


    // Arrays
    public final static String[] SKILLS = {
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

    public final static String[] ATTRIBUTES = {
            "Name",
            "Class",
            "Level",
            "XP",
            "Race",
            "Alignment",
            "Deity",
            "Size",
            "Age",
            "Gender",
            "Height",
            "Weight",
            "Eyes",
            "Hair",
            "Skin"
    };

    // Enums
    public enum Skills {
        APPRAISE        ("Appraise", "INT", true),
        BALANCE         ("Balance", "DEX", true),
        BLUFF           ("Bluff", "CHA", true),
        CLIMB           ("Climb", "STR", true),
        CONCENTRATION   ("Concentration", "CON", true),
        CRAFT1          ("Craft", "INT", true),
        CRAFT2          ("Craft", "INT", true),
        CRAFT3          ("Craft", "INT", true),
        DECIPHER_SCRIPT ("Decipher Script", "INT", false),
        DIPLOMACY       ("Diplomacy", "CHA", true),
        DISABLE_DEVICE  ("Disable Device", "INT", false),
        DISGUISE        ("Disguise", "CHA", true),
        ESCAPE_ARTIST   ("Escape Artist", "DEX", true),
        FORGERY         ("Forgery", "INT", true),
        GATHER_INFO     ("Gather Info", "CHA", true),
        HANDLE_ANIMAL   ("Handle Animal", "CHA", false),
        HEAL            ("Heal", "WIS", true),
        HIDE            ("Hide", "DEX", true),
        INITIMIDATE     ("Intimidate", "CHA", true),
        JUMP            ("Jump", "STR", true),
        KNOWLEDGE1      ("Knowledge", "INT", false),
        KNOWLEDGE2      ("Knowledge", "INT", false),
        KNOWLEDGE3      ("Knowledge", "INT", false),
        KNOWLEDGE4      ("Knowledge", "INT", false),
        LISTEN          ("Listen", "WIS", true),
        MOVE_SILENTLY   ("Move Silently", "DEX", true),
        OPEN_LOCK       ("Open Lock", "DEX", false),
        PERFORM1        ("Perform", "CHA", false),
        PERFORM2        ("Perform", "CHA", false),
        PERFORM3        ("Perform", "CHA", false),
        PROFESSION      ("Profession", "WIS", false),
        RIDE            ("Ride", "DEX", true),
        SEARCH          ("Search", "INT", true),
        SENSE_MOTIVE    ("Sense Motive", "WIS", true),
        SLEIGHT_OF_HAND ("Sleight of Hand", "DEX", false),
        SPELLCRAFT      ("Spellcraft", "INT", false),
        SPOT            ("Spot", "WIS", true),
        SURVIVAL        ("Survival", "WIS", true),
        SWIM            ("Swim", "STR", true),
        TUMBLE          ("Tumble", "DEX", false),
        MAGIC_DEVICE    ("Use Magic Device", "CHA", false),
        USE_ROPE        ("Use Rope", "DEX", true);

        private final String mName;
        private final String mMod;
        private final boolean mDefault;
        private static final int mSize = 42;

        Skills(String name, String mod, boolean mDefault) {
            this.mName = name;
            this.mMod = mod;
            this.mDefault = mDefault;
        }

        public String getName() { return mName;}
        public String getMod() { return mMod;}
        public boolean getDefault() { return mDefault;}
        public int getSize() { return mSize;}
    }
}
