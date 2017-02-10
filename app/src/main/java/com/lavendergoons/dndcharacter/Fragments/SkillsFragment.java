package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.SkillsAdapter;

import java.util.ArrayList;

public class SkillsFragment extends Fragment implements ConfirmationDialog.ConfirmationDialogInterface {

    //TODO Character should be passed in from CharacterNavDrawerActivity
    private OnFragmentInteractionListener mListener;
    private RecyclerView mSkillsRecyclerView;
    private RecyclerView.Adapter mSkillRecyclerAdapter;
    private RecyclerView.LayoutManager mSkillRecyclerLayoutManager;
    private ArrayList<Skill> skillsList;
    // TODO Get rid of test character
    private TestCharacter character;

    public SkillsFragment() {
        // Required empty public constructor
    }

    public static SkillsFragment newInstance(/*Character*/) {
        return new SkillsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Get rid of test character
        skillsList = new ArrayList<>();
        character = new TestCharacter();
        skillsList = character.getSkills();
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
}
