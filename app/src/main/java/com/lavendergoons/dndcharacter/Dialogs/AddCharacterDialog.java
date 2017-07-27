package com.lavendergoons.dndcharacter.Dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Utils;

/**
 * Initial simple SimpleCharacter creation dialog.
 */
public class AddCharacterDialog {

    @SuppressLint("InflateParams")
    public static void showAddCharacterDialog(final Activity activity, final OnCharacterCompleteListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View rootView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_character, null);

        final EditText nameEdit = (EditText) rootView.findViewById(R.id.nameEdit);
        final EditText levelEdit = (EditText) rootView.findViewById(R.id.levelEdit);

        builder.setTitle(activity.getString(R.string.title_add_character));
        builder.setView(rootView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!Utils.isStringEmpty(nameEdit.getText().toString()) && !Utils.isStringEmpty(levelEdit.getText().toString())) {
                    SimpleCharacter simpleCharacter = new SimpleCharacter(nameEdit.getText().toString(), Integer.parseInt(levelEdit.getText().toString()));
                    listener.onCharacterComplete(simpleCharacter);
                } else {
                    Toast.makeText(activity, activity.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton(R.string.cancel, null);

        builder.show();
    }

    public interface OnCharacterCompleteListener {
        void onCharacterComplete(SimpleCharacter simpleCharacter);
    }
}
