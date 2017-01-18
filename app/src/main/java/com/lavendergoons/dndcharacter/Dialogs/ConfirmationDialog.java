package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.lavendergoons.dndcharacter.R;

/**
 * Generic Confirmation Dialog to confirm
 * Sensitive actions.
 */

public class ConfirmationDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public static final String MESSAGE_KEY = "MESSAGE";
    private ConfirmationDialogInterface mInterface;
    private String mMessage;

    public ConfirmationDialog() {
        super();
    }

    public static ConfirmationDialog newInstance(String mMessage) {
        Bundle args = new Bundle();
        ConfirmationDialog fragment = new ConfirmationDialog();
        args.putString(MESSAGE_KEY, mMessage);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.confirm));

        String message = (mMessage.length() > 0) ? mMessage : getString(R.string.title_generic_confirm);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.yes, this);
        builder.setNegativeButton(R.string.no, null);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }

    public static interface ConfirmationDialogInterface {
        ConfirmationDialog newInstance(String mMessage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInterface = (ConfirmationDialogInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement ConfirmationDialogInterface");
        }
    }
}
