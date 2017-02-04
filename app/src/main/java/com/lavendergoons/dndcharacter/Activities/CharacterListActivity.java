package com.lavendergoons.dndcharacter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Dialogs.AddCharacterDialog;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterListAdapter;

import java.util.ArrayList;

/**
  * Initial Activity to hold list of characters
  * Add and delete characters.
  * Selecting Character will launch CharacterNavDrawerActivity,
  * to show all Character info.
 */

public class CharacterListActivity extends AppCompatActivity
        implements AddCharacterDialog.OnCharacterCompleteListener,
        CharacterListAdapter.OnCharacterClickListener,
        ConfirmationDialog.ConfirmationDialogInterface {

    private RecyclerView mCharacterRecyclerView;
    private RecyclerView.Adapter mCharRecyclerAdapter;
    private RecyclerView.LayoutManager mCharRecyclerLayoutManager;
    private ArrayList<Character> characters;
    private AddCharacterDialog addCharacterDialog;
    private Button addCharacterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        characters = new ArrayList<Character>();
        characters.add(new Character("Silian Mord", 1));
        characters.add(new Character("Montgomery", 15));
        characters.add(new Character("Darth Vader", 13));
        characters.add(new Character("Boba Fett", 4));
        createView();
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

        addCharacterBtn = (Button) findViewById(R.id.addCharacterBtn);
        addCharacterBtn.setOnClickListener(addCharacterClick);
    }

    private View.OnClickListener addCharacterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addCharacterDialog = new AddCharacterDialog();
            addCharacterDialog.show(getSupportFragmentManager(), getString(R.string.tag_add_character_dialog));
        }
    };

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
        characters.add(character);
        mCharRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCharacterClick(String name) {
        //TODO Clean up intent messages
        Intent intent = new Intent(this, CharacterNavDrawerActivity.class);
        intent.putExtra("CHARACTER_NAME", name);
        startActivity(intent);
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
