package com.lavendergoons.dndcharacter.Utils;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Fragments.AttributesFragment;
import com.lavendergoons.dndcharacter.Objects.Attribute;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Adapter for attributes RecyclerView
 */

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    private AttributesFragment attributesFragment;
    private ArrayList<String> mDataset = new ArrayList<>(Arrays.asList(Constants.ATTRIBUTES));
    private ArrayList<String> attributeList;
    private Character character;
    private final int NAME = 0;
    private final int LEVEL = 2;

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

    public AttributesAdapter(AttributesFragment attributesFragment, ArrayList<String> dataset, Character character) {
        this.attributesFragment = attributesFragment;
        this.attributeList = dataset;
        this.character = character;
    }

    @Override
    public AttributesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(attributesFragment.getContext()).inflate(R.layout.cardview_attributes, parent, false);
        return new ViewHolder(view, new AttributeTextWatcher());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.attributeNameView.setText(mDataset.get(position));
        holder.attributeEdit.setHint(mDataset.get(position));
        holder.textWatcher.updatePosition(position);
        if (attributeList.size() > 0) {
            if (position == NAME) {
                holder.attributeEdit.setText(character.getName());
            } else if (position == LEVEL) {
                holder.attributeEdit.setText(String.valueOf(character.getLevel()));
            } else {
                holder.attributeEdit.setText(attributeList.get(position));
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

    // Stores edittext data on text change
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
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
