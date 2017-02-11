package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.SpellAdapter;

import java.util.ArrayList;

public class SpellListFragment extends Fragment implements View.OnClickListener, ConfirmationDialog.ConfirmationDialogInterface {

    //TODO Character should be passed in from CharacterNavDrawerActivity
    private RecyclerView mSpellRecyclerView;
    private RecyclerView.Adapter mSpellRecyclerAdapter;
    private RecyclerView.LayoutManager mSpellRecyclerLayoutManager;
    private ArrayList<Spell> spellList = new ArrayList<>();
    // TODO Get rid of test character
    private TestCharacter character;
    private FloatingActionButton fab;
    private OnFragmentInteractionListener mListener;

    public SpellListFragment() {
        // Required empty public constructor
    }

    public static SpellListFragment newInstance() {
        return new SpellListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        character = new TestCharacter();
        spellList = character.getSpells();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_spell_list, container, false);
        mSpellRecyclerView = (RecyclerView) rootView.findViewById(R.id.spellRecyclerView);

        // Keeps View same size on content change
        mSpellRecyclerView.setHasFixedSize(true);

        mSpellRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mSpellRecyclerView.setLayoutManager(mSpellRecyclerLayoutManager);

        mSpellRecyclerAdapter = new SpellAdapter(this, spellList);
        mSpellRecyclerView.setAdapter(mSpellRecyclerAdapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.addSpellFAB);
        fab.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addSpellFAB:

                break;
        }
    }

    @Override
    public void ConfirmDialogOk(Object spell) {
        if (spell instanceof Spell) {
            spellList.remove(spell);
            mSpellRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {

    }

    public void deleteSpell(Spell spell) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_spell), this, spell);
    }

    public void launchSpellFragment() {

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
}
