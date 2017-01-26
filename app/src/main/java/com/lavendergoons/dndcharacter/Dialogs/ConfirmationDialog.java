package com.lavendergoons.dndcharacter.Dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.lavendergoons.dndcharacter.R;

/**
 * Generic Confirmation Dialog to confirm
 * Sensitive actions.
 */

public class ConfirmationDialog extends DialogFragment {

    public ConfirmationDialog() {
        super();
    }

    public static interface ConfirmationDialogInterface {
        void ConfirmDialogOk();
        void ConfirmDialogCancel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ConfirmationDialogInterface mInterface = (ConfirmationDialogInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement ConfirmationDialogInterface");
        }
    }

    public static void showConfirmDialog(Context context, String mMessage, final ConfirmationDialog.ConfirmationDialogInterface target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.confirm));

        String message = (mMessage.length() > 0) ? mMessage : context.getString(R.string.title_generic_confirm);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.ConfirmDialogOk();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.ConfirmDialogCancel();
            }
        });
        builder.create().show();
    }
}
