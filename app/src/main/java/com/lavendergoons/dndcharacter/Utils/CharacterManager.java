package com.lavendergoons.dndcharacter.Utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Exceptions.DatabaseNotInitializedException;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.Attack;
import com.lavendergoons.dndcharacter.Objects.Item;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Managing all read and writes to Character
 * Communicates with database
 */

public class CharacterManager {

    public static final String TAG = "CHAR_MANAGER";

    private static CharacterManager mInstance;
    private static Context mContext;
    private Character character;
    private DBAdapter dbAdapter;
    private Long characterId;
    private String characterName;

    private Gson gson = new Gson();

    private CharacterManager() {

    }

    public static synchronized CharacterManager getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new CharacterManager();
        }
        return mInstance;
    }

    public void loadCharacter(String name, Long id) {
        try {
            if (dbAdapter == null) {
                throw new DatabaseNotInitializedException(TAG);
            }
        }catch(DatabaseNotInitializedException ex) {
            ex.printStackTrace();
            FirebaseCrash.log(ex.toString());
        }
        this.characterName = name;
        this.characterId = id;
    }

    public void loadCharacter(Long id) {
        try {
            if (dbAdapter == null) {
                throw new DatabaseNotInitializedException(TAG);
            }
        }catch(DatabaseNotInitializedException ex) {
            ex.printStackTrace();
            FirebaseCrash.log(ex.toString());
        }
        this.characterId = id;
        character = new Character();
    }

    public void loadDatabase(DBAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    public void destroy() {
        mInstance = null;
        dbAdapter = null;
    }

    //**********************************************************
    // SimpleCharacter
    //**********************************************************
    public void setSimpleCharacter(SimpleCharacter simpleCharacter) {
        writeToDatabase(DBAdapter.COLUMN_CHARACTER, gson.toJson(simpleCharacter));
        Log.d(TAG, simpleCharacter.toString());
    }

    //**********************************************************
    // Abilities
    //**********************************************************
    private synchronized void readCharacterAbilities() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ABILITIES, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ABILITIES));
                if (json != null && !Utils.isStringEmpty(json)) {
                    character.setAbilities(gson.fromJson(json, Abilities.class));
                    FirebaseCrash.log("Abilities from JSON");
                } else {
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
        //TODO Move to AsyncTask
        if (character.getAbilities() == null) {
            readCharacterAbilities();
        }
        return character.getAbilities();
    }

    public void setCharacterAbilities(Abilities abilities) {
        character.setAbilities(abilities);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_ABILITIES, gson.toJson(abilities));
    }

    //**********************************************************
    // Armor
    //**********************************************************
    //TODO CLEAN UP
    @SuppressWarnings("unchecked")
    private synchronized void readCharacterArmor() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ARMOR, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ARMOR));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Armor>>(){}.getType();
                    attributeType.getClass();
                    character.setArmorList((ArrayList<Armor>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Armor> getCharacterArmor() {
        //TODO Move to AsyncTask
        if (character.getArmorList() == null || character.getArmorList().size() == 0) {
            readCharacterArmor();
        }
        return character.getArmorList();
    }

    public void setCharacterArmor(ArrayList<Armor> armorList) {
        character.setArmorList(armorList);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_ARMOR, gson.toJson(armorList));
    }


    //**********************************************************
    // Attacks
    //**********************************************************
    @SuppressWarnings("unchecked")
    private synchronized void readCharacterAttacks() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ATTACK, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ATTACK));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Attack>>(){}.getType();
                    character.setAttackList((ArrayList<Attack>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Attack> getCharacterAttacks() {
        //TODO Move to AsyncTask
        if (character.getAttackList() == null || character.getAttackList().size() == 0) {
            readCharacterAttacks();
        }
        return character.getAttackList();
    }

    public void setCharacterAttacks(ArrayList<Attack> attacks) {
        character.setAttackList(attacks);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_ATTACK, gson.toJson(attacks));
    }

    //**********************************************************
    // Attributes
    //**********************************************************
    @SuppressWarnings("unchecked")
    private synchronized void readCharacterAttributes() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ATTRIBUTES, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ATTRIBUTES));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<String>>(){}.getType();
                    character.setAttributesList((ArrayList<String>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> getCharacterAttributes() {
        //TODO Move to AsyncTask
        if (character.getAttributesList() == null || character.getAttributesList().size() == 0) {
            readCharacterAttributes();
        }
        return character.getAttributesList();
    }

    public void setCharacterAttributes(ArrayList<String> attributes) {
        character.setAttributesList(attributes);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_ATTRIBUTES, gson.toJson(attributes));
    }

    //**********************************************************
    // Database
    //**********************************************************

    private void writeToDatabase(String column, String json) {
        dbAdapter.fillColumn(characterId, column, json);
    }

    //**********************************************************
    // Private Character Object
    //**********************************************************

    private class Character {
        private String name;
        private int level;

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


    private class WriteToDatabaseTask extends AsyncTask<String, Void, Void> {
        final int COLUMN = 0;
        final int JSON = 1;
        @Override
        protected Void doInBackground(String... strings) {
            dbAdapter.fillColumn(characterId, strings[COLUMN], strings[JSON]);
            return null;
        }
    }
}
