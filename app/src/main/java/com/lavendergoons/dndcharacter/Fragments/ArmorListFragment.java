package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ArmorDialog;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.ArmorAdapter;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Fragment to hold armor recycler view
 */
public class ArmorListFragment extends Fragment implements ArmorDialog.ArmorDialogListener, View.OnClickListener, ConfirmationDialog.ConfirmationDialogInterface {

    public static final String TAG = "ARMOR_LIST_FRAG";

    private Gson gson = new Gson();

    private RecyclerView mArmorRecyclerView;
    private RecyclerView.Adapter mArmorRecyclerAdapter;
    private RecyclerView.LayoutManager mArmorRecyclerLayoutManager;
    private OnFragmentInteractionListener mListener;

    private ArrayList<Armor> armorList = new ArrayList<>();
    private DBAdapter dbAdapter;
    private Character character;
    private long characterId = -1;

    private TestCharacter testCharacter;
    private FloatingActionButton fab;

    public ArmorListFragment() {
        // Required empty public constructor
    }

    public static ArmorListFragment newInstance(Character character, long characterId) {
        ArmorListFragment frag = new ArmorListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, character);
        args.putLong(Constants.CHARACTER_ID, characterId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            characterId = getArguments().getLong(Constants.CHARACTER_ID);
            character = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        getArmor();
        try {
            Object[] array = ((CharacterNavDrawerActivity) getActivity()).retrieveArmor();
            Armor armor = (Armor) array[0];
            int index = (Integer) array[1];
            if (armor != null && index != -1) {
                armorList.add(armor);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        //TODO Get rid of test testCharacter
        //testCharacter = new TestCharacter();
        //armorList = testCharacter.getArmor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_armor_list, container, false);
        mArmorRecyclerView = (RecyclerView) rootView.findViewById(R.id.armorRecyclerView);

        // Keeps View same size on content change
        mArmorRecyclerView.setHasFixedSize(true);

        mArmorRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mArmorRecyclerView.setLayoutManager(mArmorRecyclerLayoutManager);

        mArmorRecyclerAdapter = new ArmorAdapter(this, armorList);
        mArmorRecyclerView.setAdapter(mArmorRecyclerAdapter);
        mArmorRecyclerAdapter.notifyDataSetChanged();

        fab = (FloatingActionButton) rootView.findViewById(R.id.addArmorFAB);
        fab.setOnClickListener(this);
        return rootView;
    }

    private void getArmor() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ARMOR, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ARMOR));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Armor>>(){}.getType();
                    armorList = gson.fromJson(json, attributeType);
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }

    }

    private void writeArmor() {
        String json = gson.toJson(armorList);
        if (dbAdapter != null) {
            dbAdapter.fillColumn(characterId, DBAdapter.COLUMN_ARMOR, json);
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    public void retrieveArmor(Armor armor, int index) {
        if (armor != null) {
            armorList.set(index, armor);
        }
        mArmorRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        writeArmor();
        super.onDestroy();
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
        Object[] retrieveArmor();
    }

    @Override
    public void onClick(View view) {
        ArmorDialog.showSimpleArmorDialog(this.getActivity(), this);
    }

    private void launchArmorFragment(Armor armor, int i) {
        FragmentTransaction fragTransaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_character_nav, ArmorFragment.newInstance(armor, i), ArmorFragment.TAG).addToBackStack(ArmorFragment.TAG).commit();
    }

    @Override
    public void OnArmorPositive(Armor armor) {
        int i = -1;
        if (armor != null) {
            armorList.add(armor);
            i = armorList.indexOf(armor);
        }
        mArmorRecyclerAdapter.notifyDataSetChanged();
        launchArmorFragment(armor, i);
    }

    @Override
    public void OnArmorNegative() {}

    public void deleteArmor(Armor armor) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_armor), this, armor);
    }

    // This may be sketchy
    @Override
    public void ConfirmDialogOk(Object armor) {
        if (armor instanceof Armor) {
            armorList.remove(armor);
            mArmorRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object armor) {}


}
