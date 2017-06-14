package com.lavendergoons.dndcharacter.Utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Exceptions.DatabaseNotInitializedException;
import com.lavendergoons.dndcharacter.Exceptions.IllegalCharacterIdException;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.Attack;
import com.lavendergoons.dndcharacter.Objects.Feat;
import com.lavendergoons.dndcharacter.Objects.Item;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.Objects.Spell;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Managing all read and writes to Character
 * Communicates with database
 */

public class CharacterManager {

    public static final String TAG = "CHAR_MANAGER";

    private static CharacterManager mInstance;
    private ArrayList<SimpleCharacter> simpleCharacters = new ArrayList<>();
    private Character character;
    private DBAdapter dbAdapter;
    private Long characterId;
    private String characterName;

    private Gson gson = new Gson();

    private CharacterManager() {

    }

    //TODO CLEAN UP CASTING FOR CHARACTER SETS

    public static synchronized CharacterManager getInstance() {
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
        }catch(Exception ex) {
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
        }catch(Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log(ex.toString());
        }
        this.characterId = id;
        character = new Character();
        FirebaseCrash.log(TAG+" Character: "+character);
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
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public Abilities getCharacterAbilities() {
        //TODO Move to AsyncTask
        FirebaseCrash.log(TAG+" getCharacterAbilities Character: "+character);
        if (character == null) {
            character = new Character();
        }

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
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<Armor> getCharacterArmor() {
        //TODO Move to AsyncTask
        FirebaseCrash.log(TAG+" getCharacterArmor Character: "+character);
        if (character == null) {
            character = new Character();
        }

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
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<Attack> getCharacterAttacks() {
        //TODO Move to AsyncTask
        FirebaseCrash.log(TAG+" getCharacterAttacks Character: "+character);
        if (character == null) {
            character = new Character();
        }

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
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<String> getCharacterAttributes() {
        //TODO Move to AsyncTask
        FirebaseCrash.log(TAG+" getCharacterAttributes Character: "+character);
        if (character == null) {
            character = new Character();
        }

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
    // Items
    //**********************************************************
    @SuppressWarnings("unchecked")
    private synchronized void readCharacterFeats() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_FEATS, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_FEATS));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Feat>>(){}.getType();
                    character.setFeatList((ArrayList<Feat>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<Feat> getCharacterFeats() {
        //TODO Move to AsyncTask
        FirebaseCrash.log(TAG+" getCharacterFeats Character: "+character);
        if (character == null) {
            character = new Character();
        }

        if (character.getFeatList() == null || character.getFeatList().size() == 0) {
            readCharacterFeats();
        }
        return character.getFeatList();
    }

    public void setCharacterFeats(ArrayList<Feat> feats) {
        character.setFeatList(feats);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_FEATS, gson.toJson(feats));
    }

    //**********************************************************
    // Items
    //**********************************************************
    @SuppressWarnings("unchecked")
    private synchronized void readCharacterItems() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ITEM_GENERAL, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ITEM_GENERAL));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Item>>(){}.getType();
                    character.setItemList((ArrayList<Item>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<Item> getCharacterItems() {
        if (character == null) {
            FirebaseCrash.log(TAG+" getCharacterItems Character: "+character);
            character = new Character();
        }

        if (character.getItemList() == null || character.getItemList().size() == 0) {
            readCharacterItems();
        }
        return character.getItemList();
    }

    public void setCharacterItems(ArrayList<Item> items) {
        character.setItemList(items);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_ITEM_GENERAL, gson.toJson(items));
    }

    //**********************************************************
    // Notes
    //**********************************************************
    @SuppressWarnings("unchecked")
    private void readCharacterNotes() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_NOTES, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_NOTES));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Note>>(){}.getType();
                    character.setNotesList((ArrayList<Note>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<Note> getCharacterNotes() {
        if (character == null) {
            FirebaseCrash.log(TAG+" getCharacterNotes Character: "+character);
            character = new Character();
        }

        if (character.getNotesList() == null || character.getNotesList().size() == 0) {
            readCharacterNotes();
        }
        return character.getNotesList();
    }

    public void setCharacterNotes(ArrayList<Note> notes) {
        character.setNotesList(notes);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_NOTES, gson.toJson(notes));
    }

    //**********************************************************
    // Skills
    //**********************************************************
    @SuppressWarnings("unchecked")
    private synchronized void readCharacterSkills() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_SKILL, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_SKILL));
                if (json != null && !Utils.isStringEmpty(json)) {
                    Type skillType = new TypeToken<ArrayList<Skill>>(){}.getType();
                    character.setSkillsList((ArrayList<Skill>) gson.fromJson(json, skillType));
                    FirebaseCrash.log("Skills from JSON");
                    cursor.close();
                } else {
                    initSkills();
                }
            }
        }  else {
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<Skill> getCharacterSkills() {
        if (character == null) {
            FirebaseCrash.log(TAG+" getCharacterSkills Character: "+character);
            character = new Character();
        }

        if (character.getSkillsList() == null || character.getSkillsList().size() == 0) {
            readCharacterSkills();
        }
        return character.getSkillsList();
    }

    public void setCharacterSkills(ArrayList<Skill> skills) {
        character.setSkillsList(skills);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_SKILL, gson.toJson(skills));
    }

    private void initSkills() {
        ArrayList<Skill> skillsList = new ArrayList<>();
        for (Constants.Skills s : Constants.Skills.values()) {
            skillsList.add(new Skill(s.getName(), s.getMod(), s.getDefault(), 0, 0, 0, 0));
        }
        character.setSkillsList(skillsList);
    }

    //**********************************************************
    // Spells
    //**********************************************************
    @SuppressWarnings("unchecked")
    private synchronized void readCharacterSpells() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_SPELL, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_SPELL));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Spell>>(){}.getType();
                    character.setSpellList((ArrayList<Spell>) gson.fromJson(json, attributeType));
                    cursor.close();
                }
            }
        } else {
            if (dbAdapter == null)
                throw new DatabaseNotInitializedException(TAG);
            else
                throw new IllegalCharacterIdException(TAG);
        }
    }

    public ArrayList<Spell> getCharacterSpells() {
        if (character == null) {
            FirebaseCrash.log(TAG+" getCharacterSpells Character: "+character);
            character = new Character();
        }

        if (character.getSpellList() == null || character.getSpellList().size() == 0) {
            readCharacterSpells();
        }
        return character.getSpellList();
    }

    public void setCharacterSpells(ArrayList<Spell> spells) {
        character.setSpellList(spells);
        //TODO Move to AsyncTask
        writeToDatabase(DBAdapter.COLUMN_SPELL, gson.toJson(spells));
    }

    //**********************************************************
    // Character List Calls
    //**********************************************************

    public long getCharacterId(String name) {
        long id = -1;
        if (dbAdapter == null) {
            throw new DatabaseNotInitializedException(TAG);
        }
        try {
            Cursor c = dbAdapter.getCharacterId();
            if (c != null) {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    String json = c.getString(c.getColumnIndex(DBAdapter.COLUMN_CHARACTER));
                    SimpleCharacter simpleCharacter = gson.fromJson(json, SimpleCharacter.class);
                    if (simpleCharacter.getName().equals(name)) {
                        id = (long) c.getInt(c.getColumnIndex(DBAdapter.COLUMN_ID));
                        break;
                    }
                }
            }
            c.close();
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log(TAG +ex.toString());
        }
        return id;
    }

    public SimpleCharacter getCharacterFromName(String name) {
        for(SimpleCharacter c : simpleCharacters) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    public ArrayList<SimpleCharacter> getCharacters() {
        if (dbAdapter == null) {
            throw new DatabaseNotInitializedException(TAG);
        }
        Cursor c = dbAdapter.getAllCharacterNames();
        simpleCharacters.clear();
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String json = c.getString(c.getColumnIndex(DBAdapter.COLUMN_CHARACTER));
                Log.d("JSON", "SimpleCharacter json string "+json);
                SimpleCharacter simpleCharacter = gson.fromJson(json, SimpleCharacter.class);
                simpleCharacters.add(simpleCharacter);
            }
            c.close();
        }
        return simpleCharacters;
    }

    public void insertCharacter(String characterJson) {
        if (dbAdapter == null) {
            throw new DatabaseNotInitializedException(TAG);
        }
        dbAdapter.insertRow(characterJson);
    }

    public void destroyCharacter(String name) {
        if (dbAdapter == null) {
            throw new DatabaseNotInitializedException(TAG);
        }
        dbAdapter.deleteRow(getCharacterId(name));
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
        private ArrayList<Feat> featList = new ArrayList<>();
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

        ArrayList<Feat> getFeatList() {
            return featList;
        }

        void setFeatList(ArrayList<Feat> featList) {
            this.featList = featList;
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
        final int JSON = 0;
        private String column;

        public WriteToDatabaseTask(String column) {
            super();
            this.column = column;
        }

        @Override
        protected Void doInBackground(String... strings) {
            dbAdapter.fillColumn(characterId, column, strings[JSON]);
            return null;
        }
    }
}
