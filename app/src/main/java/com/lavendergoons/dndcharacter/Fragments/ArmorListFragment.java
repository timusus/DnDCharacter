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

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Adapters.ArmorAdapter;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.util.ArrayList;


/**
 * Fragment to hold armor recycler view
 */
public class ArmorListFragment extends Fragment implements
        View.OnClickListener, ConfirmationDialog.ConfirmationDialogInterface, ArmorAdapter.ArmorAdapterListener {

    public static final String TAG = "ARMOR_LIST_FRAG";

    private RecyclerView mArmorRecyclerView;
    private RecyclerView.Adapter mArmorRecyclerAdapter;
    private RecyclerView.LayoutManager mArmorRecyclerLayoutManager;
    private CharacterManager characterManager;

    private ArrayList<Armor> armorList = new ArrayList<>();
    private SimpleCharacter simpleCharacter;
    private long characterId = -1;

    private FloatingActionButton addArmorFAB;

    public ArmorListFragment() {
        // Required empty public constructor
    }

    public static ArmorListFragment newInstance(SimpleCharacter simpleCharacter, long characterId) {
        ArmorListFragment frag = new ArmorListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, simpleCharacter);
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
        armorList = characterManager.getCharacterArmor();
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

        addArmorFAB = (FloatingActionButton) rootView.findViewById(R.id.addArmorFAB);
        addArmorFAB.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onStop() {
        characterManager.setCharacterArmor(armorList);
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        new ArmorDialog().showDialog();
    }

    private int addArmor(Armor armor) {
        int i = -1;
        if (armor != null) {
            armorList.add(armor);
            i = armorList.indexOf(armor);
            mArmorRecyclerAdapter.notifyDataSetChanged();
        }
        return i;
    }

    @Override
    public void removeArmor(Armor armor) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_armor), this, armor);
    }

    @Override
    public void ConfirmDialogOk(Object armor) {
        if (armor instanceof Armor) {
            armorList.remove(armor);
            mArmorRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object armor) {}


    // Simple Dialog for Creating New Armor
    public class ArmorDialog {
        public void showDialog() {
            final ArmorListFragment fragment = ArmorListFragment.this;

            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
            LinearLayout dialogLayout = new LinearLayout(fragment.getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final EditText armorDialogName, armorDialogWeight, armorDialogQuantity;

            dialogLayout.setOrientation(LinearLayout.VERTICAL);
            dialogLayout.setLayoutParams(params);
            dialogLayout.setPadding(8, 8, 8, 8);

            armorDialogName = new EditText(fragment.getActivity());
            armorDialogName.setHint(R.string.hint_name);
            armorDialogName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            armorDialogWeight = new EditText(fragment.getActivity());
            armorDialogWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
            armorDialogWeight.setHint(R.string.gen_weight);

            armorDialogQuantity = new EditText(fragment.getActivity());
            armorDialogQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
            armorDialogQuantity.setHint(R.string.gen_quantity);
            // Set default quantity to one
            armorDialogQuantity.setText(fragment.getActivity().getString(R.string.one));

            dialogLayout.addView(armorDialogName, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialogLayout.addView(armorDialogWeight, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialogLayout.addView(armorDialogQuantity, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            builder.setTitle(fragment.getString(R.string.title_armor_dialog));
            builder.setView(dialogLayout).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean exceptionCheck = false;
                    String name = armorDialogName.getText().toString();
                    int weight = 0;
                    int quantity = 0;
                    try {
                        weight = Integer.parseInt(armorDialogWeight.getText().toString());
                        quantity = Integer.parseInt(armorDialogQuantity.getText().toString());
                    }catch (Exception ex) {
                        ex.printStackTrace();
                        FirebaseCrash.log(TAG +ex.toString());
                        exceptionCheck = true;
                    }
                    if (!Utils.isStringEmpty(name) && !exceptionCheck) {
                        Armor armor = new Armor(name, weight, quantity);
                        int index = fragment.addArmor(armor);
                        FragmentTransaction fragTransaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
                        fragTransaction.replace(R.id.content_character_nav, ArmorFragment.newInstance(armor, index), ArmorFragment.TAG).addToBackStack(ArmorFragment.TAG).commit();
                    } else {
                        Toast.makeText(fragment.getActivity(), fragment.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                    }
                }
            }).setNegativeButton(R.string.cancel, null).create().show();
        }
    }
}
