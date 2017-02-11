package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.R;

/**
 * Fragment for showing & editing a single spell
 */
public class SpellFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public static final String TAG = "SPELL_FRAGMENT";

    public SpellFragment() {
        // Required empty public constructor
    }

    public static SpellFragment newInstance() {
        return new SpellFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_spell, container, false);
        try {
            if (((AppCompatActivity) this.getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) this.getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) this.getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            Log.e("NULL", "SupportBar null SpellFragment");
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        /*try {
            if (((AppCompatActivity) this.getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) this.getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                ((AppCompatActivity) this.getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            Log.e("NULL", "SupportBar null SpellFragment");
        }*/
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
