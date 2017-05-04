package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.BuildConfig;
import com.lavendergoons.dndcharacter.Dialogs.ACDialog;
import com.lavendergoons.dndcharacter.Dialogs.SavesDialog;
import com.lavendergoons.dndcharacter.Dialogs.ScoresDialog;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.Arrays;


/**
 * Abilities Fragment
 */
public class AbilitiesFragment extends Fragment implements View.OnClickListener, ACDialog.ACDialogListener, SavesDialog.SavesDialogListener, ScoresDialog.ScoresDialogListener {

    public static final String TAG = "ABILITIES_FRAG";

    private OnFragmentInteractionListener mListener;
    private CharacterManager characterManager;
    private SimpleCharacter simpleCharacter;
    private Abilities abilities;
    private long characterId = -1;

    private Button savesEditBtn, acEditBtn, scoresEditBtn;

    // Saves and AC Edittexts
    private TextView saveFortValue, saveReflexValue, saveWillValue, acGenValue, acTouchValue, acFlatFootValue;

    //Ability Edits
    private EditText abilityStrScoreEdit, abilityDexScoreEdit, abilityConScoreEdit, abilityIntScoreEdit,
            abilityWisScoreEdit, abilityChaScoreEdit, abilityStrModEdit, abilityDexModEdit, abilityConModEdit,
            abilityIntModEdit, abilityWisModEdit, abilityChaModEdit;

    // General Edittexts
    private EditText abilityHpEdit, abilityNonLethalEdit, abilityBaseAtkEdit, abilitySpellResEdit, abilityInitiativeEdit, abilitySpeedEdit;

    // Grapple Editexts
    private EditText grappleBaseAttackEdit, grappleStrModEdit, grappleSizeModEdit, grappleMiscModEdit, grappleTotalEdit;

    public AbilitiesFragment() {
        // Required empty public constructor
    }

    public static AbilitiesFragment newInstance(SimpleCharacter charIn, long characterId) {
        AbilitiesFragment frag = new AbilitiesFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, charIn);
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
        characterManager = CharacterManager.getInstance(this.getContext());
        abilities = characterManager.getCharacterAbilities();
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
        scoresEditBtn = (Button) rootView.findViewById(R.id.scoresEditBtn);
        scoresEditBtn.setOnClickListener(this);

        saveFortValue = (TextView) rootView.findViewById(R.id.saveFortValue);
        saveReflexValue = (TextView) rootView.findViewById(R.id.saveReflexValue);
        saveWillValue = (TextView) rootView.findViewById(R.id.saveWillValue);

        acGenValue = (TextView) rootView.findViewById(R.id.acGenValue);
        acTouchValue = (TextView) rootView.findViewById(R.id.acTouchValue);
        acFlatFootValue = (TextView) rootView.findViewById(R.id.acFlatFootValue);

        abilityHpEdit = (EditText) rootView.findViewById(R.id.abilityHpEdit);
        abilityNonLethalEdit = (EditText) rootView.findViewById(R.id.abilityNonLethalEdit);
        abilityBaseAtkEdit = (EditText) rootView.findViewById(R.id.abilityBaseAtkEdit);
        abilitySpellResEdit = (EditText) rootView.findViewById(R.id.abilitySpellResEdit);
        abilityInitiativeEdit = (EditText) rootView.findViewById(R.id.abilityInitiativeEdit);
        abilitySpeedEdit = (EditText) rootView.findViewById(R.id.abilitySpeedEdit);

        grappleBaseAttackEdit = (EditText) rootView.findViewById(R.id.grappleBaseAttackEdit);
        grappleStrModEdit = (EditText) rootView.findViewById(R.id.grappleStrModEdit);
        grappleSizeModEdit = (EditText) rootView.findViewById(R.id.grappleSizeModEdit);
        grappleMiscModEdit = (EditText) rootView.findViewById(R.id.grappleMiscModEdit);
        grappleTotalEdit = (EditText) rootView.findViewById(R.id.grappleTotalEdit);

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

