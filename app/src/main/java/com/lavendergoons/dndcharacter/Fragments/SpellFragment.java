package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;

import java.util.ArrayList;


/**
 * Fragment for showing & editing a single spell
 */
public class SpellFragment extends Fragment {

    private EditText spellNameEdit, spellLevelEdit, spellTypeEdit, spellComponentEdit, spellCastTimeEdit, spellRangeEdit, spellAreaEdit, spellDurationEdit, spellSavingThrowEdit, spellNotesEdit;
    private Switch spellResSwitch;
    private OnFragmentInteractionListener mListener;
    private CharacterManager characterManager;
    private ArrayList<Spell> spellList = new ArrayList<>();
    private Spell spell;
    private int index = -1;
    public static final String TAG = "SPELL_FRAG";

    public SpellFragment() {
        // Required empty public constructor
    }

    public static SpellFragment newInstance(Spell spell, int index) {
        SpellFragment frag = new SpellFragment();
        if (spell == null) {
            spell = new Spell();
        }
        Bundle args = new Bundle();
        args.putParcelable("SPELL", spell);
        args.putInt("INDEX", index);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            spell = getArguments() != null ? (Spell) getArguments().getParcelable("SPELL") : new Spell();
            index = getArguments().getInt("INDEX");
        }catch(BadParcelableException ex) {
            Log.e("PARSE", "Bad Parcelable in SpellFragment");
        }
        characterManager = CharacterManager.getInstance(this.getContext());
        spellList = characterManager.getCharacterSpells();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_spell, container, false);
        spellNameEdit = (EditText) rootView.findViewById(R.id.spellNameEdit);
        spellLevelEdit = (EditText) rootView.findViewById(R.id.spellLevelEdit);
        spellTypeEdit = (EditText) rootView.findViewById(R.id.spellTypeEdit);
        spellComponentEdit = (EditText) rootView.findViewById(R.id.spellComponentEdit);
        spellCastTimeEdit = (EditText) rootView.findViewById(R.id.spellCastTimeEdit);
        spellRangeEdit = (EditText) rootView.findViewById(R.id.spellRangeEdit);
        spellAreaEdit = (EditText) rootView.findViewById(R.id.spellAreaEdit);
        spellDurationEdit = (EditText) rootView.findViewById(R.id.spellDurationEdit);
        spellSavingThrowEdit = (EditText) rootView.findViewById(R.id.spellSavingThrowEdit);
        spellNotesEdit = (EditText) rootView.findViewById(R.id.spellNotesEdit);

        spellResSwitch = (Switch) rootView.findViewById(R.id.spellResSwitch);
        getValues();
        return rootView;
    }

    private void getValues() {
        try {
            spellNameEdit.setText(spell.getName());
            spellLevelEdit.setText(String.valueOf(spell.getLevel()));
            spellTypeEdit.setText(spell.getType());
            spellComponentEdit.setText(spell.getComponent());
            spellCastTimeEdit.setText(spell.getCastTime());
            spellRangeEdit.setText(spell.getRange());
            spellAreaEdit.setText(spell.getArea());
            spellDurationEdit.setText(spell.getDuration());
            spellSavingThrowEdit.setText(spell.getSavingThrow());
            spellNotesEdit.setText(spell.getNotes());
            spellResSwitch.setChecked(spell.isSpellRes());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setValues() {
        try {
            spell.setName(spellNameEdit.getText().toString());
            spell.setLevel(Integer.valueOf(spellLevelEdit.getText().toString()));
            spell.setType(spellTypeEdit.getText().toString());
            spell.setComponent(spellComponentEdit.getText().toString());
            spell.setCastTime(spellCastTimeEdit.getText().toString());
            spell.setRange(spellRangeEdit.getText().toString());
            spell.setArea(spellAreaEdit.getText().toString());
            spell.setDuration(spellDurationEdit.getText().toString());
            spell.setSavingThrow(spellSavingThrowEdit.getText().toString());
            spell.setNotes(spellNotesEdit.getText().toString());
            spell.setSpellRes(spellResSwitch.isChecked());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        setValues();
        spellList.set(index, spell);
        characterManager.setCharacterSpells(spellList);
        super.onStop();
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

    }
}
