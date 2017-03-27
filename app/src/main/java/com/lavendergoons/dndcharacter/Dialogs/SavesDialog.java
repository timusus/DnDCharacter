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
        void OnSavesPositive(Abilities abilities);
        void OnSavesNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SavesDialog.SavesDialogListener mInterface = (SavesDialog.SavesDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement SavesDialogListener");
        }
    }

    public static void showSavesDialog(Activity activity, final SavesDialog.SavesDialogListener target, final Abilities abilities) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_saves_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_saves, null);

        final EditText saveDialogFortTotalEdit = (EditText) view.findViewById(R.id.saveDialogFortTotalEdit);
        final EditText saveDialogFortBaseEdit = (EditText) view.findViewById(R.id.saveDialogFortBaseEdit);
        final EditText saveDialogFortAbilityEdit = (EditText) view.findViewById(R.id.saveDialogFortAbilityEdit);
        final EditText saveDialogFortMagicEdit = (EditText) view.findViewById(R.id.saveDialogFortMagicEdit);
        final EditText saveDialogFortMiscEdit = (EditText) view.findViewById(R.id.saveDialogFortMiscEdit);
        final EditText saveDialogFortTempEdit = (EditText) view.findViewById(R.id.saveDialogFortTempEdit);
        getSaveValues(saveDialogFortTotalEdit, saveDialogFortBaseEdit, saveDialogFortAbilityEdit, saveDialogFortMagicEdit, saveDialogFortMiscEdit, saveDialogFortTempEdit, abilities, 0);

        final EditText saveDialogReflexTotalEdit = (EditText) view.findViewById(R.id.saveDialogReflexTotalEdit);
        final EditText saveDialogReflexBaseEdit = (EditText) view.findViewById(R.id.saveDialogReflexBaseEdit);
        final EditText saveDialogReflexAbilityEdit = (EditText) view.findViewById(R.id.saveDialogReflexAbilityEdit);
        final EditText saveDialogReflexMagicEdit = (EditText) view.findViewById(R.id.saveDialogReflexMagicEdit);
        final EditText saveDialogReflexMiscEdit = (EditText) view.findViewById(R.id.saveDialogReflexMiscEdit);
        final EditText saveDialogReflexTempEdit = (EditText) view.findViewById(R.id.saveDialogReflexTempEdit);
        getSaveValues(saveDialogReflexTotalEdit, saveDialogReflexBaseEdit, saveDialogReflexAbilityEdit, saveDialogReflexMagicEdit, saveDialogReflexMiscEdit, saveDialogReflexTempEdit, abilities, 1);

        final EditText saveDialogWillTotalEdit = (EditText) view.findViewById(R.id.saveDialogWillTotalEdit);
        final EditText saveDialogWillBaseEdit = (EditText) view.findViewById(R.id.saveDialogWillBaseEdit);
        final EditText saveDialogWillAbilityEdit = (EditText) view.findViewById(R.id.saveDialogWillAbilityEdit);
        final EditText saveDialogWillMagicEdit = (EditText) view.findViewById(R.id.saveDialogWillMagicEdit);
        final EditText saveDialogWillMiscEdit = (EditText) view.findViewById(R.id.saveDialogWillMiscEdit);
        final EditText saveDialogWillTempEdit = (EditText) view.findViewById(R.id.saveDialogWillTempEdit);
        getSaveValues(saveDialogWillTotalEdit, saveDialogWillBaseEdit, saveDialogWillAbilityEdit, saveDialogWillMagicEdit, saveDialogWillMiscEdit, saveDialogWillTempEdit, abilities, 2);

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setSaveValues(saveDialogFortTotalEdit, saveDialogFortBaseEdit, saveDialogFortAbilityEdit, saveDialogFortMagicEdit, saveDialogFortMiscEdit, saveDialogFortTempEdit, abilities, 0);
                setSaveValues(saveDialogReflexTotalEdit, saveDialogReflexBaseEdit, saveDialogReflexAbilityEdit, saveDialogReflexMagicEdit, saveDialogReflexMiscEdit, saveDialogReflexTempEdit, abilities, 1);
                setSaveValues(saveDialogWillTotalEdit, saveDialogWillBaseEdit, saveDialogWillAbilityEdit, saveDialogWillMagicEdit, saveDialogWillMiscEdit, saveDialogWillTempEdit, abilities, 2);
                target.OnSavesPositive(abilities);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnSavesNegative();
            }
        });

        builder.create().show();
    }

    private static void getSaveValues(EditText totalEdit, EditText baseEdit, EditText abilityEdit, EditText magicEdit, EditText miscEdit, EditText tempEdit, Abilities abilities, int which) {
        int[] array;
        switch (which) {
            case 0:
                array = abilities.getFortArray();
                break;
            case 1:
                array = abilities.getReflexArray();
                break;
            case 2:
                array = abilities.getWillArray();
                break;
            default:
                array = new int[]{0, 0, 0, 0, 0, 0};
        }
        totalEdit.setText(String.valueOf(array[Abilities.SAVE_TOTAL]));
        baseEdit.setText(String.valueOf(array[Abilities.SAVE_BASE]));
        abilityEdit.setText(String.valueOf(array[Abilities.SAVE_ABILITY]));
        magicEdit.setText(String.valueOf(array[Abilities.SAVE_MAGIC]));
        miscEdit.setText(String.valueOf(array[Abilities.SAVE_MISC]));
        tempEdit.setText(String.valueOf(array[Abilities.SAVE_TEMP]));
    }

    private static void setSaveValues(EditText totalEdit, EditText baseEdit, EditText abilityEdit, EditText magicEdit, EditText miscEdit, EditText tempEdit, Abilities abilities, int which) {
        int total=0, base=0, ability=0, magic=0, misc=0, temp=0;
        int[] array;
        switch (which) {
            case 0:
                array = abilities.getFortArray();
                break;
            case 1:
                array = abilities.getReflexArray();
                break;
            case 2:
                array = abilities.getWillArray();
                break;
            default:
                array = new int[]{0, 0, 0, 0, 0, 0};
        }
        try {
            total = Integer.parseInt(totalEdit.getText().toString());
            base = Integer.parseInt(baseEdit.getText().toString());
            ability = Integer.parseInt(abilityEdit.getText().toString());
            magic = Integer.parseInt(magicEdit.getText().toString());
            misc = Integer.parseInt(miscEdit.getText().toString());
            temp = Integer.parseInt(tempEdit.getText().toString());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        array[Abilities.SAVE_TOTAL] = total;
        array[Abilities.SAVE_BASE] = base;
        array[Abilities.SAVE_ABILITY] = ability;
        array[Abilities.SAVE_MAGIC] = magic;
        array[Abilities.SAVE_MISC] = misc;
        array[Abilities.SAVE_TEMP] = temp;
    }
}
