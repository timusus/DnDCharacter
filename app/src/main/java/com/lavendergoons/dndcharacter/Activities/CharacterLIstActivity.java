package com.lavendergoons.dndcharacter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterListAdapter;

import java.util.ArrayList;

public class CharacterLIstActivity extends AppCompatActivity {

    private RecyclerView mCharacterRecyclerView;
    private RecyclerView.Adapter mCharRecyclerAdapter;
    private RecyclerView.LayoutManager mCharRecyclerLayoutManager;
    private ArrayList<Character> characters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        characters.add(new Character("Silian Mord", 1));
        characters.add(new Character("Montgomery", 15));
        characters.add(new Character("Darth Vader", 13));
        characters.add(new Character("Boba Fett", 4));
        createView();
    }

    private void createView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCharacterRecyclerView = (RecyclerView) findViewById(R.id.characterListRecyclerView);

        // Keeps View same size on content change
        mCharacterRecyclerView.setHasFixedSize(true);

        mCharRecyclerLayoutManager = new LinearLayoutManager(this);
        mCharacterRecyclerView.setLayoutManager(mCharRecyclerLayoutManager);

        mCharRecyclerAdapter = new CharacterListAdapter(characters);
        mCharacterRecyclerView.setAdapter(mCharRecyclerAdapter);
    }
}
