package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.R;

import static java.lang.Integer.parseInt;


/**
 * Dialog to edit full score values
 */

public class ScoresDialog {

    public static final String TAG = "SCORE_DIALOG";

    private Activity activity;
    private ScoresDialogListener target;
    private Abilities abilities;

    EditText strScoreEdit, strModEdit, strScoreTempEdit, strModTempEdit;
    EditText dexScoreEdit, dexModEdit, dexScoreTempEdit, dexModTempEdit;
    EditText conScoreEdit, conModEdit, conScoreTempEdit, conModTempEdit;
    EditText intScoreEdit, intModEdit, intScoreTempEdit, intModTempEdit;
    EditText wisScoreEdit, wisModEdit, wisScoreTempEdit, wisModTempEdit;
    EditText chaScoreEdit, chaModEdit, chaScoreTempEdit, chaModTempEdit;

    public ScoresDialog(Activity activity, ScoresDialogListener target, Abilities abilities) {
        this.activity = activity;
        this.target = target;
        this.abilities = abilities;
    }

    public static interface ScoresDialogListener {
        void OnScoresPositive(Abilities abilities);
        void OnScoresNegative();
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_scores_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ability_scores, null);

        strScoreEdit = (EditText) view.findViewById(R.id.strScoreEdit);
        strModEdit = (EditText) view.findViewById(R.id.strModEdit);
        strScoreTempEdit = (EditText) view.findViewById(R.id.strScoreTempEdit);
        strModTempEdit = (EditText) view.findViewById(R.id.strModTempEdit);
        getAbilityValues(strScoreEdit, strModEdit, strScoreTempEdit, strModTempEdit, Abilities.STR);

        dexScoreEdit = (EditText) view.findViewById(R.id.dexScoreEdit);
        dexModEdit = (EditText) view.findViewById(R.id.dexModEdit);
        dexScoreTempEdit = (EditText) view.findViewById(R.id.dexScoreTempEdit);
        dexModTempEdit = (EditText) view.findViewById(R.id.dexModTempEdit);
        getAbilityValues(dexScoreEdit, dexModEdit, dexScoreTempEdit, dexModTempEdit, Abilities.DEX);

        conScoreEdit = (EditText) view.findViewById(R.id.conScoreEdit);
        conModEdit = (EditText) view.findViewById(R.id.conModEdit);
        conScoreTempEdit = (EditText) view.findViewById(R.id.conScoreTempEdit);
        conModTempEdit = (EditText) view.findViewById(R.id.conModTempEdit);
        getAbilityValues(conScoreEdit, conModEdit, conScoreTempEdit, conModTempEdit, Abilities.CON);

        intScoreEdit = (EditText) view.findViewById(R.id.intScoreEdit);
        intModEdit = (EditText) view.findViewById(R.id.intModEdit);
        intScoreTempEdit = (EditText) view.findViewById(R.id.intScoreTempEdit);
        intModTempEdit = (EditText) view.findViewById(R.id.intModTempEdit);
        getAbilityValues(intScoreEdit, intModEdit, intScoreTempEdit, intModTempEdit, Abilities.INT);

        wisScoreEdit = (EditText) view.findViewById(R.id.wisScoreEdit);
        wisModEdit = (EditText) view.findViewById(R.id.wisModEdit);
        wisScoreTempEdit = (EditText) view.findViewById(R.id.wisScoreTempEdit);
        wisModTempEdit = (EditText) view.findViewById(R.id.wisModTempEdit);
        getAbilityValues(wisScoreEdit, wisModEdit, wisScoreTempEdit, wisModTempEdit, Abilities.WIS);

        chaScoreEdit = (EditText) view.findViewById(R.id.chaScoreEdit);
        chaModEdit = (EditText) view.findViewById(R.id.chaModEdit);
        chaScoreTempEdit = (EditText) view.findViewById(R.id.chaScoreTempEdit);
        chaModTempEdit = (EditText) view.findViewById(R.id.chaModTempEdit);
        getAbilityValues(chaScoreEdit, chaModEdit, chaScoreTempEdit, chaModTempEdit, Abilities.CHA);

