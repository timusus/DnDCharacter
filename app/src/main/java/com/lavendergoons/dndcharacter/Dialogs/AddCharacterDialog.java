package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Fragments.CharacterListFragment;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Utils;

/**
 * Initial simple SimpleCharacter creation dialog.
 */
public class AddCharacterDialog extends DialogFragment {

    private EditText nameEdit, levelEdit;
    private CharacterListFragment fragment;

    public AddCharacterDialog() {
        super();
    }

    public static void showAddCharacterDialog(final Activity activity, final AddCharacterDialog.OnCharacterCompleteListener target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LinearLayout dialogLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText nameEdit, levelEdit;

        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setLayoutParams(params);
        dialogLayout.setPadding(2, 2, 2, 2);

        nameEdit = new EditText(activity);
        nameEdit.setHint(R.string.hint_name);
        nameEdit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        levelEdit = new EditText(activity);
        levelEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        levelEdit.setHint(R.string.hint_level);

        dialogLayout.addView(nameEdit, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialogLayout.addView(levelEdit, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        builder.setTitle(activity.getString(R.string.title_add_character));
        builder.setView(dialogLayout);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!Utils.isStringEmpty(nameEdit.getText().toString()) && !Utils.isStringEmpty(levelEdit.getText().toString())) {
                    SimpleCharacter simpleCharacter = new SimpleCharacter(nameEdit.getText().toString(), Integer.parseInt(levelEdit.getText().toString()));
                    target.onCharacterComplete(simpleCharacter);
                } else {
                    Toast.makeText(activity, activity.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }


    public static interface OnCharacterCompleteListener {
        void onCharacterComplete(SimpleCharacter simpleCharacter);
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
