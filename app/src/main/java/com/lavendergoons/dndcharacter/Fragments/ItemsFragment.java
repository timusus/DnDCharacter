package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

/**
 * Top parent Fragment of all 'items' ie. Armor, General Items, etc.
 */
public class ItemsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "ITEMS_FRAG";

    private OnFragmentInteractionListener mListener;
    private Spinner itemSelectionSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private Character character;
    private long id = -1;

    public ItemsFragment() {
        // Required empty public constructor
    }

    public static ItemsFragment newInstance(Character character, long characterId) {
        ItemsFragment frag = new ItemsFragment();
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
            id = getArguments().getLong(Constants.CHARACTER_ID);
            character = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        spinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.item_spinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_items, container, false);
        itemSelectionSpinner = (Spinner) rootView.findViewById(R.id.itemSelectionSpinner);
        itemSelectionSpinner.setAdapter(spinnerAdapter);
        itemSelectionSpinner.setOnItemSelectedListener(this);
        itemSelectionSpinner.setSelection(1);

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        // TODO Clean up. Dont init fragment
        Fragment fragment = new Fragment();
        switch (pos) {
            case Constants.SPINNER_ARMOR:
                fragment = ArmorListFragment.newInstance(character, id);
                break;
            case Constants.SPINNER_ITEM:
                fragment = ItemsGeneralFragment.newInstance(character, id);
                break;
        }
        FragmentTransaction fragTransaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_items_view, fragment).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
