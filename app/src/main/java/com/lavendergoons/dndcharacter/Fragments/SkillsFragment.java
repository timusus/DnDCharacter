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
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.SkillsAdapter;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SkillsFragment extends Fragment implements ConfirmationDialog.ConfirmationDialogInterface {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mSkillsRecyclerView;
    private SkillsAdapter mSkillRecyclerAdapter;
    private RecyclerView.LayoutManager mSkillRecyclerLayoutManager;
    private ArrayList<Skill> skillsList = new ArrayList<>();
    private Gson gson = new Gson();

    public static final String TAG = "SKILLS_FRAG";
    private long characterId = -1;
    private Character character;
    private Abilities abilities;
    private DBAdapter dbAdapter;

    public SkillsFragment() {
        // Required empty public constructor
    }

    public static SkillsFragment newInstance(Character charIn, long characterId) {
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
            character = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
            FirebaseCrash.log("Is DBAdapter Null?"+(dbAdapter == null));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        getSkills();
        readAbilityValues();
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
    public void onDestroy() {
        writeSkills();
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    @Override
    public void ConfirmDialogOk(Object o) {
        Toast.makeText(this.getContext(), "SkillsFragment Confirm", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ConfirmDialogCancel(Object o) {
        Toast.makeText(this.getContext(), "SkillsFragment Cancel", Toast.LENGTH_SHORT).show();
    }

    private void initSkills() {
        for (Constants.Skills s : Constants.Skills.values()) {
            skillsList.add(new Skill(s.getName(), s.getMod(), s.getDefault(), 0, 0, 0, 0));
        }
    }

    private void writeSkills() {
        skillsList = mSkillRecyclerAdapter.getSkillList();
        String json = gson.toJson(skillsList);
        FirebaseCrash.log("Is DBAdapter Null?"+(dbAdapter == null));
        try {
            dbAdapter.fillColumn(characterId, DBAdapter.COLUMN_SKILL, json);
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.report(ex);
        }

    }

    private void getSkills() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_SKILL, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_SKILL));
                if (json != null && !Utils.isStringEmpty(json)) {
                    Type skillType = new TypeToken<ArrayList<Skill>>(){}.getType();
                    skillsList = gson.fromJson(json, skillType);
                    FirebaseCrash.log("Skills from JSON");
                    cursor.close();
                } else {
                    initSkills();
                }
            }
        }  else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    // Read ability object from database
    private void readAbilityValues() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ABILITIES, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ABILITIES));
                if (json != null && !Utils.isStringEmpty(json)) {
                    abilities = gson.fromJson(json, Abilities.class);
                    FirebaseCrash.log("Abilities from JSON");
                } else {
                    abilities = new Abilities();
                    FirebaseCrash.log("New Abilities Object");
                }
                cursor.close();
            }
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }
}
