package com.lavendergoons.dndcharacter.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.AddCharacterDialog;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterListAdapter;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;


/**
  * Initial Activity to hold list of characters
  * Add and delete characters.
  * Selecting Character will launch CharacterNavDrawerActivity,
  * to show all Character info.
 */

public class CharacterListActivity extends AppCompatActivity implements
        AddCharacterDialog.OnCharacterCompleteListener,
        CharacterListAdapter.OnCharacterClickListener,
        ConfirmationDialog.ConfirmationDialogInterface,
        View.OnClickListener {

    private RecyclerView mCharacterRecyclerView;
    private RecyclerView.Adapter mCharRecyclerAdapter;
    private RecyclerView.LayoutManager mCharRecyclerLayoutManager;
    private ArrayList<Character> characters;
    private AddCharacterDialog addCharacterDialog;
    private FloatingActionButton fab;
    Toolbar mToolbar;
    private DBAdapter dbAdapter;
    public static final String TAG = "CHARACTER_LIST";

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        gson = new Gson();
        characters = new ArrayList<Character>();
        createView();
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        getCharacters();
        Log.d("APP", "onCreate");
    }

    private void createView() {
        mToolbar = (Toolbar) findViewById(R.id.character_list_toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        mCharacterRecyclerView = (RecyclerView) findViewById(R.id.characterListRecyclerView);

        // Keeps View same size on content change
        mCharacterRecyclerView.setHasFixedSize(true);

        mCharRecyclerLayoutManager = new LinearLayoutManager(this);
        mCharacterRecyclerView.setLayoutManager(mCharRecyclerLayoutManager);

        mCharRecyclerAdapter = new CharacterListAdapter(this, characters);
        mCharacterRecyclerView.setAdapter(mCharRecyclerAdapter);

        fab = (FloatingActionButton) findViewById(R.id.addCharacterFAB);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        //Log.d("APP", "onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        //Log.d("APP", "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        //Log.d("APP", "onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        //Log.d("APP", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //Log.d("APP", "onDestroy");
        dbAdapter.close();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addCharacterFAB:
                addCharacterDialog = new AddCharacterDialog();
                addCharacterDialog.show(getSupportFragmentManager(), getString(R.string.tag_add_character_dialog));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_character_list_menu, menu);
        return true;
    }

    @Override
    public void onCharacterComplete(Character character) {
        String characterJson = gson.toJson(character);
        dbAdapter.insertRow(characterJson);
        characters.add(character);
        mCharRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCharacterClick(String name) {
        Intent intent = new Intent(this, CharacterNavDrawerActivity.class);
        intent.putExtra(Constants.CHARACTER_KEY, getCharacterFromName(name));
        intent.putExtra(Constants.CHARACTER_ID, getCharacterId(name));
        startActivity(intent);
        finish();
    }


    @Override
    public void ConfirmDialogOk(Object o) {
        if (o instanceof Character) {
            int i = characters.indexOf(o);
            dbAdapter.deleteRow(getCharacterId(characters.get(i).getName()));
            characters.remove(i);
            mCharRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {}

    private Character getCharacterFromName(String name) {
        for(Character c : characters) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    private void getCharacters() {
        Cursor c = dbAdapter.getAllCharacterNames();
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String json = c.getString(c.getColumnIndex(DBAdapter.COLUMN_CHARACTER));
                Log.d("JSON", "Character json string "+json);
                Character character = gson.fromJson(json, Character.class);
                characters.add(character);
            }
            mCharRecyclerAdapter.notifyDataSetChanged();
            c.close();
        }
    }

    private long getCharacterId(String name) {
        long id = -1;
        try {
            Cursor c = dbAdapter.getCharacterId();
            if (c != null) {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    String json = c.getString(c.getColumnIndex(DBAdapter.COLUMN_CHARACTER));
                    Character character = gson.fromJson(json, Character.class);
                    if (character.getName().equals(name)) {
                        id = (long) c.getInt(c.getColumnIndex(DBAdapter.COLUMN_ID));
                        break;
                    }
                }
            }
            c.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

}
