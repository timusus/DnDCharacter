package com.lavendergoons.dndcharacter.Adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.Fragments.AttributesFragment;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Adapter for attributes RecyclerView
 */

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    private Fragment fragment;
    private ArrayList<String> mDataset = new ArrayList<>(Arrays.asList(Constants.ATTRIBUTES));
    private ArrayList<String> attributeList;
    private SimpleCharacter simpleCharacter;

    private final int NAME = 0;
    private final int LEVEL = 2;
    private final int XP = 3;
    private final int AGE = 8;
    private final int HEIGHT = 10;
    private final int WEIGHT = 11;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView attributeNameView;
        public EditText attributeEdit;
        public AttributeTextWatcher textWatcher;
        public ViewHolder(View view, AttributeTextWatcher watcher) {
            super(view);
            this.cardView = view;
            this.attributeNameView = (TextView) view.findViewById(R.id.attributeNameView);
            this.attributeEdit = (EditText) view.findViewById(R.id.attributeEdit);
            this.textWatcher = watcher;
            attributeEdit.addTextChangedListener(this.textWatcher);
        }
    }

    public AttributesAdapter(Fragment fragment, ArrayList<String> dataset, SimpleCharacter simpleCharacter) {
        this.fragment = fragment;
        this.attributeList = dataset;
        this.simpleCharacter = simpleCharacter;
        if (!(fragment instanceof AttributesAdapterListener)) {
            throw new RuntimeException(fragment.toString()
                    + " must implement AttributesAdapterListener");
        }
    }

    public interface AttributesAdapterListener { }

    @Override
    public AttributesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.cardview_attributes, parent, false);
        return new ViewHolder(view, new AttributeTextWatcher());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.attributeNameView.setText(mDataset.get(position));
        holder.attributeEdit.setHint(mDataset.get(position));
        holder.textWatcher.updatePosition(position);
        if (attributeList.size() > 0) {
            if (position == NAME) {
                holder.attributeEdit.setText(simpleCharacter.getName());
            } else if (position == LEVEL) {
                holder.attributeEdit.setText(String.valueOf(simpleCharacter.getLevel()));
                holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                holder.attributeEdit.setText(attributeList.get(position));
            }

            // Set InputType of Numeric Attributes
            switch (position) {
                case XP:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case AGE:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case HEIGHT:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case WEIGHT:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<String> getAttributeList() {
        return attributeList;
    }

    // Stores EditText data on text change
    // Stops attributes from moving to the wrong card
    private class AttributeTextWatcher implements TextWatcher {
        private int position;

        public void updatePosition(int pos) {
            this.position = pos;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            attributeList.set(position, charSequence.toString());
            if (position == NAME) {
                simpleCharacter.setName(charSequence.toString());
            }
            if (position == LEVEL) {
                int lvl = simpleCharacter.getLevel();
                try {
                    lvl = Integer.parseInt(charSequence.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    FirebaseCrash.log(ex.toString());
                }
                simpleCharacter.setLevel(lvl);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
