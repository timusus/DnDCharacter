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
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Dialogs.ArmorDialog;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.ArmorAdapter;

import java.util.ArrayList;

/**
 * Fragment to hold armor recycler view
 */
public class ArmorFragment extends Fragment implements ArmorDialog.OnArmorAction, View.OnClickListener {

    //TODO Character should be passed in from CharacterNavDrawerActivity
    private RecyclerView mArmorRecyclerView;
    private RecyclerView.Adapter mArmorRecyclerAdapter;
    private RecyclerView.LayoutManager mArmorRecyclerLayoutManager;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Armor> armor;
    private TestCharacter character;
    private FloatingActionButton fab;

    public ArmorFragment() {
        // Required empty public constructor
    }

    public static ArmorFragment newInstance() {
        ArmorFragment fragment = new ArmorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Get rid of test character
        character = new TestCharacter();
        armor = character.getArmor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_armor, container, false);
        mArmorRecyclerView = (RecyclerView) rootView.findViewById(R.id.armorRecyclerView);

        // Keeps View same size on content change
        mArmorRecyclerView.setHasFixedSize(true);

        mArmorRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mArmorRecyclerView.setLayoutManager(mArmorRecyclerLayoutManager);

        mArmorRecyclerAdapter = new ArmorAdapter(this, armor);
        mArmorRecyclerView.setAdapter(mArmorRecyclerAdapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.addArmorFAB);
        fab.setOnClickListener(this);
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
    public void onClick(View view) {
        ArmorDialog.showArmorDialog(this.getActivity(), this, null);
    }

    @Override
    public void OnArmorPositive(Armor armor) {
        //TODO add armor from dialog to armor list
        //Toast.makeText(this.getContext(), "OK", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnArmorNegative() {
        Toast.makeText(this.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }
}
