package com.lavendergoons.dndcharacter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Adapters.SkillsAdapter;

import java.util.ArrayList;

public class SkillsFragment extends Fragment implements SkillsAdapter.SkillAdapterListener {

    private RecyclerView mSkillsRecyclerView;
    private SkillsAdapter mSkillRecyclerAdapter;
    private RecyclerView.LayoutManager mSkillRecyclerLayoutManager;
    private ArrayList<Skill> skillsList = new ArrayList<>();
    private CharacterManager characterManager;

    public static final String TAG = "SKILLS_FRAG";
    private long characterId = -1;
    private SimpleCharacter simpleCharacter;
    private Abilities abilities;

    public SkillsFragment() {
        // Required empty public constructor
    }

    public static SkillsFragment newInstance(SimpleCharacter charIn, long characterId) {
        SkillsFragment frag = new SkillsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, charIn);
        args.putLong(Constants.CHARACTER_ID, characterId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            characterId = getArguments().getLong(Constants.CHARACTER_ID);
            simpleCharacter = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        characterManager = CharacterManager.getInstance();
        skillsList = characterManager.getCharacterSkills();
        abilities = characterManager.getCharacterAbilities();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_skills, container, false);
        mSkillsRecyclerView = (RecyclerView) rootView.findViewById(R.id.skillsRecyclerView);

        // Keeps View same size on content change
        mSkillsRecyclerView.setHasFixedSize(true);

        mSkillRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mSkillsRecyclerView.setLayoutManager(mSkillRecyclerLayoutManager);

        mSkillRecyclerAdapter = new SkillsAdapter(this, skillsList, abilities);
        mSkillsRecyclerView.setAdapter(mSkillRecyclerAdapter);

        return rootView;
    }


    @Override
    public void onStop() {
        writeSkills();
        super.onStop();
    }

    private void writeSkills() {
        skillsList = mSkillRecyclerAdapter.getSkillList();
        characterManager.setCharacterSkills(skillsList);
    }

}
