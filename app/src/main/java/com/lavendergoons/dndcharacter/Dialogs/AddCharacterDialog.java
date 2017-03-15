package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Activities.CharacterListActivity;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Utils;

/**
 * Initial simple Character creation dialog.
 */
public class AddCharacterDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private EditText nameEdit, levelEdit;

    public AddCharacterDialog() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout dialogLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setLayoutParams(params);
        dialogLayout.setPadding(2, 2, 2, 2);

        nameEdit = new EditText(getActivity());
        nameEdit.setHint(R.string.hint_name);
        nameEdit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        levelEdit = new EditText(getActivity());
        levelEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        levelEdit.setHint(R.string.hint_level);

        dialogLayout.addView(nameEdit, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialogLayout.addView(levelEdit, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        builder.setTitle(getString(R.string.title_add_character));
        builder.setView(dialogLayout);
        builder.setPositiveButton(R.string.ok, this).setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static interface OnCharacterCompleteListener {
        void onCharacterComplete(Character character);
    }

    @Override
    public void onClick(DialogInterface dialog, int position) {
        if (!Utils.isStringEmpty(nameEdit.getText().toString()) && !Utils.isStringEmpty(levelEdit.getText().toString())) {
            Character character = new Character(nameEdit.getText().toString(), Integer.parseInt(levelEdit.getText().toString()));
            CharacterListActivity activity = (CharacterListActivity) getActivity();
            activity.onCharacterComplete(character);
            dialog.dismiss();
        } else {
            Toast.makeText(getContext(), getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            OnCharacterCompleteListener mListener = (OnCharacterCompleteListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCharacterCompleteListener");
        }
    }
}