        setTextWatchers();
        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setAbilityValues(strScoreEdit, strModEdit, strScoreTempEdit, strModTempEdit, Abilities.STR);
                setAbilityValues(dexScoreEdit, dexModEdit, dexScoreTempEdit, dexModTempEdit, Abilities.DEX);
                setAbilityValues(conScoreEdit, conModEdit, conScoreTempEdit, conModTempEdit, Abilities.CON);
                setAbilityValues(intScoreEdit, intModEdit, intScoreTempEdit, intModTempEdit, Abilities.INT);
                setAbilityValues(wisScoreEdit, wisModEdit, wisScoreTempEdit, wisModTempEdit, Abilities.WIS);
                setAbilityValues(chaScoreEdit, chaModEdit, chaScoreTempEdit, chaModTempEdit, Abilities.CHA);
                target.OnScoresPositive(abilities);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnScoresNegative();
            }
        });

        builder.create().show();
    }

    // Set the values of the edittexts
    private void getAbilityValues(EditText score, EditText mod, EditText scoreTemp, EditText modTemp, int which) {
        score.setText(String.valueOf(abilities.getScore(which)));
        mod.setText(String.valueOf(abilities.getMod(which)));
        scoreTemp.setText(String.valueOf(abilities.getScoreTemp(which)));
        modTemp.setText(String.valueOf(abilities.getModTemp(which)));
    }

    // Set abilities values based on new data
    private void setAbilityValues(EditText scoreEdit, EditText modEdit, EditText scoreTempEdit, EditText modTempEdit, int which) {
        int score=0, mod=0, scoreTemp=0, modTemp=0;
        try {
            score = parseInt(scoreEdit.getText().toString());
            mod = parseInt(modEdit.getText().toString());
            scoreTemp = parseInt(scoreTempEdit.getText().toString());
            modTemp = parseInt(modTempEdit.getText().toString());
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log(TAG +ex.toString());
        }
        abilities.setScore(score, which);
        abilities.setScoreTemp(scoreTemp, which);
        abilities.setMod(mod, which);
        abilities.setModTemp(modTemp, which);
    }

    private void setTextWatchers() {
        strScoreEdit.addTextChangedListener(new ScoreWatcher(strModEdit));
        strScoreTempEdit.addTextChangedListener(new ScoreWatcher(strModTempEdit));

        dexScoreEdit.addTextChangedListener(new ScoreWatcher(dexModEdit));
        dexScoreTempEdit.addTextChangedListener(new ScoreWatcher(dexModTempEdit));

        conScoreEdit.addTextChangedListener(new ScoreWatcher(conModEdit));
        conScoreTempEdit.addTextChangedListener(new ScoreWatcher(conModTempEdit));

        intScoreEdit.addTextChangedListener(new ScoreWatcher(intModEdit));
        intScoreTempEdit.addTextChangedListener(new ScoreWatcher(intModTempEdit));

        wisScoreEdit.addTextChangedListener(new ScoreWatcher(wisModEdit));
        wisScoreTempEdit.addTextChangedListener(new ScoreWatcher(wisModTempEdit));

        chaScoreEdit.addTextChangedListener(new ScoreWatcher(chaModEdit));
        chaScoreTempEdit.addTextChangedListener(new ScoreWatcher(chaModTempEdit));
    }

    private class ScoreWatcher implements TextWatcher {
        EditText modEdit;
        public ScoreWatcher(EditText modEdit) {
            this.modEdit = modEdit;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            modEdit.setText(modValue(charSequence));
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private String modValue(CharSequence score) {
        int mod = 0;
        if (score.length() <= 0) {
            return String.valueOf(mod);
        }
        try {
            mod = parseInt(score.toString());
        }catch (NumberFormatException ex) {
            ex.printStackTrace();
            FirebaseCrash.log(TAG +ex.toString());
        }
        mod = (mod%2==0)? (mod - 10)/2 : (mod - 11)/2;
        return String.valueOf(mod);
    }
}
