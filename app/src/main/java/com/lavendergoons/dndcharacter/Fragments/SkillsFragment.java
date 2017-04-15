package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Adapters.SkillsAdapter;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SkillsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mSkillsRecyclerView;
    private SkillsAdapter mSkillRecyclerAdapter;
    private RecyclerView.LayoutManager mSkillRecyclerLayoutManager;
    private ArrayList<Skill> skillsList = new ArrayList<>();
    private CharacterManager characterManager;
    private Gson gson = new Gson();

    public static final String TAG = "SKILLS_FRAG";
    private long characterId = -1;
    private SimpleCharacter simpleCharacter;
    private Abilities abilities;
    private DBAdapter dbAdapter;

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
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
            FirebaseCrash.log("Is DBAdapter Null?"+(dbAdapter == null));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        characterManager = CharacterManager.getInstance(this.getContext());
        skillsList = characterManager.getCharacterSkills();
        abilities = characterManager.getCharacterAbilities();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getSkills();
        //readAbilityValues();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStop() {
        writeSkills();
        super.onStop();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    private void writeSkills() {
        skillsList = mSkillRecyclerAdapter.getSkillList();
        characterManager.setCharacterSkills(skillsList);
    }

}
