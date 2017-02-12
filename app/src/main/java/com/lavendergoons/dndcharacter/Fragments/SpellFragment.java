package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;

/**
 * Fragment for showing & editing a single spell
 */
public class SpellFragment extends Fragment {

    private TextView spellName;
    private OnFragmentInteractionListener mListener;
    private Spell spell;
    public static final String TAG = "SPELL_FRAG";

    public SpellFragment() {
        // Required empty public constructor
    }

    public static SpellFragment newInstance(Spell spell) {
        SpellFragment frag = new SpellFragment();
        Bundle args = new Bundle();
        args.putParcelable("SPELL", spell);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            spell = getArguments() != null ? (Spell) getArguments().getParcelable("SPELL") : new Spell();
        }catch(BadParcelableException ex) {
            Log.e("PARSE", "Bad Parcelable in SpellFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_spell, container, false);
        spellName = (TextView) rootView.findViewById(R.id.spellNameVIew);
        spellName.setText(spell.getName());
        return rootView;
    }

    @Override
    public void onStop() {
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
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
