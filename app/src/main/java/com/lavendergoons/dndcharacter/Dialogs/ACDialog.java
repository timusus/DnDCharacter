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
 * Dialog to Edit AC
 */

public class ACDialog extends DialogFragment {

    public ACDialog() {
        super();
    }

    public static interface ACDialogListener {
        void OnACPositive();
        void OnACNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ACDialog.ACDialogListener mInterface = (ACDialog.ACDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement ACDialogListener");
        }
    }

    public static void showACDialog(Activity activity, final ACDialogListener target, final Abilities abilities) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_ac_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ac, null);

        //TODO Add text change listener
        final EditText acArmorEdit = (EditText) view.findViewById(R.id.acDialogArmorEdit);
        final EditText acShieldEdit = (EditText) view.findViewById(R.id.acDialogShieldEdit);
        final EditText acDexEdit = (EditText) view.findViewById(R.id.acDialogDexEdit);
        final EditText acSizeEdit = (EditText) view.findViewById(R.id.acDialogSizeEdit);
        final EditText acNaturalEdit = (EditText) view.findViewById(R.id.acDialogNaturalEdit);
        final EditText acDeflectionEdit = (EditText) view.findViewById(R.id.acDialogDeflectionEdit);
        final EditText acMiscEdit = (EditText) view.findViewById(R.id.acDialogMiscEdit);
        final EditText acTotalEdit = (EditText) view.findViewById(R.id.acDialogTotalEdit);

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnACPositive();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnACNegative();
            }
        });

        builder.create().show();
    }
}
