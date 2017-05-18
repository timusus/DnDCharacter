package com.lavendergoons.dndcharacter.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Adapters.CompanionAdapter;
import com.lavendergoons.dndcharacter.Objects.Companion;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.util.ArrayList;

public class CompanionsListFragment extends Fragment implements View.OnClickListener, CompanionAdapter.CompanionAdapterListener {

    public static final String TAG = "COMPANION_LIST_FRAG";

    private RecyclerView mCompanionRecyclerView;
    private RecyclerView.Adapter mCompanionRecyclerAdapter;
    private RecyclerView.LayoutManager mCompanionRecyclerLayoutManager;
    private CharacterManager characterManager;

    private FloatingActionButton addCompanionFAB;

    private ArrayList<Companion> companionList = new ArrayList<>();
    private SimpleCharacter simpleCharacter;
    private long characterId = -1;

    public CompanionsListFragment() {
        // Required empty public constructor
    }

    public static CompanionsListFragment newInstance(SimpleCharacter simpleCharacter, long characterId) {
        CompanionsListFragment fragment = new CompanionsListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, simpleCharacter);
        args.putLong(Constants.CHARACTER_ID, characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            characterId = getArguments().getLong(Constants.CHARACTER_ID);
            simpleCharacter = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        characterManager = CharacterManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_companions_list, container, false);
        mCompanionRecyclerView = (RecyclerView) rootView.findViewById(R.id.companionRecyclerView);

        // Keeps View same size on content change
        mCompanionRecyclerView.setHasFixedSize(true);

        mCompanionRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mCompanionRecyclerView.setLayoutManager(mCompanionRecyclerLayoutManager);

        mCompanionRecyclerAdapter = new CompanionAdapter(this, companionList);
        mCompanionRecyclerView.setAdapter(mCompanionRecyclerAdapter);

        addCompanionFAB = (FloatingActionButton) rootView.findViewById(R.id.addCompanionFAB);
        addCompanionFAB.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        new CompanionDialog().showDialog();
    }

    private int addCompanion(Companion companion) {
        int index = -1;
        if (companion != null) {
            companionList.add(companion);
            index = companionList.indexOf(companion);
            mCompanionRecyclerAdapter.notifyDataSetChanged();
        }
        return index;
    }

    @Override
    public void removeCompanion(Companion companion) {

    }

    private class CompanionDialog {
        public void showDialog() {
            final CompanionsListFragment fragment = CompanionsListFragment.this;

            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
            LinearLayout dialogLayout = new LinearLayout(fragment.getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final EditText companionNameEdit;

            dialogLayout.setOrientation(LinearLayout.VERTICAL);
            dialogLayout.setLayoutParams(params);
            dialogLayout.setPadding(2, 2, 2, 2);

            companionNameEdit = new EditText(fragment.getActivity());
            companionNameEdit.setHint(R.string.hint_name);
            companionNameEdit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            dialogLayout.addView(companionNameEdit, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            builder.setTitle(fragment.getString(R.string.title_companion_dialog));
            builder.setView(dialogLayout);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String name = companionNameEdit.getText().toString();
                    if (!Utils.isStringEmpty(name)) {
                        Companion companion = new Companion(name);
                        int index = fragment.addCompanion(companion);
                        FragmentTransaction fragTransaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
                        fragTransaction.replace(R.id.content_character_nav, CompanionFragment.newInstance(companion, index), CompanionFragment.TAG).addToBackStack(CompanionFragment.TAG).commit();
                    } else {
                        Toast.makeText(fragment.getContext(), getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                    }
                }
            }).setNegativeButton(R.string.cancel, null).create().show();
        }
    }
}
