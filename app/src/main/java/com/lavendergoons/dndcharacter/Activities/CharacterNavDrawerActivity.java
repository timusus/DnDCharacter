package com.lavendergoons.dndcharacter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Fragments.AbilitiesFragment;
import com.lavendergoons.dndcharacter.Fragments.AboutFragment;
import com.lavendergoons.dndcharacter.Fragments.ArmorFragment;
import com.lavendergoons.dndcharacter.Fragments.ArmorListFragment;
import com.lavendergoons.dndcharacter.Fragments.AttacksFragment;
import com.lavendergoons.dndcharacter.Fragments.AttributesFragment;
import com.lavendergoons.dndcharacter.Fragments.ItemsGeneralFragment;
import com.lavendergoons.dndcharacter.Fragments.NoteFragment;
import com.lavendergoons.dndcharacter.Fragments.NotesListFragment;
import com.lavendergoons.dndcharacter.Fragments.SkillsFragment;
import com.lavendergoons.dndcharacter.Fragments.SpellFragment;
import com.lavendergoons.dndcharacter.Fragments.SpellListFragment;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;

/**
 * Nav Drawer Activity to display fragments,
 * with all SimpleCharacter info.
 */

public class CharacterNavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AbilitiesFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        ArmorFragment.OnFragmentInteractionListener,
        ArmorListFragment.OnFragmentInteractionListener,
        AttacksFragment.OnFragmentInteractionListener,
        AttributesFragment.OnFragmentInteractionListener,
        ItemsGeneralFragment.OnFragmentInteractionListener,
        NotesListFragment.OnFragmentInteractionListener,
        NoteFragment.OnFragmentInteractionListener,
        SkillsFragment.OnFragmentInteractionListener,
        SpellFragment.OnFragmentInteractionListener,
        SpellListFragment.OnFragmentInteractionListener,
        FragmentManager.OnBackStackChangedListener,
        ConfirmationDialog.ConfirmationDialogInterface {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    public static final String TAG = "CHARACTER_NAV";

    private boolean toolbarListenerRegister = false;

    private DBAdapter dbAdapter;
    private SimpleCharacter simpleCharacter;
    private CharacterManager characterManager;
    private long characterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_nav);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            simpleCharacter = (extras.get(Constants.CHARACTER_KEY) instanceof SimpleCharacter)? (SimpleCharacter) extras.get(Constants.CHARACTER_KEY) : new SimpleCharacter();
            characterId = extras.getLong(Constants.CHARACTER_ID);
        }

        mToolbar = (Toolbar) findViewById(R.id.character_nav_toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (savedInstanceState == null) {
            loadDefaultFragment();
        }
        mToolbar.setTitle(getString(R.string.title_fragment_attributes));
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        //Load database in CharacterManager
        characterManager = CharacterManager.getInstance(this);
        characterManager.loadDatabase(dbAdapter);
        characterManager.loadCharacter(characterId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showBackButton(boolean enabled) {
        try {
            if (enabled) {
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                if (!toolbarListenerRegister) {
                    mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                }
                toolbarListenerRegister = true;
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                mDrawerToggle.setToolbarNavigationClickListener(null);
                toolbarListenerRegister = false;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setDrawerLock(boolean locked) {
        if (locked) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            mDrawerToggle.syncState();
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            mDrawerToggle.syncState();
        }
    }

    private boolean isCurrentFragment(String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        return frag != null && frag.isVisible();
    }

    @Override
    public void onBackStackChanged() {
        if (isCurrentFragment(SpellFragment.TAG) || isCurrentFragment(ArmorFragment.TAG)
                || isCurrentFragment(AboutFragment.TAG) || isCurrentFragment(NoteFragment.TAG)) {
            showBackButton(true);
            setDrawerLock(true);
        } else {
            showBackButton(false);
            setDrawerLock(false);
        }
        mToolbar.setTitle(getCurrentFragmentTitle());
    }

    private String getCurrentFragmentTitle() {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.content_character_nav);
        String title = getString(R.string.app_name);
        if (frag != null) {
            if (frag instanceof AttributesFragment) {
                title = getString(R.string.title_fragment_attributes);
            } else if (frag instanceof AboutFragment) {
                title = getString(R.string.title_fragment_about);
            } else if (frag instanceof AbilitiesFragment) {
                title = getString(R.string.title_fragment_abilities);
            } else if (frag instanceof SkillsFragment) {
                title = getString(R.string.title_fragment_skills);
            } else if (frag instanceof AttacksFragment) {
                title = getString(R.string.title_fragment_attacks);
            } else if (frag instanceof ItemsGeneralFragment) {
                title = getString(R.string.title_fragment_items);
            } else if (frag instanceof ArmorListFragment) {
                title = getString(R.string.title_fragment_armor);
            } else if (frag instanceof SpellListFragment) {
                title = getString(R.string.title_fragment_spells);
            } else if (frag instanceof NotesListFragment) {
                title = getString(R.string.title_fragment_notes);
            }else if (frag instanceof NoteFragment) {
                title = getString(R.string.title_fragment_notes);
            }
        }
        return title;
    }

    @Override
    protected void onStop() {
        dbAdapter.close();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionAbout) {
            mToolbar.setTitle(getString(R.string.title_fragment_about));
            FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.content_character_nav, AboutFragment.newInstance(), AboutFragment.TAG).addToBackStack(AboutFragment.TAG).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDefaultFragment() {
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_character_nav, AttributesFragment.newInstance(simpleCharacter, characterId),  AttributesFragment.TAG).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = new Fragment();
        String tag = "tag";
        String title = "";
        switch (item.getItemId()) {
            case R.id.nav_characters:
                // Stop Attributes Fragment to Write Name/Level Changes
                if (isCurrentFragment(AttributesFragment.TAG)) {
                    Fragment frag = getSupportFragmentManager().findFragmentByTag(AttributesFragment.TAG);
                    frag.onStop();
                }
                Intent intent = new Intent(this, CharacterListActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_attributes:
                fragment = AttributesFragment.newInstance(simpleCharacter, characterId);
                tag = AttributesFragment.TAG;
                title = getString(R.string.title_fragment_attributes);
                break;
            case R.id.nav_abilities:
                fragment = AbilitiesFragment.newInstance(simpleCharacter, characterId);
                tag = AbilitiesFragment.TAG;
                title = getString(R.string.title_fragment_abilities);
                break;
            case R.id.nav_skills:
                fragment = SkillsFragment.newInstance(simpleCharacter, characterId);
                tag = SkillsFragment.TAG;
                title = getString(R.string.title_fragment_skills);
                break;
            case R.id.nav_attacks:
                fragment = AttacksFragment.newInstance(simpleCharacter, characterId);
                tag = AttacksFragment.TAG;
                title = getString(R.string.title_fragment_attacks);
                break;
            case R.id.nav_items:
                fragment = ItemsGeneralFragment.newInstance(simpleCharacter, characterId);
                tag = ItemsGeneralFragment.TAG;
                title = getString(R.string.title_fragment_items);
                break;
            case R.id.nav_armor:
                fragment = ArmorListFragment.newInstance(simpleCharacter, characterId);
                tag = ArmorListFragment.TAG;
                title = getString(R.string.title_fragment_armor);
                break;
            case R.id.nav_spells:
                fragment = SpellListFragment.newInstance(simpleCharacter, characterId);
                tag = SpellListFragment.TAG;
                title = getString(R.string.title_fragment_spells);
                break;
            case R.id.nav_notes:
                fragment = NotesListFragment.newInstance(simpleCharacter, characterId);
                tag = NotesListFragment.TAG;
                title = getString(R.string.title_fragment_notes);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (!isCurrentFragment(tag)) {
            mToolbar.setTitle(title);
            FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.content_character_nav, fragment, tag).commit();
        }
        return true;
    }

    @Override
    public void ConfirmDialogOk(Object o) {

    }

    @Override
    public void ConfirmDialogCancel(Object o) {
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void passBackSpell(Spell spell, int i) {
        try {
            SpellListFragment frag = (SpellListFragment) getSupportFragmentManager().findFragmentByTag(SpellListFragment.TAG);
            frag.retrieveSpell(spell, i);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void passBackArmor(Armor armor, int index) {
        try {
            ArmorListFragment frag = (ArmorListFragment) getSupportFragmentManager().findFragmentByTag(ArmorListFragment.TAG);
            frag.retrieveArmor(armor, index);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void passBackNote(Note note, int index) {
        try {
            NotesListFragment frag = (NotesListFragment) getSupportFragmentManager().findFragmentByTag(NotesListFragment.TAG);
            frag.retrieveNote(note, index);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DBAdapter getDbAdapter() {
        return dbAdapter;
    }
}
