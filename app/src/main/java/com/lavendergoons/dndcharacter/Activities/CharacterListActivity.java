package com.lavendergoons.dndcharacter.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Fragments.AboutFragment;
import com.lavendergoons.dndcharacter.Fragments.CharacterListFragment;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;



/**
  * Initial Activity to hold list of simpleCharacters
  * Add and delete simpleCharacters.
  * Selecting SimpleCharacter will launch CharacterNavDrawerActivity,
  * to show all SimpleCharacter info.
 */

public class CharacterListActivity extends AppCompatActivity implements
        AboutFragment.OnFragmentInteractionListener,
        CharacterListFragment.OnCharacterClickListener{

    public static final String TAG = "CHARACTER_LIST";
    private static final String FIRST_OPEN = "FIRST_OPEN";

    Toolbar mToolbar;
    private DBAdapter dbAdapter;

    boolean isFirstOpen = true;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        createView();

        sharedPreferences = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        isFirstOpen = sharedPreferences.getBoolean(FIRST_OPEN, true);

        //if (isFirstOpen) {
            new AlertDialog.Builder(this).setTitle(getString(R.string.announcement_title))
                    .setMessage(getString(R.string.announcement_first_open_message))
                    .setPositiveButton(R.string.ok, null)
                    .create().show();
            isFirstOpen = false;
        //}
        sharedEditor.putBoolean(FIRST_OPEN, isFirstOpen);
        sharedEditor.apply();
    }

    private void createView() {
        mToolbar = (Toolbar) findViewById(R.id.character_list_toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_character_list, CharacterListFragment.newInstance(), CharacterListFragment.TAG).commit();
    }

    @Override
    protected void onStart() {
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        super.onStart();
    }

    @Override
    protected void onStop() {
        dbAdapter.close();
        super.onStop();
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

    @Override
    public void onFragmentInteraction() {

    }

    public DBAdapter getDbAdapter() {
        return dbAdapter;
    }
}
