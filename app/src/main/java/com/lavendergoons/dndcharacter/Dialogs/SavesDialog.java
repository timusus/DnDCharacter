package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.R;

/**
 * Dialog to Edit Saves
 */

public class SavesDialog {

    public static final String TAG = "SAVES_DIALOG";

    private Activity activity;
    private SavesDialogListener target;
    private Abilities abilities;

    public static final int FORT = 0;
    public static final int REFLEX = 1;
    public static final int WILL = 2;

    EditText saveDialogFortTotalEdit, saveDialogFortBaseEdit, saveDialogFortAbilityEdit, saveDialogFortMagicEdit, saveDialogFortMiscEdit, saveDialogFortTempEdit;
    EditText saveDialogReflexTotalEdit, saveDialogReflexBaseEdit, saveDialogReflexAbilityEdit, saveDialogReflexMagicEdit, saveDialogReflexMiscEdit, saveDialogReflexTempEdit;
    EditText saveDialogWillTotalEdit, saveDialogWillBaseEdit, saveDialogWillAbilityEdit, saveDialogWillMagicEdit, saveDialogWillMiscEdit, saveDialogWillTempEdit;

    public SavesDialog(Activity activity, SavesDialogListener target, Abilities abilities) {
        this.activity = activity;
        this.target = target;
        this.abilities = abilities;
    }

    public static interface SavesDialogListener {
        void OnSavesPositive(Abilities abilities);
        void OnSavesNegative();
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_saves_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_saves, null);

        saveDialogFortTotalEdit = (EditText) view.findViewById(R.id.saveDialogFortTotalEdit);
        saveDialogFortBaseEdit = (EditText) view.findViewById(R.id.saveDialogFortBaseEdit);
        saveDialogFortAbilityEdit = (EditText) view.findViewById(R.id.saveDialogFortAbilityEdit);
        saveDialogFortMagicEdit = (EditText) view.findViewById(R.id.saveDialogFortMagicEdit);
        saveDialogFortMiscEdit = (EditText) view.findViewById(R.id.saveDialogFortMiscEdit);
        saveDialogFortTempEdit = (EditText) view.findViewById(R.id.saveDialogFortTempEdit);
        getSaveValues(saveDialogFortTotalEdit, saveDialogFortBaseEdit, saveDialogFortAbilityEdit, saveDialogFortMagicEdit, saveDialogFortMiscEdit, saveDialogFortTempEdit, FORT);

        saveDialogReflexTotalEdit = (EditText) view.findViewById(R.id.saveDialogReflexTotalEdit);
        saveDialogReflexBaseEdit = (EditText) view.findViewById(R.id.saveDialogReflexBaseEdit);
        saveDialogReflexAbilityEdit = (EditText) view.findViewById(R.id.saveDialogReflexAbilityEdit);
        saveDialogReflexMagicEdit = (EditText) view.findViewById(R.id.saveDialogReflexMagicEdit);
        saveDialogReflexMiscEdit = (EditText) view.findViewById(R.id.saveDialogReflexMiscEdit);
        saveDialogReflexTempEdit = (EditText) view.findViewById(R.id.saveDialogReflexTempEdit);
        getSaveValues(saveDialogReflexTotalEdit, saveDialogReflexBaseEdit, saveDialogReflexAbilityEdit, saveDialogReflexMagicEdit, saveDialogReflexMiscEdit, saveDialogReflexTempEdit, REFLEX);

        saveDialogWillTotalEdit = (EditText) view.findViewById(R.id.saveDialogWillTotalEdit);
        saveDialogWillBaseEdit = (EditText) view.findViewById(R.id.saveDialogWillBaseEdit);
        saveDialogWillAbilityEdit = (EditText) view.findViewById(R.id.saveDialogWillAbilityEdit);
        saveDialogWillMagicEdit = (EditText) view.findViewById(R.id.saveDialogWillMagicEdit);
        saveDialogWillMiscEdit = (EditText) view.findViewById(R.id.saveDialogWillMiscEdit);
        saveDialogWillTempEdit = (EditText) view.findViewById(R.id.saveDialogWillTempEdit);
        getSaveValues(saveDialogWillTotalEdit, saveDialogWillBaseEdit, saveDialogWillAbilityEdit, saveDialogWillMagicEdit, saveDialogWillMiscEdit, saveDialogWillTempEdit, WILL);

        setTextWatchers();

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setSaveValues(saveDialogFortTotalEdit, saveDialogFortBaseEdit, saveDialogFortAbilityEdit, saveDialogFortMagicEdit, saveDialogFortMiscEdit, saveDialogFortTempEdit, FORT);
                setSaveValues(saveDialogReflexTotalEdit, saveDialogReflexBaseEdit, saveDialogReflexAbilityEdit, saveDialogReflexMagicEdit, saveDialogReflexMiscEdit, saveDialogReflexTempEdit, REFLEX);
                setSaveValues(saveDialogWillTotalEdit, saveDialogWillBaseEdit, saveDialogWillAbilityEdit, saveDialogWillMagicEdit, saveDialogWillMiscEdit, saveDialogWillTempEdit, WILL);
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

