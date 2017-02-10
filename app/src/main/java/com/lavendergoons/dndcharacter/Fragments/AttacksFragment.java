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

import com.lavendergoons.dndcharacter.Dialogs.AttackDialog;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Attack;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.AttackAdapter;

import java.util.ArrayList;

public class AttacksFragment extends Fragment implements View.OnClickListener, AttackDialog.AttackDialogListener, ConfirmationDialog.ConfirmationDialogInterface {

    //TODO Character should be passed in from CharacterNavDrawerActivity
    private RecyclerView mAttacksRecyclerView;
    private RecyclerView.Adapter mAttacksRecyclerAdapter;
    private RecyclerView.LayoutManager mAttacksRecyclerLayoutManager;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Attack> attackList;
    private TestCharacter character;
    private FloatingActionButton fab;

    public AttacksFragment() {
        // Required empty public constructor
    }

    public static AttacksFragment newInstance() {
        return new AttacksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attackList = new ArrayList<>();
        character = new TestCharacter();
        attackList = character.getAttacks();
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
            mAttacksRecyclerAdapter.notifyDataSetChanged();
        }
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
