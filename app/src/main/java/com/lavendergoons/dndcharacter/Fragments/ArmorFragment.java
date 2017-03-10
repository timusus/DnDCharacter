package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class ArmorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Armor armor;
    private int index = -1;
    private EditText armorNameEdit, armorTypeEdit, armorACEdit, armorDexEdit, armorCheckEdit, armorSpellEdit, armorSpeedEdit, armorWeightEdit, armorPropertiesEdit, armorQuantityEdit;
    public static final String TAG = "ARMOR_FRAG";

    public ArmorFragment() {
        // Required empty public constructor
    }

    public static ArmorFragment newInstance(Armor armor, int index) {
        ArmorFragment frag = new ArmorFragment();
        if (armor == null) {
            armor = new Armor();
        }
        Bundle args = new Bundle();
        args.putParcelable("ARMOR", armor);
        args.putInt("INDEX", index);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            armor = getArguments() != null ? (Armor) getArguments().getParcelable("ARMOR") : new Armor();
            index = getArguments().getInt("INDEX");
        }catch(BadParcelableException ex) {
            Log.e("PARSE", "Bad Parcelable in ArmorFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_armor, container, false);
        armorNameEdit = (EditText) rootView.findViewById(R.id.armorNameEdit);
        armorTypeEdit = (EditText) rootView.findViewById(R.id.armorTypeEdit);
        armorACEdit = (EditText) rootView.findViewById(R.id.armorACEdit);
        armorDexEdit = (EditText) rootView.findViewById(R.id.armorDexEdit);
        armorCheckEdit = (EditText) rootView.findViewById(R.id.armorCheckEdit);
        armorSpellEdit = (EditText) rootView.findViewById(R.id.armorSpellEdit);
        armorSpeedEdit = (EditText) rootView.findViewById(R.id.armorSpeedEdit);
        armorWeightEdit = (EditText) rootView.findViewById(R.id.armorWeightEdit);
        armorPropertiesEdit = (EditText) rootView.findViewById(R.id.armorPropertiesEdit);
        armorQuantityEdit = (EditText) rootView.findViewById(R.id.armorQuantityEdit);
        getValues();
        return rootView;
    }

    private void setValues() {
        try {
            armor.setName(armorNameEdit.getText().toString());
            armor.setType(armorTypeEdit.getText().toString());
            armor.setAcBonus(Integer.parseInt(armorACEdit.getText().toString()));
            armor.setMaxDex(Integer.parseInt(armorDexEdit.getText().toString()));
            armor.setCheckPenalty(Integer.parseInt(armorCheckEdit.getText().toString()));
            armor.setSpellFailure(Integer.parseInt(armorSpellEdit.getText().toString()));
            armor.setWeight(Integer.parseInt(armorSpeedEdit.getText().toString()));
            armor.setSpeed(Integer.parseInt(armorWeightEdit.getText().toString()));
            armor.setProperties(armorPropertiesEdit.getText().toString());
            armor.setQuantity(Integer.parseInt(armorQuantityEdit.getText().toString()));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getValues() {
        try {
            armorNameEdit.setText(armor.getName());
            armorTypeEdit.setText(armor.getType());
            armorACEdit.setText(String.valueOf(armor.getAcBonus()));
            armorDexEdit.setText(String.valueOf(armor.getMaxDex()));
            armorCheckEdit.setText(String.valueOf(armor.getCheckPenalty()));
            armorSpellEdit.setText(String.valueOf(armor.getSpellFailure()));
            armorSpeedEdit.setText(String.valueOf(armor.getSpeed()));
            armorWeightEdit.setText(String.valueOf(armor.getWeight()));
            armorPropertiesEdit.setText(armor.getProperties());
            armorQuantityEdit.setText(String.valueOf(armor.getQuantity()));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        setValues();
//        try {
//            ((OnFragmentInteractionListener) getActivity()).passBackArmor(armor, index);
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
//        try {
//            ((OnFragmentInteractionListener) getActivity()).passBackArmor(armor, index);
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
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
        try {
            ((OnFragmentInteractionListener) getActivity()).passBackArmor(armor, index);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        /**
         * Pass back armor to CharacterNavActivity
         * to pass to ArmorListFragment
         */
        void passBackArmor(Armor armor, int index);
    }
}
