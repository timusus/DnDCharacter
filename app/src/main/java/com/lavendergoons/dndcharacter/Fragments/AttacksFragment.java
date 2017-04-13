package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.lavendergoons.dndcharacter.Dialogs.AttackDialog;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Attack;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Adapters.AttackAdapter;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AttacksFragment extends Fragment implements View.OnClickListener, AttackDialog.AttackDialogListener, ConfirmationDialog.ConfirmationDialogInterface {

    public static final String TAG = "ATTACKS_FRAG";

    private Gson gson = new Gson();

    private RecyclerView mAttacksRecyclerView;
    private RecyclerView.Adapter mAttacksRecyclerAdapter;
    private RecyclerView.LayoutManager mAttacksRecyclerLayoutManager;
    private OnFragmentInteractionListener mListener;
    private DBAdapter dbAdapter;
    private Character character;
    private long characterId = -1;

    private ArrayList<Attack> attackList = new ArrayList<>();
    private FloatingActionButton fab;

    public AttacksFragment() {
        // Required empty public constructor
    }

    public static AttacksFragment newInstance(Character character, long id) {
        AttacksFragment frag = new AttacksFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, character);
        args.putLong(Constants.CHARACTER_ID, id);
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
        getAttacks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_attacks, container, false);
        mAttacksRecyclerView = (RecyclerView) rootView.findViewById(R.id.attacksRecyclerView);

        // Keeps View same size on content change
        mAttacksRecyclerView.setHasFixedSize(true);

        mAttacksRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mAttacksRecyclerView.setLayoutManager(mAttacksRecyclerLayoutManager);

        mAttacksRecyclerAdapter = new AttackAdapter(this, attackList);
        mAttacksRecyclerView.setAdapter(mAttacksRecyclerAdapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.addAttackFAB);
        fab.setOnClickListener(this);
        return rootView;
    }

    private void getAttacks() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ATTACK, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ATTACK));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Attack>>(){}.getType();
                    attackList = gson.fromJson(json, attributeType);
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    private void writeAttacks() {
        String json = gson.toJson(attackList);
        if (dbAdapter != null) {
            dbAdapter.fillColumn(characterId, DBAdapter.COLUMN_ATTACK, json);
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addAttackFAB:
                AttackDialog.showAttackDialog(this.getActivity(), this, null);
                break;
        }
    }

    @Override
    public void OnAttackDialogPositive(Attack attack) {
        if (attack != null) {
            attackList.add(attack);
        }
        mAttacksRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnAttackDialogNegative() {

    }

    @Override
    public void ConfirmDialogOk(Object attack) {
        if (attack instanceof Attack) {
            attackList.remove(attack);
            mAttacksRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {

    }

    public void deleteAttack(Attack attack) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_attack), this, attack);
    }

    @Override
    public void onDestroy() {
        writeAttacks();
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
        void onFragmentInteraction();
    }
}
