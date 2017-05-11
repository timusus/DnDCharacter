package com.lavendergoons.dndcharacter.Fragments;

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
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Adapters.AttackAdapter;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;

public class AttacksFragment extends Fragment
        implements View.OnClickListener, AttackDialog.AttackDialogListener, AttackAdapter.AttackAdapterListener, ConfirmationDialog.ConfirmationDialogInterface {

    public static final String TAG = "ATTACKS_FRAG";

    private RecyclerView mAttacksRecyclerView;
    private RecyclerView.Adapter mAttacksRecyclerAdapter;
    private RecyclerView.LayoutManager mAttacksRecyclerLayoutManager;

    private CharacterManager characterManager;
    private SimpleCharacter simpleCharacter;
    private long characterId = -1;

    private ArrayList<Attack> attackList = new ArrayList<>();
    private FloatingActionButton addAttackFAB;

    public AttacksFragment() {
        // Required empty public constructor
    }

    public static AttacksFragment newInstance(SimpleCharacter simpleCharacter, long id) {
        AttacksFragment frag = new AttacksFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, simpleCharacter);
        args.putLong(Constants.CHARACTER_ID, id);
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
        characterManager = CharacterManager.getInstance(this.getContext());
        attackList = characterManager.getCharacterAttacks();
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

        addAttackFAB = (FloatingActionButton) rootView.findViewById(R.id.addAttackFAB);
        addAttackFAB.setOnClickListener(this);
        return rootView;
    }

    private void writeAttacks() {
        characterManager.setCharacterAttacks(attackList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addAttackFAB:
                //AttackDialog.showAttackDialog(this.getActivity(), this, null);
                new AttackDialog(this, null).showDialog();
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
    public void OnAttackDialogNegative() {}

    @Override
    public void ConfirmDialogOk(Object attack) {
        if (attack instanceof Attack) {
            attackList.remove(attack);
            mAttacksRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {}

    @Override
    public void removeAttack(Attack attack) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_attack), this, attack);
    }

    @Override
    public void onStop() {
        writeAttacks();
        super.onStop();
    }
}
