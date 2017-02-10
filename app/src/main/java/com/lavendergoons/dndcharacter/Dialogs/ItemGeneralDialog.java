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

import com.lavendergoons.dndcharacter.Objects.Item;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Utils;

/**
 * Dialog to create and edit items
 */

public class ItemGeneralDialog extends DialogFragment {

    public ItemGeneralDialog() {
        super();
    }

    public static interface ItemsGeneralDialogListener {
        void OnItemsPositive(Item item);
        void OnItemsNegative();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ItemGeneralDialog.ItemsGeneralDialogListener mInterface = (ItemGeneralDialog.ItemsGeneralDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +" must implement ItemsGeneralDialogListener");
        }
    }

    public static void showItemsDialog(final Activity activity, final ItemGeneralDialog.ItemsGeneralDialogListener target, final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.title_items_general_dialog));

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_item_general, null);

        final EditText itemDialogNameEdit = (EditText) view.findViewById(R.id.itemDialogNameEdit);
        final EditText itemDialogWeightEdit = (EditText) view.findViewById(R.id.itemDialogWeightEdit);
        final EditText itemDialogQuantityEdit = (EditText) view.findViewById(R.id.itemDialogQuantityEdit);

        if (item != null) {
            itemDialogNameEdit.setText(item.getName());
            itemDialogWeightEdit.setText(String.valueOf(item.getWeight()));
            itemDialogQuantityEdit.setText(String.valueOf(item.getQuantity()));
        } else {
            itemDialogQuantityEdit.setText(activity.getString(R.string.one));
        }

        builder.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean exceptionCheck = false;
                int weight = 0;
                int quantity = 0;
                String name = itemDialogNameEdit.getText().toString();
                try {
                    weight = Integer.parseInt(itemDialogWeightEdit.getText().toString());
                    quantity = Integer.parseInt(itemDialogQuantityEdit.getText().toString());
                } catch (Exception ex) {
                    Log.e("PARSE", "Error parsing item ints");
                    exceptionCheck = true;
                }

                if (!Utils.isStringEmpty(name) && !exceptionCheck && item != null) {
                    item.setName(name);
                    item.setWeight(weight);
                    item.setQuantity(quantity);
                    target.OnItemsPositive(null);
                } else if (!Utils.isStringEmpty(name) && !exceptionCheck && item == null) {
                    target.OnItemsPositive(new Item(name, weight, quantity));
                } else {
                    Toast.makeText(activity, activity.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                target.OnItemsNegative();
            }
        });

        builder.create().show();
    }
}