    private void getSaveValues(EditText totalEdit, EditText baseEdit, EditText abilityEdit, EditText magicEdit, EditText miscEdit, EditText tempEdit, int which) {
        int[] array;
        switch (which) {
            case FORT:
                array = abilities.getFortArray();
                break;
            case REFLEX:
                array = abilities.getReflexArray();
                break;
            case WILL:
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

    private void setSaveValues(EditText totalEdit, EditText baseEdit, EditText abilityEdit, EditText magicEdit, EditText miscEdit, EditText tempEdit, int which) {
        int total=0, base=0, ability=0, magic=0, misc=0, temp=0;
        int[] array = new int[]{0, 0, 0, 0, 0, 0};

        try {
            total = Integer.parseInt(totalEdit.getText().toString());
            base = Integer.parseInt(baseEdit.getText().toString());
            ability = Integer.parseInt(abilityEdit.getText().toString());
            magic = Integer.parseInt(magicEdit.getText().toString());
            misc = Integer.parseInt(miscEdit.getText().toString());
            temp = Integer.parseInt(tempEdit.getText().toString());
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log(TAG +ex.toString());
        }
        array[Abilities.SAVE_TOTAL] = total;
        array[Abilities.SAVE_BASE] = base;
        array[Abilities.SAVE_ABILITY] = ability;
        array[Abilities.SAVE_MAGIC] = magic;
        array[Abilities.SAVE_MISC] = misc;
        array[Abilities.SAVE_TEMP] = temp;
        switch (which) {
            case FORT:
                abilities.setFortArray(array);
                break;
            case REFLEX:
                abilities.setReflexArray(array);
                break;
            case WILL:
                abilities.setWillArray(array);
                break;
        }
    }

    private void setTextWatchers() {
        saveDialogFortBaseEdit.addTextChangedListener(new SavesTextWatcher(FORT));
        saveDialogFortAbilityEdit.addTextChangedListener(new SavesTextWatcher(FORT));
        saveDialogFortMagicEdit.addTextChangedListener(new SavesTextWatcher(FORT));
        saveDialogFortMiscEdit.addTextChangedListener(new SavesTextWatcher(FORT));
        saveDialogFortTempEdit.addTextChangedListener(new SavesTextWatcher(FORT));

        saveDialogReflexBaseEdit.addTextChangedListener(new SavesTextWatcher(REFLEX));
        saveDialogReflexAbilityEdit.addTextChangedListener(new SavesTextWatcher(REFLEX));
        saveDialogReflexMagicEdit.addTextChangedListener(new SavesTextWatcher(REFLEX));
        saveDialogReflexMiscEdit.addTextChangedListener(new SavesTextWatcher(REFLEX));
        saveDialogReflexTempEdit.addTextChangedListener(new SavesTextWatcher(REFLEX));

        saveDialogWillBaseEdit.addTextChangedListener(new SavesTextWatcher(WILL));
        saveDialogWillAbilityEdit.addTextChangedListener(new SavesTextWatcher(WILL));
        saveDialogWillMagicEdit.addTextChangedListener(new SavesTextWatcher(WILL));
        saveDialogWillMiscEdit.addTextChangedListener(new SavesTextWatcher(WILL));
        saveDialogWillTempEdit.addTextChangedListener(new SavesTextWatcher(WILL));
    }

    private class SavesTextWatcher implements TextWatcher {

        int which;
        public SavesTextWatcher(int which) {
            this.which = which;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int[] array = new int[]{0, 0, 0, 0, 0, 0};
            int total = 0;
            switch (which) {
                case FORT:
                    setSaveValues(saveDialogFortTotalEdit, saveDialogFortBaseEdit, saveDialogFortAbilityEdit, saveDialogFortMagicEdit, saveDialogFortMiscEdit, saveDialogFortTempEdit, FORT);
                    array = abilities.getFortArray();
                    break;
                case REFLEX:
                    setSaveValues(saveDialogReflexTotalEdit, saveDialogReflexBaseEdit, saveDialogReflexAbilityEdit, saveDialogReflexMagicEdit, saveDialogReflexMiscEdit, saveDialogReflexTempEdit, REFLEX);
                    array = abilities.getReflexArray();
                    break;
                case WILL:
                    setSaveValues(saveDialogWillTotalEdit, saveDialogWillBaseEdit, saveDialogWillAbilityEdit, saveDialogWillMagicEdit, saveDialogWillMiscEdit, saveDialogWillTempEdit, WILL);
                    array = abilities.getWillArray();
                    break;
            }
            for (int j = 0; j<array.length; j++) {
                if (j != Abilities.SAVE_TOTAL) {
                    total += array[j];
                }
            }
            if (which == FORT) {
                abilities.setFort(total, Abilities.SAVE_TOTAL);
                saveDialogFortTotalEdit.setText(String.valueOf(total));
            } else if(which == REFLEX) {
                abilities.setReflex(total, Abilities.SAVE_TOTAL);
                saveDialogReflexTotalEdit.setText(String.valueOf(total));
            }else if(which == WILL) {
                abilities.setWill(total, Abilities.SAVE_TOTAL);
                saveDialogWillTotalEdit.setText(String.valueOf(total));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }
}
