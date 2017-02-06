package com.lavendergoons.dndcharacter.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Utils;

/**
 * Dialog for Armor to be edited
 */
public class ArmorDialog extends DialogFragment {

    public ArmorDialog() {
        super();
    }

    public static interface ArmorDialogListener {
        void OnArmorPositive(Armor armor);
        void OnArmorNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ArmorDialogListener mInterface = (ArmorDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement OnArmorAction");
        }
    }

    public static void showArmorDialog(final Activity activity, final ArmorDialogListener target, final Armor armor) {
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
        final EditText armorQuantityEdit = (EditText) view.findViewById(R.id.armorQuantityEdit);

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
            armorQuantityEdit.setText(String.valueOf(armor.getQuantity()));
        } else {
            // Make quantity 1 by default
            armorQuantityEdit.setText(activity.getString(R.string.one));
        }

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO Update onClicks
                boolean exceptionCheck = false;

                String name = armorNameEdit.getText().toString();
                String type = armorTypeEdit.getText().toString();
                String properties = armorPropertiesEdit.getText().toString();
                long ac = -1;
                long dex = -1;
                long check = -1;
                long spell = -1;
                long speed = -1;
                long weight = -1;
                long quantity = 1;
                try {
                    ac = Long.parseLong(armorACEdit.getText().toString());
                    dex = Long.parseLong(armorDexEdit.getText().toString());
                    check = Long.parseLong(armorCheckEdit.getText().toString());
                    spell = Long.parseLong(armorSpellEdit.getText().toString());
                    weight = Long.parseLong(armorWeightEdit.getText().toString());
                    speed = Long.parseLong(armorSpeedEdit.getText().toString());
                    quantity = Long.parseLong(armorQuantityEdit.getText().toString());
                } catch (Exception ex) {
                    Log.e("PARSE", "Error parsing longs");
                    //Toast.makeText(activity, ex.toString(), Toast.LENGTH_SHORT).show();
                    exceptionCheck = true;
                }

                // Not Editing existing Armor, create new one
                if (Utils.isStringEmpty(name) && !exceptionCheck && armor == null) {
                    target.OnArmorPositive(new Armor(name, type, ac, dex, check, spell, weight, speed, properties, quantity));
                } else if (Utils.isStringEmpty(name) && !exceptionCheck && armor != null) {
                    armor.setName(name);
                    armor.setType(type);
                    armor.setAcBonus(ac);
                    armor.setMaxDex(dex);
                    armor.setCheckPenalty(check);
                    armor.setSpellFailure(spell);
                    armor.setWeight(weight);
                    armor.setSpeed(speed);
                    armor.setProperties(properties);
                    armor.setQuantity(quantity);
                    target.OnArmorPositive(null);
                } else {
                    Toast.makeText(activity, activity.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                }
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
