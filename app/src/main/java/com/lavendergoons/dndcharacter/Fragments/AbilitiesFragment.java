package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Dialogs.ACDialog;
import com.lavendergoons.dndcharacter.Dialogs.SavesDialog;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;

import static com.lavendergoons.dndcharacter.R.id.savesEditBtn;

/**
 * Abilities Fragment
 */
public class AbilitiesFragment extends Fragment implements View.OnClickListener, ACDialog.ACDialogListener, SavesDialog.SavesDialogListener {


    private OnFragmentInteractionListener mListener;
    private Button savesEditBtn, acEditBtn;
    private TextView saveFortValue, saveReflexValue, saveWillValue, acGenValue, acTouchValue, acFlatFootValue;
    private EditText abilityStrScoreEdit, abilityDexScoreEdit, abilityConScoreEdit, abilityIntScoreEdit,
            abilityWisScoreEdit, abilityChaScoreEdit, abilityStrModEdit, abilityDexModEdit, abilityConModEdit,
            abilityIntModEdit, abilityWisModEdit, abilityChaModEdit;
    TestCharacter character;

    public AbilitiesFragment() {
        // Required empty public constructor
    }

    public static AbilitiesFragment newInstance() {
        return new AbilitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        character = new TestCharacter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_abilities, container, false);

        savesEditBtn = (Button) rootView.findViewById(R.id.savesEditBtn);
        savesEditBtn.setOnClickListener(this);
        acEditBtn = (Button) rootView.findViewById(R.id.acEditBtn);
        acEditBtn.setOnClickListener(this);

        saveFortValue = (TextView) rootView.findViewById(R.id.saveFortValue);
        saveReflexValue = (TextView) rootView.findViewById(R.id.saveReflexValue);
        saveWillValue = (TextView) rootView.findViewById(R.id.saveWillValue);

        acGenValue = (TextView) rootView.findViewById(R.id.acGenValue);
        acTouchValue = (TextView) rootView.findViewById(R.id.acTouchValue);
        acFlatFootValue = (TextView) rootView.findViewById(R.id.acFlatFootValue);

        abilityStrScoreEdit = (EditText) rootView.findViewById(R.id.abilityStrScoreEdit);
        abilityStrScoreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                abilityStrModEdit.setText(modValue(charSequence));
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        abilityDexScoreEdit = (EditText) rootView.findViewById(R.id.abilityDexScoreEdit);
        abilityDexScoreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                abilityDexModEdit.setText(modValue(charSequence));
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        abilityConScoreEdit = (EditText) rootView.findViewById(R.id.abilityConScoreEdit);
        abilityConScoreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                abilityConModEdit.setText(modValue(charSequence));
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        abilityIntScoreEdit = (EditText) rootView.findViewById(R.id.abilityIntScoreEdit);
        abilityIntScoreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                abilityIntModEdit.setText(modValue(charSequence));
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        abilityWisScoreEdit = (EditText) rootView.findViewById(R.id.abilityWisScoreEdit);
        abilityWisScoreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                abilityWisModEdit.setText(modValue(charSequence));
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        abilityChaScoreEdit = (EditText) rootView.findViewById(R.id.abilityChaScoreEdit);
        abilityChaScoreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                abilityChaModEdit.setText(modValue(charSequence));
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        abilityStrModEdit = (EditText) rootView.findViewById(R.id.abilityStrModEdit);
        abilityDexModEdit = (EditText) rootView.findViewById(R.id.abilityDexModEdit);
        abilityConModEdit = (EditText) rootView.findViewById(R.id.abilityConModEdit);
        abilityIntModEdit = (EditText) rootView.findViewById(R.id.abilityIntModEdit);
        abilityWisModEdit = (EditText) rootView.findViewById(R.id.abilityWisModEdit);
        abilityChaModEdit = (EditText) rootView.findViewById(R.id.abilityChaModEdit);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acEditBtn: {
                ACDialog.showACDialog(this.getActivity(), this, character.getAbilities());
                break;
            }
            case R.id.savesEditBtn: {
                SavesDialog.showSavesDialog(this.getActivity(), this, character.getAbilities());
                break;
            }
        }
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
    public void OnACPositive() {
        Toast.makeText(this.getContext(), "AC AbilitesFragment OK", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnACNegative() {
        Toast.makeText(this.getContext(), "AC AbilitesFragment Cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSavesPositive() {
        Toast.makeText(this.getContext(), "Saves AbilitesFragment Cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSavesNegative() {
        Toast.makeText(this.getContext(), "Saves AbilitesFragment Cancel", Toast.LENGTH_SHORT).show();
    }

    private String modValue(CharSequence score) {
        long mod = 0;
        if (score.length() <= 0) {
            return String.valueOf(mod);
        }
        try {
            mod = Long.parseLong(score.toString());
        }catch (NumberFormatException ex) {
            ex.printStackTrace();
            Toast.makeText(this.getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
        if (mod % 2 == 0) {
            mod = (mod - 10)/2;
        } else {
            mod = (mod - 11) / 2;
        }
        return String.valueOf(mod);
    }
}