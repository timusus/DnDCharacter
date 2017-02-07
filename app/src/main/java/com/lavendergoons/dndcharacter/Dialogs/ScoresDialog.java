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
        void OnScoresPositive();
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

    public static void showScoresDialog(Activity activity, final ScoresDialog.ScoresDialogListener target, final Abilities abiliites) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_scores_dialog));
        //TODO make temp values effect total
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ability_scores, null);

        final EditText strScoreEdit = (EditText) view.findViewById(R.id.strScoreEdit);
        final EditText strModEdit = (EditText) view.findViewById(R.id.strModEdit);
        final EditText strScoreTempEdit = (EditText) view.findViewById(R.id.strScoreTempEdit);
        final EditText strModTempEdit = (EditText) view.findViewById(R.id.strModTempEdit);
        getValues(strScoreEdit, strModEdit, strScoreTempEdit, strModTempEdit, abiliites, Abilities.STR);

        final EditText dexScoreEdit = (EditText) view.findViewById(R.id.dexScoreEdit);
        final EditText dexModEdit = (EditText) view.findViewById(R.id.dexModEdit);
        final EditText dexScoreTempEdit = (EditText) view.findViewById(R.id.dexScoreTempEdit);
        final EditText dexModTempEdit = (EditText) view.findViewById(R.id.dexModTempEdit);
        getValues(dexScoreEdit, dexModEdit, dexScoreTempEdit, dexModTempEdit, abiliites, Abilities.DEX);

        final EditText conScoreEdit = (EditText) view.findViewById(R.id.conScoreEdit);
        final EditText conModEdit = (EditText) view.findViewById(R.id.conModEdit);
        final EditText conScoreTempEdit = (EditText) view.findViewById(R.id.conScoreTempEdit);
        final EditText conModTempEdit = (EditText) view.findViewById(R.id.conModTempEdit);
        getValues(conScoreEdit, conModEdit, conScoreTempEdit, conModTempEdit, abiliites, Abilities.CON);

        final EditText intScoreEdit = (EditText) view.findViewById(R.id.intScoreEdit);
        final EditText intModEdit = (EditText) view.findViewById(R.id.intModEdit);
        final EditText intScoreTempEdit = (EditText) view.findViewById(R.id.intScoreTempEdit);
        final EditText intModTempEdit = (EditText) view.findViewById(R.id.intModTempEdit);
        getValues(intScoreEdit, intModEdit, intScoreTempEdit, intModTempEdit, abiliites, Abilities.INT);

        final EditText wisScoreEdit = (EditText) view.findViewById(R.id.wisScoreEdit);
        final EditText wisModEdit = (EditText) view.findViewById(R.id.wisModEdit);
        final EditText wisScoreTempEdit = (EditText) view.findViewById(R.id.wisScoreTempEdit);
        final EditText wisModTempEdit = (EditText) view.findViewById(R.id.wisModTempEdit);
        getValues(wisScoreEdit, wisModEdit, wisScoreTempEdit, wisModTempEdit, abiliites, Abilities.WIS);

        final EditText chaScoreEdit = (EditText) view.findViewById(R.id.chaScoreEdit);
        final EditText chaModEdit = (EditText) view.findViewById(R.id.chaModEdit);
        final EditText chaScoreTempEdit = (EditText) view.findViewById(R.id.chaScoreTempEdit);
        final EditText chaModTempEdit = (EditText) view.findViewById(R.id.chaModTempEdit);
        getValues(chaScoreEdit, chaModEdit, chaScoreTempEdit, chaModTempEdit, abiliites, Abilities.CHA);

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setValues(strScoreEdit, strModEdit, strScoreTempEdit, strModTempEdit, abiliites, Abilities.STR);
                setValues(dexScoreEdit, dexModEdit, dexScoreTempEdit, dexModTempEdit, abiliites, Abilities.DEX);
                setValues(conScoreEdit, conModEdit, conScoreTempEdit, conModTempEdit, abiliites, Abilities.CON);
                setValues(intScoreEdit, intModEdit, intScoreTempEdit, intModTempEdit, abiliites, Abilities.INT);
                setValues(wisScoreEdit, wisModEdit, wisScoreTempEdit, wisModTempEdit, abiliites, Abilities.WIS);
                setValues(chaScoreEdit, chaModEdit, chaScoreTempEdit, chaModTempEdit, abiliites, Abilities.CHA);
                target.OnScoresPositive();
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
    private static void getValues(EditText score, EditText mod, EditText scoreTemp, EditText modTemp, Abilities abilities, int which) {
        score.setText(String.valueOf(abilities.getScore(which)));
        mod.setText(String.valueOf(abilities.getMod(which)));
        scoreTemp.setText(String.valueOf(abilities.getScoreTemp(which)));
        modTemp.setText(String.valueOf(abilities.getModTemp(which)));
    }

    // Set abilities values based on new data
    private static void setValues(EditText score, EditText mod, EditText scoreTemp, EditText modTemp, Abilities abilities, int which) {
        abilities.setScore(Integer.parseInt(score.getText().toString()), which);
        abilities.setScoreTemp(Integer.parseInt(scoreTemp.getText().toString()), which);
        abilities.setMod(Integer.parseInt(mod.getText().toString()), which);
        abilities.setModTemp(Integer.parseInt(modTemp.getText().toString()), which);
    }
}
