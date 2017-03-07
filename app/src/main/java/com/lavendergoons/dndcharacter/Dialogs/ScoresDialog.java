package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.R;

/**
 * Dialog to edit full score values
 */

public class ScoresDialog extends DialogFragment {

    public ScoresDialog() {
        super();
    }

    public static interface ScoresDialogListener {
        void OnScoresPositive(Abilities abilities);
        void OnScoresNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ScoresDialog.ScoresDialogListener mInterface = (ScoresDialog.ScoresDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement ScoresDialogListener");
        }
    }

    public static void showScoresDialog(Activity activity, final ScoresDialog.ScoresDialogListener target, final Abilities abiliities) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_scores_dialog));
        //TODO make temp values effect total
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ability_scores, null);

        final EditText strScoreEdit = (EditText) view.findViewById(R.id.strScoreEdit);
        final EditText strModEdit = (EditText) view.findViewById(R.id.strModEdit);
        final EditText strScoreTempEdit = (EditText) view.findViewById(R.id.strScoreTempEdit);
        final EditText strModTempEdit = (EditText) view.findViewById(R.id.strModTempEdit);
        getAbilityValues(strScoreEdit, strModEdit, strScoreTempEdit, strModTempEdit, abiliities, Abilities.STR);

        final EditText dexScoreEdit = (EditText) view.findViewById(R.id.dexScoreEdit);
        final EditText dexModEdit = (EditText) view.findViewById(R.id.dexModEdit);
        final EditText dexScoreTempEdit = (EditText) view.findViewById(R.id.dexScoreTempEdit);
        final EditText dexModTempEdit = (EditText) view.findViewById(R.id.dexModTempEdit);
        getAbilityValues(dexScoreEdit, dexModEdit, dexScoreTempEdit, dexModTempEdit, abiliities, Abilities.DEX);

        final EditText conScoreEdit = (EditText) view.findViewById(R.id.conScoreEdit);
        final EditText conModEdit = (EditText) view.findViewById(R.id.conModEdit);
        final EditText conScoreTempEdit = (EditText) view.findViewById(R.id.conScoreTempEdit);
        final EditText conModTempEdit = (EditText) view.findViewById(R.id.conModTempEdit);
        getAbilityValues(conScoreEdit, conModEdit, conScoreTempEdit, conModTempEdit, abiliities, Abilities.CON);

        final EditText intScoreEdit = (EditText) view.findViewById(R.id.intScoreEdit);
        final EditText intModEdit = (EditText) view.findViewById(R.id.intModEdit);
        final EditText intScoreTempEdit = (EditText) view.findViewById(R.id.intScoreTempEdit);
        final EditText intModTempEdit = (EditText) view.findViewById(R.id.intModTempEdit);
        getAbilityValues(intScoreEdit, intModEdit, intScoreTempEdit, intModTempEdit, abiliities, Abilities.INT);

        final EditText wisScoreEdit = (EditText) view.findViewById(R.id.wisScoreEdit);
        final EditText wisModEdit = (EditText) view.findViewById(R.id.wisModEdit);
        final EditText wisScoreTempEdit = (EditText) view.findViewById(R.id.wisScoreTempEdit);
        final EditText wisModTempEdit = (EditText) view.findViewById(R.id.wisModTempEdit);
        getAbilityValues(wisScoreEdit, wisModEdit, wisScoreTempEdit, wisModTempEdit, abiliities, Abilities.WIS);

        final EditText chaScoreEdit = (EditText) view.findViewById(R.id.chaScoreEdit);
        final EditText chaModEdit = (EditText) view.findViewById(R.id.chaModEdit);
        final EditText chaScoreTempEdit = (EditText) view.findViewById(R.id.chaScoreTempEdit);
        final EditText chaModTempEdit = (EditText) view.findViewById(R.id.chaModTempEdit);
        getAbilityValues(chaScoreEdit, chaModEdit, chaScoreTempEdit, chaModTempEdit, abiliities, Abilities.CHA);

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setAbilityValues(strScoreEdit, strModEdit, strScoreTempEdit, strModTempEdit, abiliities, Abilities.STR);
                setAbilityValues(dexScoreEdit, dexModEdit, dexScoreTempEdit, dexModTempEdit, abiliities, Abilities.DEX);
                setAbilityValues(conScoreEdit, conModEdit, conScoreTempEdit, conModTempEdit, abiliities, Abilities.CON);
                setAbilityValues(intScoreEdit, intModEdit, intScoreTempEdit, intModTempEdit, abiliities, Abilities.INT);
                setAbilityValues(wisScoreEdit, wisModEdit, wisScoreTempEdit, wisModTempEdit, abiliities, Abilities.WIS);
                setAbilityValues(chaScoreEdit, chaModEdit, chaScoreTempEdit, chaModTempEdit, abiliities, Abilities.CHA);
                target.OnScoresPositive(abiliities);
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
    private static void getAbilityValues(EditText score, EditText mod, EditText scoreTemp, EditText modTemp, Abilities abilities, int which) {
        score.setText(String.valueOf(abilities.getScore(which)));
        mod.setText(String.valueOf(abilities.getMod(which)));
        scoreTemp.setText(String.valueOf(abilities.getScoreTemp(which)));
        modTemp.setText(String.valueOf(abilities.getModTemp(which)));
    }

    // Set abilities values based on new data
    private static void setAbilityValues(EditText scoreEdit, EditText modEdit, EditText scoreTempEdit, EditText modTempEdit, Abilities abilities, int which) {
        int score=0, mod=0, scoreTemp=0, modTemp=0;
        try {
            score = Integer.parseInt(scoreEdit.getText().toString());
            mod = Integer.parseInt(modEdit.getText().toString());
            scoreTemp = Integer.parseInt(scoreTempEdit.getText().toString());
            modTemp = Integer.parseInt(modTempEdit.getText().toString());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        abilities.setScore(score, which);
        abilities.setScoreTemp(scoreTemp, which);
        abilities.setMod(mod, which);
        abilities.setModTemp(modTemp, which);
    }
}
