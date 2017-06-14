package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.R;

import java.util.Arrays;


/**
 * Dialog to Edit AC
 */

public class ACDialog  {

    public static final String TAG = "AC_DIALOG";

    private EditText acArmorEdit, acShieldEdit, acDexEdit, acSizeEdit, acNaturalEdit, acDeflectionEdit, acMiscEdit, acTotalEdit;
    private Activity activity;
    private ACDialogListener target;
    private Abilities abilities;

    public ACDialog(Activity activity, ACDialogListener target, Abilities abilities) {
        this.activity = activity;
        this.target = target;
        this.abilities = abilities;
        Log.d(TAG, Arrays.toString(abilities.getACArray()));
    }

    public static interface ACDialogListener {
        void OnACPositive(Abilities abilities);
        void OnACNegative();
    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_ac_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ac, null);

        acArmorEdit = (EditText) view.findViewById(R.id.acDialogArmorEdit);
        acShieldEdit = (EditText) view.findViewById(R.id.acDialogShieldEdit);
        acDexEdit = (EditText) view.findViewById(R.id.acDialogDexEdit);
        acSizeEdit = (EditText) view.findViewById(R.id.acDialogSizeEdit);
        acNaturalEdit = (EditText) view.findViewById(R.id.acDialogNaturalEdit);
        acDeflectionEdit = (EditText) view.findViewById(R.id.acDialogDeflectionEdit);
        acMiscEdit = (EditText) view.findViewById(R.id.acDialogMiscEdit);
        acTotalEdit = (EditText) view.findViewById(R.id.acDialogTotalEdit);

        setTextValues();

        acArmorEdit.addTextChangedListener(new ACTextWatcher());
        acShieldEdit.addTextChangedListener(new ACTextWatcher());
        acDexEdit.addTextChangedListener(new ACTextWatcher());
        acSizeEdit.addTextChangedListener(new ACTextWatcher());
        acNaturalEdit.addTextChangedListener(new ACTextWatcher());
        acDeflectionEdit.addTextChangedListener(new ACTextWatcher());
        acMiscEdit.addTextChangedListener(new ACTextWatcher());

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                readValues();
                target.OnACPositive(abilities);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnACNegative();
            }
        });
        builder.create().show();
    }

    private void readValues() {
        int armor=0, shield=0, dex=0, size=0, natural=0, deflection=0, misc=0, total=0;
        try {
            armor = Integer.parseInt(acArmorEdit.getText().toString());
            shield = Integer.parseInt(acShieldEdit.getText().toString());
            dex = Integer.parseInt(acDexEdit.getText().toString());
            size = Integer.parseInt(acSizeEdit.getText().toString());
            natural = Integer.parseInt(acNaturalEdit.getText().toString());
            deflection = Integer.parseInt(acDeflectionEdit.getText().toString());
            misc = Integer.parseInt(acMiscEdit.getText().toString());
            total = Integer.parseInt(acTotalEdit.getText().toString());
        }catch (Exception ex) {
            ex.printStackTrace();
            FirebaseCrash.log(TAG +ex.toString());
        }
        abilities.setAC(armor, Abilities.AC_ARMOR);
        abilities.setAC(shield, Abilities.AC_SHIELD);
        abilities.setAC(dex, Abilities.AC_DEX);
        abilities.setAC(size, Abilities.AC_SIZE);
        abilities.setAC(natural, Abilities.AC_NATURAL);
        abilities.setAC(deflection, Abilities.AC_DEFLECTION);
        abilities.setAC(misc, Abilities.AC_MISC);
        abilities.setAC(total, Abilities.AC_TOTAL);
        Log.d(TAG,
                armor+" "+
                shield+" "+
                dex+" "+
                size+" "+
                natural+" "+
                deflection+" "+
                misc+" "+
                total
        );
    }

    private void setTextValues() {
        if (abilities != null) {
            acArmorEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_ARMOR)));
            acShieldEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_SHIELD)));
            acDexEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_DEX)));
            acSizeEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_SIZE)));
            acNaturalEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_NATURAL)));
            acDeflectionEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_DEFLECTION)));
            acMiscEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_MISC)));
            acTotalEdit.setText(String.valueOf(abilities.getAC(Abilities.AC_TOTAL)));
        } else {
            FirebaseCrash.log("Abilities Null In ACDialog");
        }
    }

    private class ACTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            readValues();
            int total = abilities.getAC(Abilities.AC_DEFAULT)+
            abilities.getAC(Abilities.AC_ARMOR)+
            abilities.getAC(Abilities.AC_SHIELD)+
            abilities.getAC(Abilities.AC_DEX)+
            abilities.getAC(Abilities.AC_SIZE)+
            abilities.getAC(Abilities.AC_NATURAL)+
            abilities.getAC(Abilities.AC_DEFLECTION)+
            abilities.getAC(Abilities.AC_MISC);
            abilities.setAC(total, Abilities.AC_TOTAL);
            acTotalEdit.setText(String.valueOf(total));
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }
}
