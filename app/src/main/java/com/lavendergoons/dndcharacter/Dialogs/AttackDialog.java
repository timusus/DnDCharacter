package com.lavendergoons.dndcharacter.Dialogs;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Objects.Attack;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Utils;

/**
 * Dialog for Editing and Creating Attacks
 */

public class AttackDialog extends DialogFragment {

    public AttackDialog() {
        super();
    }

    public static interface AttackDialogListener {
        void OnAttackDialogPositive(Attack attack);
        void OnAttackDialogNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            AttackDialog.AttackDialogListener mInterface = (AttackDialog.AttackDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement AttackDialogListener");
        }
    }

    public static void showAttackDialog(final Activity activity, final AttackDialog.AttackDialogListener target, final Attack attack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_attack_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_attack, null);

        final EditText atkDialogNameEdit = (EditText) view.findViewById(R.id.atkDialogNameEdit);
        final EditText atkDialogBonusEdit = (EditText) view.findViewById(R.id.atkDialogBonusEdit);
        final EditText atkDialogDamageEdit = (EditText) view.findViewById(R.id.atkDialogDamageEdit);
        final EditText atkDialogCriticalEdit = (EditText) view.findViewById(R.id.atkDialogCriticalEdit);
        final EditText atkDialogRangeEdit = (EditText) view.findViewById(R.id.atkDialogRangeEdit);
        final EditText atkDialogTypeEdit = (EditText) view.findViewById(R.id.atkDialogTypeEdit);
        final EditText atkDialogAmmoEdit = (EditText) view.findViewById(R.id.atkDialogAmmoEdit);
        final EditText atkDialogNotesEdit = (EditText) view.findViewById(R.id.atkDialogNotesEdit);

        if (attack != null) {
            atkDialogNameEdit.setText(attack.getAttack());
            atkDialogBonusEdit.setText(attack.getAttackBonus());
            atkDialogDamageEdit.setText(attack.getDamage());
            atkDialogCriticalEdit.setText(attack.getCritical());
            atkDialogRangeEdit.setText(String.valueOf(attack.getRange()));
            atkDialogTypeEdit.setText(attack.getType());
            atkDialogAmmoEdit.setText(String.valueOf(attack.getAmmo()));
            atkDialogNotesEdit.setText(attack.getNotes());
        }

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean exceptionCheck = false;
                String name = atkDialogNameEdit.getText().toString();
                String bonus = atkDialogBonusEdit.getText().toString();
                String damage = atkDialogDamageEdit.getText().toString();
                String critical = atkDialogCriticalEdit.getText().toString();
                int range = 0;
                String type = atkDialogTypeEdit.getText().toString();
                int ammo = 0;
                String notes = atkDialogNotesEdit.getText().toString();
                try {
                    range = Integer.parseInt(atkDialogRangeEdit.getText().toString());
                    ammo = Integer.parseInt(atkDialogAmmoEdit.getText().toString());
                }catch (Exception ex) {
                    Log.e("PARSE", "Error parsing attack ints");
                    exceptionCheck = true;
                }
                if (!Utils.isStringArrayEmpty(new String[]{name, bonus, damage, critical, type}) && !exceptionCheck && attack != null) {
                    attack.setAttack(name);
                    attack.setAttackBonus(bonus);
                    attack.setDamage(damage);
                    attack.setCritical(critical);
                    attack.setRange(range);
                    attack.setType(type);
                    attack.setAmmo(ammo);
                    attack.setNotes(notes);
                    target.OnAttackDialogPositive(null);
                } else if (!Utils.isStringArrayEmpty(new String[]{name, bonus, damage, critical, type}) && !exceptionCheck && attack == null) {
                    target.OnAttackDialogPositive(new Attack(name, bonus, damage, critical, range, type, ammo, notes));
                } else {
                    Toast.makeText(activity, activity.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnAttackDialogNegative();
            }
        });

        builder.create().show();
    }
}
