package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.R;


/**
 * Dialog for Armor to be edited
 */
public class ArmorDialog extends DialogFragment {

    public ArmorDialog() {
        super();
    }

    public static interface OnArmorAction {
        void OnArmorPositive();
        void OnArmorNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            OnArmorAction mInterface = (OnArmorAction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement OnArmorAction");
        }
    }

    public static void showArmorDialog(Activity activity, final OnArmorAction target, Armor armor) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_armor_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_armor, null);
        final EditText armorNameEdit = (EditText) view.findViewById(R.id.armorNameEdit);
        final EditText armorTypeEdit = (EditText) view.findViewById(R.id.armorTypeEdit);
        final EditText armorACEdit = (EditText) view.findViewById(R.id.armorACEdit);
        final EditText armorDexEdit = (EditText) view.findViewById(R.id.armorDexEdit);
        final EditText armorCheckEdit = (EditText) view.findViewById(R.id.armorCheckEdit);
        final EditText armorSpellEdit = (EditText) view.findViewById(R.id.armorSpellEdit);
        final EditText armorSpeedEdit = (EditText) view.findViewById(R.id.armorSpeedEdit);
        final EditText armorWeightEdit = (EditText) view.findViewById(R.id.armorWeightEdit);
        final EditText armorPropertiesEdit = (EditText) view.findViewById(R.id.armorPropertiesEdit);

        if (armor != null) {
            armorNameEdit.setText(armor.getName());
            armorTypeEdit.setText(armor.getType());
            armorACEdit.setText(String.valueOf(armor.getAcBonus()));
            armorDexEdit.setText(String.valueOf(armor.getMaxDex()));
            armorCheckEdit.setText(String.valueOf(armor.getCheckPenalty()));
            armorSpellEdit.setText(String.valueOf(armor.getSpellFailure()));
            armorSpeedEdit.setText(String.valueOf(armor.getSpeed()));
            armorWeightEdit.setText(String.valueOf(armor.getWeight()));
            armorPropertiesEdit.setText(armor.getProperties());
        }

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO Update onClicks
                target.OnArmorPositive();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnArmorNegative();
            }
        });
        builder.create().show();
    }
}
