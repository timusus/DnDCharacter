package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
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
    private long id = -1;
    private Character character;
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
            id = getArguments().getLong(Constants.CHARACTER_ID);
            character = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        initSkills();
        getSkills();
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

        mSkillRecyclerAdapter = new SkillsAdapter(this, skillsList);
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
        // TODO: Update argument type and name
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
        dbAdapter.fillColumn(id, DBAdapter.COLUMN_SKILL, json);
    }

    private void getSkills() {
        if (dbAdapter != null && id != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_SKILL, id);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_SKILL));
                if (json != null && !Utils.isStringEmpty(json)) {
                    Type skillType = new TypeToken<ArrayList<Skill>>(){}.getType();
                    skillsList = gson.fromJson(json, skillType);
                    cursor.close();
                }
            }
        }  else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }
}
