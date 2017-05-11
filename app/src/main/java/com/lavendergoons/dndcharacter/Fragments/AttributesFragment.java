package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Adapters.AttributesAdapter;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.util.ArrayList;


public class AttributesFragment extends Fragment implements AttributesAdapter.AttributesAdapterListener{

    public static final String TAG = "ATTRIBUTES_FRAG";

    private RecyclerView mAttributesRecyclerView;
    private AttributesAdapter mAttributeRecyclerAdapter;
    private RecyclerView.LayoutManager mAttributeLayoutManager;
    private ArrayList<String> attributesList = new ArrayList<>(Constants.ATTRIBUTES.length);
    private SimpleCharacter simpleCharacter;
    private CharacterManager characterManager;

    private long characterId = -1;
    private final int NAME = 0;
    private final int LEVEL = 2;

    public AttributesFragment() {
        // Required empty public constructor
    }

    public static AttributesFragment newInstance(SimpleCharacter charIn, long characterId) {
        AttributesFragment frag = new AttributesFragment();
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
        characterManager = CharacterManager.getInstance();
        attributesList = characterManager.getCharacterAttributes();

        // Fill attribute list with empty data
        if (attributesList.size() == 0) {
            for (int i=0;i<Constants.ATTRIBUTES.length;i++) {
                attributesList.add(i, "");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_attributes, container, false);
        mAttributesRecyclerView = (RecyclerView) rootView.findViewById(R.id.attributesRecyclerView);

        // Keeps View same size on content change
        mAttributesRecyclerView.setHasFixedSize(true);

        mAttributeLayoutManager = new GridLayoutManager(this.getActivity(), Constants.ATTRIBUTES_GRID_SPAN);
        mAttributesRecyclerView.setLayoutManager(mAttributeLayoutManager);

        mAttributeRecyclerAdapter = new AttributesAdapter(this, attributesList, simpleCharacter);
        mAttributesRecyclerView.setAdapter(mAttributeRecyclerAdapter);
        return rootView;
    }

    @Override
    public void onStop() {
        writeAttributes();
        super.onStop();
    }

    private void writeAttributes() {
        attributesList = mAttributeRecyclerAdapter.getAttributeList();
        if (!Utils.isStringEmpty(attributesList.get(NAME))) {
            simpleCharacter.setName(attributesList.get(NAME));
        }
        if (!Utils.isStringEmpty(String.valueOf(attributesList.get(LEVEL)))) {
            int lvl = 0;
            try {
                lvl = Integer.parseInt(attributesList.get(LEVEL));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            simpleCharacter.setLevel(lvl);
        }
        characterManager.setSimpleCharacter(simpleCharacter);
        characterManager.setCharacterAttributes(attributesList);
    }
}
