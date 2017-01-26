package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.SkillsAdapter;

import java.util.ArrayList;


public class AttributesFragment extends Fragment {

    //TODO Character should be passed in from CharacterNavDrawerActivity
    private OnFragmentInteractionListener mListener;
    private RecyclerView mAttributesRecyclerView;
    private RecyclerView.Adapter mAttributeRecyclerAdapter;
    private RecyclerView.LayoutManager mAttributeLayoutManager;
    //TODO Change to Attribute object
    private ArrayList<String> attributes;
    //TODO Get Rid of TestCharacter
    private TestCharacter character;

    public AttributesFragment() {
        // Required empty public constructor
    }

    public static AttributesFragment newInstance() {
        AttributesFragment fragment = new AttributesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_attributes, container, false);
        mAttributesRecyclerView = (RecyclerView) rootView.findViewById(R.id.attributesRecyclerView);

        // Keeps View same size on content change
        mAttributesRecyclerView.setHasFixedSize(true);

        mAttributeLayoutManager = new LinearLayoutManager(this.getContext());
        mAttributesRecyclerView.setLayoutManager(mAttributeLayoutManager);

        //mAttributeRecyclerAdapter = new SkillsAdapter(this, attributes);
        mAttributesRecyclerView.setAdapter(mAttributeRecyclerAdapter);
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
}
