package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lavendergoons.dndcharacter.Activities.CharacterListActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.AddCharacterDialog;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Adapters.CharacterListAdapter;

import java.util.ArrayList;


public class CharacterListFragment extends Fragment implements
        AddCharacterDialog.OnCharacterCompleteListener,
        CharacterListAdapter.OnCharacterClickListener,
        View.OnClickListener,
        ConfirmationDialog.ConfirmationDialogInterface {

    private RecyclerView mCharacterRecyclerView;
    private CharacterListAdapter mCharRecyclerAdapter;
    private RecyclerView.LayoutManager mCharRecyclerLayoutManager;
    private ArrayList<SimpleCharacter> simpleCharacters = new ArrayList<>();
    private FloatingActionButton fab;
    private OnCharacterClickListener mListener;

    private DBAdapter dbAdapter;
    public static final String TAG = "CHARACTER_LIST_FRAG";

    private Gson gson;

    public CharacterListFragment() {
        // Required empty public constructor
    }

    public static CharacterListFragment newInstance() {
        CharacterListFragment fragment = new CharacterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_charcter_list, container, false);
        mCharacterRecyclerView = (RecyclerView) rootView.findViewById(R.id.characterListRecyclerView);

        // Keeps View same size on content change
        mCharacterRecyclerView.setHasFixedSize(true);

        mCharRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mCharacterRecyclerView.setLayoutManager(mCharRecyclerLayoutManager);

        mCharRecyclerAdapter = new CharacterListAdapter(this, simpleCharacters);
        mCharacterRecyclerView.setAdapter(mCharRecyclerAdapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.addCharacterFAB);
        fab.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            dbAdapter = ((CharacterListActivity) getActivity()).getDbAdapter();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        getCharacters();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addCharacterFAB:
                AddCharacterDialog.showAddCharacterDialog(this.getActivity(), this);
                break;
        }
    }

    private void getCharacters() {
        Cursor c = dbAdapter.getAllCharacterNames();
        simpleCharacters.clear();
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String json = c.getString(c.getColumnIndex(DBAdapter.COLUMN_CHARACTER));
                Log.d("JSON", "SimpleCharacter json string "+json);
                SimpleCharacter simpleCharacter = gson.fromJson(json, SimpleCharacter.class);
                simpleCharacters.add(simpleCharacter);
            }
            //mCharRecyclerAdapter.notifyDataSetChanged();
            c.close();
        }
    }

    private long getCharacterId(String name) {
        long id = -1;
        try {
            Cursor c = dbAdapter.getCharacterId();
            if (c != null) {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    String json = c.getString(c.getColumnIndex(DBAdapter.COLUMN_CHARACTER));
                    SimpleCharacter simpleCharacter = gson.fromJson(json, SimpleCharacter.class);
                    if (simpleCharacter.getName().equals(name)) {
                        id = (long) c.getInt(c.getColumnIndex(DBAdapter.COLUMN_ID));
                        break;
                    }
                }
            }
            c.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    private SimpleCharacter getCharacterFromName(String name) {
        for(SimpleCharacter c : simpleCharacters) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    @Override
    public void ConfirmDialogOk(Object o) {
        if (o instanceof SimpleCharacter) {
            int i = simpleCharacters.indexOf(o);
            dbAdapter.deleteRow(getCharacterId(simpleCharacters.get(i).getName()));
            simpleCharacters.remove(i);
            mCharRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {

    }

    @Override
    public void onCharacterComplete(SimpleCharacter simpleCharacter) {
        String characterJson = gson.toJson(simpleCharacter);
        dbAdapter.insertRow(characterJson);
        simpleCharacters.add(simpleCharacter);
        mCharRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCharacterClick(String name) {
        mListener = (OnCharacterClickListener) getActivity();
        mListener.onFragmentCharacterClick(getCharacterFromName(name), getCharacterId(name));
    }

    public void deleteCharacter(SimpleCharacter simpleCharacter) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_character), this, simpleCharacter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCharacterClickListener) {
            mListener = (OnCharacterClickListener) context;
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

    public interface OnCharacterClickListener {
        void onFragmentCharacterClick(SimpleCharacter name, long id);
    }
}
