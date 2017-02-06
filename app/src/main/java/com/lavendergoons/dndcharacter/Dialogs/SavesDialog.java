package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.R;

/**
 * Dialog to Edit Saves
 */

public class SavesDialog extends DialogFragment {

    public SavesDialog() {
        super();
    }

    public static interface SavesDialogListener {
        void OnSavesPositive();
        void OnSavesNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SavesDialog.SavesDialogListener mInterface = (SavesDialog.SavesDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement OnAcDialogAction");
        }
    }

    public static void showSavesDialog(Activity activity, final SavesDialog.SavesDialogListener target, final Abilities abilities) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_saves_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_saves, null);

        //TODO Add text change listener
        final EditText saveDialogFortTotalEdit = (EditText) view.findViewById(R.id.saveDialogFortTotalEdit);
        final EditText saveDialogFortBaseEdit = (EditText) view.findViewById(R.id.saveDialogFortBaseEdit);
        final EditText saveDialogFortAbilityEdit = (EditText) view.findViewById(R.id.saveDialogFortAbilityEdit);
        final EditText saveDialogFortMagicEdit = (EditText) view.findViewById(R.id.saveDialogFortMagicEdit);
        final EditText saveDialogFortMiscEdit = (EditText) view.findViewById(R.id.saveDialogFortMiscEdit);
        final EditText saveDialogFortTempEdit = (EditText) view.findViewById(R.id.saveDialogFortTempEdit);

        final EditText saveDialogReflexTotalEdit = (EditText) view.findViewById(R.id.saveDialogReflexTotalEdit);
        final EditText saveDialogReflexBaseEdit = (EditText) view.findViewById(R.id.saveDialogReflexBaseEdit);
        final EditText saveDialogReflexAbilityEdit = (EditText) view.findViewById(R.id.saveDialogReflexAbilityEdit);
        final EditText saveDialogReflexMagicEdit = (EditText) view.findViewById(R.id.saveDialogReflexMagicEdit);
        final EditText saveDialogReflexMiscEdit = (EditText) view.findViewById(R.id.saveDialogReflexMiscEdit);
        final EditText saveDialogReflexTempEdit = (EditText) view.findViewById(R.id.saveDialogReflexTempEdit);

        final EditText saveDialogWillTotalEdit = (EditText) view.findViewById(R.id.saveDialogWillTotalEdit);
        final EditText saveDialogWillBaseEdit = (EditText) view.findViewById(R.id.saveDialogWillBaseEdit);
        final EditText saveDialogWillAbilityEdit = (EditText) view.findViewById(R.id.saveDialogWillAbilityEdit);
        final EditText saveDialogWillMagicEdit = (EditText) view.findViewById(R.id.saveDialogWillMagicEdit);
        final EditText saveDialogWillMiscEdit = (EditText) view.findViewById(R.id.saveDialogWillMiscEdit);
        final EditText saveDialogWillTempEdit = (EditText) view.findViewById(R.id.saveDialogWillTempEdit);

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnSavesPositive();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnSavesNegative();
            }
        });

        builder.create().show();
    }
}
