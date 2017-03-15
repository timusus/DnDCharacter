package com.lavendergoons.dndcharacter.Activities;

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
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Fragments.AbilitiesFragment;
import com.lavendergoons.dndcharacter.Fragments.ArmorFragment;
import com.lavendergoons.dndcharacter.Fragments.ArmorListFragment;
import com.lavendergoons.dndcharacter.Fragments.AttacksFragment;
import com.lavendergoons.dndcharacter.Fragments.AttributesFragment;
import com.lavendergoons.dndcharacter.Fragments.ItemsGeneralFragment;
import com.lavendergoons.dndcharacter.Fragments.SkillsFragment;
import com.lavendergoons.dndcharacter.Fragments.SpellFragment;
import com.lavendergoons.dndcharacter.Fragments.SpellListFragment;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

/**
 * Nav Drawer Activity to display fragments,
 * with all Character info.
 */

public class CharacterNavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AbilitiesFragment.OnFragmentInteractionListener,
        ArmorFragment.OnFragmentInteractionListener,
        ArmorListFragment.OnFragmentInteractionListener,
        AttacksFragment.OnFragmentInteractionListener,
        AttributesFragment.OnFragmentInteractionListener,
        ItemsGeneralFragment.OnFragmentInteractionListener,
        SkillsFragment.OnFragmentInteractionListener,
        SpellFragment.OnFragmentInteractionListener,
        SpellListFragment.OnFragmentInteractionListener,
        FragmentManager.OnBackStackChangedListener,
        ConfirmationDialog.ConfirmationDialogInterface {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static final String TAG = "CHARACTER_NAV";
    private boolean toolbarListenerRegister = false;
    Toolbar mToolbar;
    private Character character;
    private long characterId;
    private DBAdapter dbAdapter;
    private Armor tempArmor;
    private int tempArmorIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_nav);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            character = (extras.get(Constants.CHARACTER_KEY) instanceof Character)? (Character) extras.get(Constants.CHARACTER_KEY) : new Character();
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
        if (isCurrentFragment(SpellFragment.TAG) || isCurrentFragment(ArmorFragment.TAG)) {
            showBackButton(true);
            setDrawerLock(true);
        } else {
            showBackButton(false);
            setDrawerLock(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void loadDefaultFragment() {
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_character_nav, AttributesFragment.newInstance(character, characterId),  AttributesFragment.TAG).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = new Fragment();
        String tag = "tag";
        String title = "";
        switch (item.getItemId()) {
            case R.id.nav_attributes:
                fragment = AttributesFragment.newInstance(character, characterId);
                tag = AttributesFragment.TAG;
                title = getString(R.string.title_fragment_attributes);
                break;
            case R.id.nav_abilities:
                fragment = AbilitiesFragment.newInstance(character, characterId);
                tag = AbilitiesFragment.TAG;
                title = getString(R.string.title_fragment_abilities);
                break;
            case R.id.nav_skills:
                fragment = SkillsFragment.newInstance(character, characterId);
                tag = SkillsFragment.TAG;
                title = getString(R.string.title_fragment_skills);
                break;
            case R.id.nav_attacks:
                fragment = AttacksFragment.newInstance(character, characterId);
                tag = AttacksFragment.TAG;
                title = getString(R.string.title_fragment_attacks);
                break;
            case R.id.nav_items:
                fragment = ItemsGeneralFragment.newInstance(character, characterId);
                tag = ItemsGeneralFragment.TAG;
                title = getString(R.string.title_fragment_items);
                break;
            case R.id.nav_armor:
                fragment = ArmorListFragment.newInstance(character, characterId);
                tag = ArmorListFragment.TAG;
                title = getString(R.string.title_fragment_armor);
                break;
            case R.id.nav_spells:
                fragment = SpellListFragment.newInstance(character, characterId);
                tag = SpellListFragment.TAG;
                title = getString(R.string.title_fragment_spells);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        mToolbar.setTitle(title);
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_character_nav, fragment, tag).commit();
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
        //this.tempArmor = armor;
        //this.tempArmorIndex = index;
        try {
            ArmorListFragment frag = (ArmorListFragment) getSupportFragmentManager().findFragmentByTag(ArmorListFragment.TAG);
            frag.retrieveArmor(armor, index);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DBAdapter getDbAdapter() {
        return dbAdapter;
    }
}
