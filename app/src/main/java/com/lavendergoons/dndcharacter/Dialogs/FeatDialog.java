package com.lavendergoons.dndcharacter.Dialogs;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Objects.Feat;
import com.lavendergoons.dndcharacter.R;


public class FeatDialog {

    private Context context;
    private FeatDialogListener target;

    public FeatDialog(Fragment fragment) {
        this.context = fragment.getContext();
        if (fragment instanceof FeatDialogListener) {
            this.target = (FeatDialogListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement FeatDialogListener");
        }
    }

    public FeatDialog(Context context) {
        this.context = context;
        if (context instanceof FeatDialogListener) {
            this.target = (FeatDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FeatDialogListener");
        }
    }

    public interface FeatDialogListener {
        void OnFeatPositive(Feat feat);
        void OnFeatNegative(Feat feat);
    }

    public void showDialog(final Feat feat) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.title_select_feat_type));
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final EditText contentEdit;
        final Spinner typeSpinner;

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(2, 2, 2, 2);

        contentEdit = new EditText(context);
        contentEdit.setHint(context.getString(R.string.hint_name));
        contentEdit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        typeSpinner = new Spinner(context);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.feat_type_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        linearLayout.addView(contentEdit, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Margins have to be large
        spinnerParams.setMargins(0,50,0,50);
        linearLayout.addView(typeSpinner, spinnerParams);

        if (feat != null) {
            contentEdit.setText(feat.getContent());
            int index = -1;
            if (feat.getType().equals(Feat.FEAT)) {
                index = Feat.FEAT_INDEX;
            } else if (feat.getType().equals(Feat.SPECIAL_ABILITIES)) {
                index = Feat.SPEC_INDEX;
            } else if (feat.getType().equals(Feat.LANGUAGE)) {
                index = Feat.LANG_INDEX;
            }
            typeSpinner.setSelection(index);
        }

        builder.setView(linearLayout).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String type = typeSpinner.getSelectedItem().toString();
                String content = contentEdit.getText().toString();
                if (type.length() > 0 && content.length() > 0) {
                    if (feat != null) {
                        feat.setContent(content);
                        feat.setType(type);
                        target.OnFeatPositive(null);
                    } else {
                        target.OnFeatPositive(new Feat(type, content));
                    }
                } else {
                    Toast.makeText(context, R.string.warning_enter_required_fields, Toast.LENGTH_LONG).show();
                }
            }
        }).create().show();
    }
}
