package com.lavendergoons.dndcharacter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.AddCharacterDialog;
import com.lavendergoons.dndcharacter.Fragments.AboutFragment;
import com.lavendergoons.dndcharacter.Fragments.CharacterListFragment;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Adapters.CharacterListAdapter;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;


/**
  * Initial Activity to hold list of simpleCharacters
  * Add and delete simpleCharacters.
  * Selecting SimpleCharacter will launch CharacterNavDrawerActivity,
  * to show all SimpleCharacter info.
 */

public class CharacterListActivity extends AppCompatActivity implements
        /*AddCharacterDialog.OnCharacterCompleteListener,
        CharacterListAdapter.OnCharacterClickListener,
        ConfirmationDialog.ConfirmationDialogInterface,
        View.OnClickListener,*/
        AboutFragment.OnFragmentInteractionListener,
        CharacterListFragment.OnCharacterClickListener{

    private RecyclerView mCharacterRecyclerView;
    private CharacterListAdapter mCharRecyclerAdapter;
    private RecyclerView.LayoutManager mCharRecyclerLayoutManager;
    private ArrayList<SimpleCharacter> simpleCharacters;
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
        simpleCharacters = new ArrayList<SimpleCharacter>();
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        createView();
        //getCharacters();
        Log.d("APP", "onCreate");
    }

    private void createView() {
        mToolbar = (Toolbar) findViewById(R.id.character_list_toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_character_list, CharacterListFragment.newInstance(), CharacterListFragment.TAG).commit();
/*
        mCharacterRecyclerView = (RecyclerView) findViewById(R.id.characterListRecyclerView);

        // Keeps View same size on content change
        mCharacterRecyclerView.setHasFixedSize(true);

        mCharRecyclerLayoutManager = new LinearLayoutManager(this);
        mCharacterRecyclerView.setLayoutManager(mCharRecyclerLayoutManager);

        mCharRecyclerAdapter = new CharacterListAdapter(this, simpleCharacters);
        mCharacterRecyclerView.setAdapter(mCharRecyclerAdapter);

        fab = (FloatingActionButton) findViewById(R.id.addCharacterFAB);
        fab.setOnClickListener(this);
        */
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
    public void onFragmentCharacterClick(SimpleCharacter simpleCharacter, long id) {
        Intent intent = new Intent(this, CharacterNavDrawerActivity.class);
        intent.putExtra(Constants.CHARACTER_KEY, simpleCharacter);
        intent.putExtra(Constants.CHARACTER_ID, id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isCurrentFragment(AboutFragment.TAG)) {
            mToolbar.setTitle(getString(R.string.app_name));
        }
        super.onBackPressed();
    }

    private boolean isCurrentFragment(String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        return frag != null && frag.isVisible();
    }

    /*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addCharacterFAB:
                addCharacterDialog = new AddCharacterDialog();
                addCharacterDialog.show(getSupportFragmentManager(), getString(R.string.tag_add_character_dialog));
                break;
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAbout:
                mToolbar.setTitle(getString(R.string.title_fragment_about));
                FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
                fragTransaction.replace(R.id.content_character_list, AboutFragment.newInstance(), AboutFragment.TAG).addToBackStack(AboutFragment.TAG).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
/*
    @Override
    public void onCharacterComplete(SimpleCharacter character) {
        String characterJson = gson.toJson(character);
        dbAdapter.insertRow(characterJson);
        simpleCharacters.add(character);
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
        if (o instanceof SimpleCharacter) {
            int i = simpleCharacters.indexOf(o);
            dbAdapter.deleteRow(getCharacterId(simpleCharacters.get(i).getName()));
            simpleCharacters.remove(i);
            mCharRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {}

    private SimpleCharacter getCharacterFromName(String name) {
        for(SimpleCharacter c : simpleCharacters) {
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
                Log.d("JSON", "SimpleCharacter json string "+json);
                SimpleCharacter character = gson.fromJson(json, SimpleCharacter.class);
                simpleCharacters.add(character);
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
                    SimpleCharacter character = gson.fromJson(json, SimpleCharacter.class);
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
    }*/

    @Override
    public void onFragmentInteraction() {

    }

    public DBAdapter getDbAdapter() {
        return dbAdapter;
    }
}