        setValues();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acEditBtn: {
                ACDialog.showACDialog(this.getActivity(), this, abilities);
                break;
            }
            case R.id.savesEditBtn: {
                SavesDialog.showSavesDialog(this.getActivity(), this, abilities);
                break;
            }
            case R.id.scoresEditBtn: {
                ScoresDialog.showScoresDialog(this.getActivity(), this, abilities);
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
    public void onStop() {
        writeAbilities();
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void writeAbilities() {
        readAbilityGeneralValues();
        readGrappleValues();
        readScoreModValues();
        characterManager.setCharacterAbilities(abilities);
    }

    //**********************************************************
    // Listeners
    //**********************************************************

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    @Override
    public void OnACPositive(Abilities abilities) {
        this.abilities = abilities;
        if (this.abilities == null) {
            FirebaseCrash.log("Abilities Null in AcPositive");
        }
        setACValues();
    }

    @Override
    public void OnACNegative() {
    }

    @Override
    public void OnSavesPositive(Abilities abilities) {
        this.abilities = abilities;
        setScoreValues();
    }

    @Override
    public void OnSavesNegative() {
    }

    @Override
    public void OnScoresPositive(Abilities abilities) {
        this.abilities = abilities;
        setSaveModValues();
    }

    @Override
    public void OnScoresNegative() {}

    //**********************************************************
    // Set Values To EditTexts
    //**********************************************************

    private void setValues() {
        if (abilities != null) {
            setACValues();
            setScoreValues();
            setSaveModValues();
            setAbilityGeneralValues();
            setGrappleValues();
        }
    }

    private void setACValues() {
        try {
            acGenValue.setText(String.valueOf(abilities.getAC(Abilities.AC_TOTAL)));
            acTouchValue.setText(String.valueOf(abilities.getAcTouch()));
            acFlatFootValue.setText(String.valueOf(abilities.getAcFlatFoot()));
        }catch (Exception ex) {
            ex.printStackTrace();
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
        }
    }

    private void setScoreValues() {
        try {
            saveFortValue.setText(String.valueOf(abilities.getFort(Abilities.SAVE_TOTAL)));
            saveReflexValue.setText(String.valueOf(abilities.getReflex(Abilities.SAVE_TOTAL)));
            saveWillValue.setText(String.valueOf(abilities.getWill(Abilities.SAVE_TOTAL)));
        }catch (Exception ex) {
            ex.printStackTrace();
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
        }
    }

    private void setSaveModValues() {
        try {
            abilityStrScoreEdit.setText(String.valueOf(abilities.getScore(Abilities.STR)));
            abilityDexScoreEdit.setText(String.valueOf(abilities.getScore(Abilities.DEX)));
            abilityConScoreEdit.setText(String.valueOf(abilities.getScore(Abilities.CON)));
            abilityIntScoreEdit.setText(String.valueOf(abilities.getScore(Abilities.INT)));
            abilityWisScoreEdit.setText(String.valueOf(abilities.getScore(Abilities.WIS)));
            abilityChaScoreEdit.setText(String.valueOf(abilities.getScore(Abilities.CHA)));

            abilityStrModEdit.setText(String.valueOf(abilities.getMod(Abilities.STR)));
            abilityDexModEdit.setText(String.valueOf(abilities.getMod(Abilities.DEX)));
            abilityConModEdit.setText(String.valueOf(abilities.getMod(Abilities.CON)));
            abilityIntModEdit.setText(String.valueOf(abilities.getMod(Abilities.INT)));
            abilityWisModEdit.setText(String.valueOf(abilities.getMod(Abilities.WIS)));
            abilityChaModEdit.setText(String.valueOf(abilities.getMod(Abilities.CHA)));
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log("Scores Array: "+Arrays.toString(abilities.getScoreArray()));
            FirebaseCrash.log("Scores Array: "+Arrays.toString(abilities.getModArray()));
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
        }
    }

    private void setAbilityGeneralValues() {
        try {
            abilityHpEdit.setText(String.valueOf(abilities.getHp()));
            abilityNonLethalEdit.setText(String.valueOf(abilities.getNonLethal()));
            abilityBaseAtkEdit.setText(String.valueOf(abilities.getBaseAtk()));
            abilitySpellResEdit.setText(String.valueOf(abilities.getSpellRes()));
            abilityInitiativeEdit.setText(String.valueOf(abilities.getInitiative()));
            abilitySpeedEdit.setText(String.valueOf(abilities.getSpeed()));
        }catch (Exception ex) {
            ex.printStackTrace();
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
        }
    }

    private void setGrappleValues() {
        try {
            grappleBaseAttackEdit.setText(String.valueOf(abilities.getGrapple(Abilities.GRAPPLE_BASE)));
            grappleStrModEdit.setText(String.valueOf(abilities.getGrapple(Abilities.GRAPPLE_STR)));
            grappleSizeModEdit.setText(String.valueOf(abilities.getGrapple(Abilities.GRAPPLE_SIZE)));
            grappleMiscModEdit.setText(String.valueOf(abilities.getGrapple(Abilities.GRAPPLE_MISC)));
            grappleTotalEdit.setText(String.valueOf(abilities.getGrapple(Abilities.GRAPPLE_TOTAL)));
        }catch (Exception ex) {
            ex.printStackTrace();
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
        }
    }

    //**********************************************************
    // Read Values From EditTexts
    //**********************************************************

    private void readAbilityGeneralValues() {
        int hp = 0, nonLethal = 0, baseAtk = 0, spellRes = 0, init = 0, speed = 0;
        try {
            hp = Integer.parseInt(abilityHpEdit.getText().toString());
            nonLethal = Integer.parseInt(abilityNonLethalEdit.getText().toString());
            baseAtk = Integer.parseInt(abilityBaseAtkEdit.getText().toString());
            spellRes = Integer.parseInt(abilitySpellResEdit.getText().toString());
            init = Integer.parseInt(abilityInitiativeEdit.getText().toString());
            speed = Integer.parseInt(abilitySpeedEdit.getText().toString());
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log("EditText Values:\n"+
                    "HP: "+abilityHpEdit.getText().toString()+
                    " NonLethal: "+abilityNonLethalEdit.getText().toString()+
                    " BaseAtk: "+abilityBaseAtkEdit.getText().toString()+
                    " SpellRes: "+abilitySpellResEdit.getText().toString()+
                    " Initiative: "+abilityInitiativeEdit.getText().toString()+
                    " Speed: "+abilitySpeedEdit.getText().toString()
            );
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
        }
        abilities.setHp(hp);
        abilities.setNonLethal(nonLethal);
        abilities.setBaseAtk(baseAtk);
        abilities.setSpellRes(spellRes);
        abilities.setInitiative(init);
        abilities.setSpeed(speed);
    }

    private void readGrappleValues() {
        int[] array = new int[]{0,0,0,0,0};
        try {
            array[Abilities.GRAPPLE_BASE] = Integer.parseInt(grappleBaseAttackEdit.getText().toString());
            array[Abilities.GRAPPLE_STR] = Integer.parseInt(grappleStrModEdit.getText().toString());
            array[Abilities.GRAPPLE_SIZE] = Integer.parseInt(grappleSizeModEdit.getText().toString());
            array[Abilities.GRAPPLE_MISC] = Integer.parseInt(grappleMiscModEdit.getText().toString());
            array[Abilities.GRAPPLE_TOTAL] = Integer.parseInt(grappleTotalEdit.getText().toString());
        }catch (Exception ex) {
            FirebaseCrash.log("EditText Values:\n"+
                    " BaseAtk: "+grappleBaseAttackEdit.getText().toString()+
                    " StrMod: "+grappleStrModEdit.getText().toString()+
                    " Size: "+grappleSizeModEdit.getText().toString()+
                    " Misc: "+grappleMiscModEdit.getText().toString()+
                    " Total: "+grappleTotalEdit.getText().toString()
            );
            FirebaseCrash.log("Array: "+ Arrays.toString(array));
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
            ex.printStackTrace();
        }
        abilities.setGrappleArray(array);
    }

    private void readScoreModValues() {
        int[] scores = new int[]{0,0,0,0,0,0};
        int[] mods = new int[]{0,0,0,0,0,0};
        try {
            scores[Abilities.STR] = Integer.parseInt(abilityStrScoreEdit.getText().toString());
            scores[Abilities.DEX] = Integer.parseInt(abilityDexScoreEdit.getText().toString());
            scores[Abilities.CON] = Integer.parseInt(abilityConScoreEdit.getText().toString());
            scores[Abilities.INT] = Integer.parseInt(abilityIntScoreEdit.getText().toString());
            scores[Abilities.WIS] = Integer.parseInt(abilityWisScoreEdit.getText().toString());
            scores[Abilities.CHA] = Integer.parseInt(abilityChaScoreEdit.getText().toString());

            mods[Abilities.STR] = Integer.parseInt(abilityStrModEdit.getText().toString());
            mods[Abilities.DEX] = Integer.parseInt(abilityDexModEdit.getText().toString());
            mods[Abilities.CON] = Integer.parseInt(abilityConModEdit.getText().toString());
            mods[Abilities.INT] = Integer.parseInt(abilityIntModEdit.getText().toString());
            mods[Abilities.WIS] = Integer.parseInt(abilityWisModEdit.getText().toString());
            mods[Abilities.CHA] = Integer.parseInt(abilityChaModEdit.getText().toString());
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log("Score EditText Values:\n"+
                    " STR: "+abilityStrScoreEdit.getText().toString()+
                    " DEX: "+abilityDexScoreEdit.getText().toString()+
                    " CON: "+abilityConScoreEdit.getText().toString()+
                    " INT: "+abilityIntScoreEdit.getText().toString()+
                    " WIS: "+abilityWisScoreEdit.getText().toString()+
                    " CHA: "+abilityChaScoreEdit.getText().toString()
            );
            FirebaseCrash.log("Mod EditText Values:\n"+
                    " STR: "+abilityStrModEdit.getText().toString()+
                    " DEX: "+abilityDexModEdit.getText().toString()+
                    " CON: "+abilityConModEdit.getText().toString()+
                    " INT: "+abilityIntModEdit.getText().toString()+
                    " WIS: "+abilityWisModEdit.getText().toString()+
                    " CHA: "+abilityChaModEdit.getText().toString()
            );
            FirebaseCrash.log("Score Array: "+Arrays.toString(scores));
            FirebaseCrash.log("Mod Array: "+Arrays.toString(mods));
            if (!BuildConfig.DEBUG) {
                FirebaseCrash.report(ex);
            }
        }
        abilities.setScoreArray(scores);
        abilities.setModArray(mods);
    }

    private String modValue(CharSequence score) {
        int mod = 0;
        if (score.length() <= 0) {
            return String.valueOf(mod);
        }
        try {
            mod = Integer.parseInt(score.toString());
        }catch (NumberFormatException ex) {
            ex.printStackTrace();
            Toast.makeText(this.getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
        mod = (mod%2==0)? (mod - 10)/2 : (mod - 11)/2;
        return String.valueOf(mod);
    }
}
