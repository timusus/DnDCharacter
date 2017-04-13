package com.lavendergoons.dndcharacter.Utils;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.Attack;
import com.lavendergoons.dndcharacter.Objects.Character2;
import com.lavendergoons.dndcharacter.Objects.Item;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Managing all read and writes to Character2
 */

public class CharacterManager {

    public static final String TAG = "CHAR_MANAGER";

    private CharacterManager mInstance;
    private Context mContext;
    private Character2 mCharacter;
    private Character character;
    private DBAdapter dbAdapter;
    private Long characterId;
    private String characterName;

    private Gson gson = new Gson();

    private CharacterManager() {

    }

    public synchronized CharacterManager getInstance() {
        if (mInstance == null) {
            mInstance = new CharacterManager();
        }
        return mInstance;
    }

    public void loadCharacter(String name, Long id) {
        this.characterName = name;
        this.characterId = id;
    }

    public void loadCharacter(Long id) {
        this.characterId = id;
    }

    public void loadDatabase(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    //**********************************************************
    // Abilities
    //**********************************************************

    /**
     * Read Character Ability Values from Database
     */
    private void readCharacterAbilities() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ABILITIES, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ABILITIES));
                if (json != null && !Utils.isStringEmpty(json)) {
                    mCharacter.setAbilities(gson.fromJson(json, Abilities.class));
                    character.setAbilities(gson.fromJson(json, Abilities.class));
                    FirebaseCrash.log("Abilities from JSON");
                } else {
                    mCharacter.setAbilities(new Abilities());
                    character.setAbilities(new Abilities());
                    FirebaseCrash.log("New Abilities Object");
                }
                cursor.close();
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    public Abilities getCharacterAbilities() {
        readCharacterAbilities();
        return character.getAbilities();
    }

    public void setCharacterAbilities(Abilities abilities) {
        writeCharacterAbilities(abilities);
    }

    private void writeCharacterAbilities(Abilities abilities) {
        character.setAbilities(abilities);
    }

    //**********************************************************
    // Armor
    //**********************************************************

    //TODO CLEAN UP
    @SuppressWarnings("unchecked")
    private void readCharacterArmor() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ARMOR, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ARMOR));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Armor>>(){}.getType();
                    attributeType.getClass();
                    mCharacter.setArmorList((ArrayList<Armor>) gson.fromJson(json, attributeType));
                    character.setArmorList((ArrayList<Armor>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Public method for getting Armor
     * @return
     */
    public ArrayList<Armor> getCharacterArmor() {
        readCharacterArmor();
        return character.getArmorList();
    }

    /**
     * Public method for setting Armor
     * @param armorList
     */
    public void setCharacterArmor(ArrayList<Armor> armorList) {
        writeCharacterArmor(armorList);
    }

    /**
     * Private method for setting Armor
     * //TODO call databse asynctask to write to DB
     * @param armorList
     */
    private void writeCharacterArmor(ArrayList<Armor> armorList) {
        character.setArmorList(armorList);
    }

    //**********************************************************
    // Attacks
    //**********************************************************

    @SuppressWarnings("unchecked")
    private void readCharacterAttacks() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ATTACK, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ATTACK));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Attack>>(){}.getType();
                    mCharacter.setAttackList((ArrayList<Attack>) gson.fromJson(json, attributeType));
                    character.setAttackList((ArrayList<Attack>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Attack> getCharacterAttacks() {
        readCharacterAttacks();
        return character.getAttackList();
    }

    public void setCharacterAttacks(ArrayList<Attack> attacks) {
        writeCharacterAttacks(attacks);
    }

    private void writeCharacterAttacks(ArrayList<Attack> attacks) {
        character.setAttackList(attacks);
    }

    //**********************************************************
    // Database
    //**********************************************************

    private void writeToDatabase(String column, String json) {
        dbAdapter.fillColumn(characterId, column, json);
    }

    /*public static class Database {
        public static void loadDatabase(DBAdapter dbAdapter) {
            try {
                loadDatabase(dbAdapter);
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }*/

    private class Character {
        private Abilities abilities;
        private ArrayList<Armor> armorList = new ArrayList<>();
        private ArrayList<Attack> attackList = new ArrayList<>();
        private ArrayList<String> attributesList = new ArrayList<>(Constants.ATTRIBUTES.length);
        private ArrayList<Item> itemList = new ArrayList<>();
        private ArrayList<Note> notesList = new ArrayList<>();
        private ArrayList<Skill> skillsList = new ArrayList<>();
        private ArrayList<Spell> spellList = new ArrayList<>();

        private Character() {

        }

        Abilities getAbilities() {
            return abilities;
        }

        void setAbilities(Abilities abilities) {
            this.abilities = abilities;
        }

        ArrayList<Armor> getArmorList() {
            return armorList;
        }

        void setArmorList(ArrayList<Armor> armorList) {
            this.armorList = armorList;
        }

        ArrayList<Attack> getAttackList() {
            return attackList;
        }

        void setAttackList(ArrayList<Attack> attackList) {
            this.attackList = attackList;
        }

        ArrayList<String> getAttributesList() {
            return attributesList;
        }

        void setAttributesList(ArrayList<String> attributesList) {
            this.attributesList = attributesList;
        }

        ArrayList<Item> getItemList() {
            return itemList;
        }

        void setItemList(ArrayList<Item> itemList) {
            this.itemList = itemList;
        }

        ArrayList<Note> getNotesList() {
            return notesList;
        }

        void setNotesList(ArrayList<Note> notesList) {
            this.notesList = notesList;
        }

        ArrayList<Skill> getSkillsList() {
            return skillsList;
        }

        void setSkillsList(ArrayList<Skill> skillsList) {
            this.skillsList = skillsList;
        }

        public ArrayList<Spell> getSpellList() {
            return spellList;
        }

        void setSpellList(ArrayList<Spell> spellList) {
            this.spellList = spellList;
        }
    }
}
