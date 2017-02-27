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
import android.widget.Toast;

import com.google.gson.Gson;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.AddCharacterDialog;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterListAdapter;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;

import static com.lavendergoons.dndcharacter.Utils.Constants.CHARACTER_ID;
import static com.lavendergoons.dndcharacter.Utils.Constants.CHARACTER_KEY;

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
    private DBAdapter dbAdapter;
    public static final String TAG = "CHARACTER_LIST";

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        gson = new Gson();
        characters = new ArrayList<Character>();
        //characters.add(new Character("Silian Mord", 1));
        //characters.add(new Character("Montgomery", 15));
        //characters.add(new Character("Darth Vader", 13));
        //characters.add(new Character("Boba Fett", 4));
        createView();
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        getCharacters();
        Log.d("APP", "onCreate");
    }

    private void createView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.character_list_toolbar);
        toolbar.setTitle(getString(R.string.title_activity_character_list));
        setSupportActionBar(toolbar);

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
        Log.d("APP", "onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d("APP", "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("APP", "onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d("APP", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("APP", "onDestroy");
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
        switch (item.getItemId()) {
            case R.id.actionSettings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
        //TODO Clean up
        Toast.makeText(this, "CharacterListActivity Confirm", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ConfirmDialogCancel(Object o) {
        Toast.makeText(this, "CharacterListActivity Cancel", Toast.LENGTH_SHORT).show();
    }

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
                String json = c.getString(c.getColumnIndex(dbAdapter.COLUMN_CHARACTER));
                Character character = gson.fromJson(json, Character.class);
                characters.add(character);
            }
            mCharRecyclerAdapter.notifyDataSetChanged();
            c.close();
        }
    }

    //TODO maybe put id inside character object
    private long getCharacterId(String name) {
        Cursor c = dbAdapter.getCharacterId();
        long id = -1;
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String json = c.getString(c.getColumnIndex(dbAdapter.COLUMN_CHARACTER));
                Character character = gson.fromJson(json, Character.class);
                if (character.getName().equals(name)) {
                    id = (long) c.getInt(c.getColumnIndex(dbAdapter.COLUMN_ID));
                    break;
                }
            }
        }
        c.close();
        return id;
    }
}

/*
    NavDrawer Activity Layout

    activity_main
        include:
            Appbar
                include:
                    content_main
         NavigationMenu

 */
