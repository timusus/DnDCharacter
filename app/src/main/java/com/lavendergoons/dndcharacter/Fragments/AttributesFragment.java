package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Objects.Attribute;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.AttributesAdapter;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AttributesFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private RecyclerView mAttributesRecyclerView;
    private AttributesAdapter mAttributeRecyclerAdapter;
    private RecyclerView.LayoutManager mAttributeLayoutManager;
    //private ArrayList<Attribute> attributesList;
    private ArrayList<String> attributesList;
    //TODO Get Rid of TestCharacter
    private TestCharacter testCharacter;
    private Character character;
    public static final String TAG = "ATTRIBUTES_FRAG";
    private long id;
    private DBAdapter dbAdapter;
    private Gson gson = new Gson();

    public AttributesFragment() {
        // Required empty public constructor
    }

    public static AttributesFragment newInstance(Character charIn, long characterId) {
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
            id = getArguments().getLong(Constants.CHARACTER_ID);
            character = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        // TODO Get rid of test testCharacter
        attributesList = new ArrayList<>(Constants.ATTRIBUTES.length);
        for (int i=0;i<Constants.ATTRIBUTES.length;i++) {
            attributesList.add(i, "");
        }
        getAttributes();
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

        mAttributeRecyclerAdapter = new AttributesAdapter(this, attributesList);
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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        writeAttibutes();
        super.onDestroy();
    }

    private void writeAttibutes() {
        attributesList = mAttributeRecyclerAdapter.getAttributeList();
        String json = gson.toJson(attributesList);
        dbAdapter.fillColumn(id, DBAdapter.COLUMN_ATTRIBUTES, json);
    }

    private void getAttributes() {
        if (dbAdapter != null) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ATTRIBUTES, id);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ATTRIBUTES));
                if (json != null && !Utils.isStringEmpty(json)) {
                    Type attributeType = new TypeToken<ArrayList<String>>(){}.getType();
                    attributesList = gson.fromJson(json, attributeType);
                }
            }
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
