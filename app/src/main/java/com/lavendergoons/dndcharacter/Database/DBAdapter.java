package com.lavendergoons.dndcharacter.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lavendergoons.dndcharacter.Utils.Utils;


public class DBAdapter {

    public static final String TAG = "DATABASE_ADAPTER";

    private static final String TABLE_CHARACTERS = "characters";
    private static final String DATABASE_NAME = "DnD_Characters.db";
    public static final int DATABASE_VERSION = 3;


    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_CHARACTER = "character";
    public static final String COLUMN_ABILITIES = "abilities";
    public static final String COLUMN_ATTRIBUTES = "attributes";
    public static final String COLUMN_ARMOR = "armor";
    public static final String COLUMN_ATTACK = "attack";
    public static final String COLUMN_ITEM_GENERAL = "item_general";
    public static final String COLUMN_SKILL = "skill";
    public static final String COLUMN_SPELL = "spell";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_FEATS = "feats";

    public static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_CHARACTER,
            COLUMN_ABILITIES,
            COLUMN_ATTRIBUTES,
            COLUMN_ARMOR,
            COLUMN_ATTACK,
            COLUMN_ITEM_GENERAL,
            COLUMN_SKILL,
            COLUMN_SPELL,
            COLUMN_NOTES,
            COLUMN_FEATS
    };

    //*********************************************
    // PUT COMMA AFTER text not null default
    //*********************************************
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_CHARACTERS + " (" + COLUMN_ID +
                    " integer primary key autoincrement, " +
                    COLUMN_CHARACTER + " text not null default '', " +
                    COLUMN_ABILITIES + " text not null default '', " +
                    COLUMN_ATTRIBUTES + " text not null default '', " +
                    COLUMN_ARMOR + " text not null default '', " +
                    COLUMN_ATTACK + " text not null default '', " +
                    COLUMN_ITEM_GENERAL + " text not null default '', " +
                    COLUMN_SKILL + " text not null default '', " +
                    COLUMN_SPELL + " text not null default '', " +
                    COLUMN_NOTES + " text not null default '', " +
                    COLUMN_FEATS + " text not null default ''" +
                    " );";

    private final Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter(Context context) {
        this.context = context;
        // Init DatabaseHelper
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor getAllCharacterNames() {
        Cursor cursor = database.query(true, TABLE_CHARACTERS, new String[]{COLUMN_CHARACTER}, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        return null;
    }

    public Cursor getAllRows() {
        Cursor cursor = database.query(true, TABLE_CHARACTERS, ALL_COLUMNS, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        return null;
    }

    public Cursor getCharacterId() {
        Cursor cursor = database.query(true, TABLE_CHARACTERS, new String[]{COLUMN_ID, COLUMN_CHARACTER}, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        return null;
    }

    public boolean insertRow(String characterName) {
        if (!Utils.isStringEmpty(characterName) && database.isOpen()) {
            ContentValues values = new ContentValues();
            values = fillBlankColumns(values);
            values.put(COLUMN_CHARACTER, characterName);
            long id = database.insert(TABLE_CHARACTERS, null, values);
            Log.d(TAG, "Insert Row ID "+id+" for character "+characterName);
            return true;
        }
        return false;
    }

    public Cursor getColumnCursor(String column, long id) {
        if (!Utils.isStringEmpty(column) && database.isOpen()) {
            Cursor cursor = database.query(true, TABLE_CHARACTERS, new String[]{COLUMN_ID, column}, COLUMN_ID+ " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                return cursor;
            }
        }
        return null;
    }

    public boolean fillColumn(long id, String col, String value) {
        if (!Utils.isStringEmpty(value) && !Utils.isStringEmpty(col) && database.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(col, value);
            // Update TABLE with values WHERE COLUMN_CHARACTER = character
            database.update(TABLE_CHARACTERS, values, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
            return true;
        }
        return false;
    }

   public void deleteRows() {
        Cursor cursor = getAllRows();
        long id = cursor.getColumnIndexOrThrow(COLUMN_ID);
        if (cursor.moveToFirst()) {
            do {
                deleteRow(cursor.getLong((int) id));
            }while (cursor.moveToNext());
        }
    }

    public int deleteRow(long id) {
        String where = COLUMN_ID + " = ?";
        return database.delete(TABLE_CHARACTERS, where, new String[]{String.valueOf(id)});
    }

    private ContentValues fillBlankColumns(ContentValues values) {
        values.put(COLUMN_ABILITIES, "");
        values.put(COLUMN_ATTRIBUTES, "");
        values.put(COLUMN_ARMOR, "");
        values.put(COLUMN_ATTACK, "");
        values.put(COLUMN_ITEM_GENERAL, "");
        values.put(COLUMN_SKILL, "");
        values.put(COLUMN_SPELL, "");
        values.put(COLUMN_NOTES, "");
        values.put(COLUMN_FEATS, "");
        return values;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, databaseName, factory, version);
        }

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            //TODO Refactor and make this better
            Log.w(TAG, "Upgrading SQLite Table");

            // Upgrading from 1 to 2 add notes column
            if (newVersion == 2 && oldVersion == 1) {
                sqLiteDatabase.execSQL("ALTER TABLE "+TABLE_CHARACTERS+" ADD COLUMN "+COLUMN_NOTES +" text not null default ''");
            }

            // Upgrading from 2 to 3 add feats column
            if (newVersion == 3 && oldVersion == 2) {
                sqLiteDatabase.execSQL("ALTER TABLE "+TABLE_CHARACTERS+" ADD COLUMN "+COLUMN_FEATS +" text not null default ''");
            }

            // Upgrading from 1 to 3 add notes & feats column
            if (newVersion == 3 && oldVersion == 1) {
                sqLiteDatabase.execSQL("ALTER TABLE "+TABLE_CHARACTERS+" ADD COLUMN "+COLUMN_NOTES +" text not null default ''");
                sqLiteDatabase.execSQL("ALTER TABLE "+TABLE_CHARACTERS+" ADD COLUMN "+COLUMN_FEATS +" text not null default ''");
            }
        }
    }
}
