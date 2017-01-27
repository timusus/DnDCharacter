package com.lavendergoons.dndcharacter.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Fragments.AttributesFragment;
import com.lavendergoons.dndcharacter.Objects.Attribute;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

/**
 * Adapter for attributes RecyclerView
 */

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    private AttributesFragment attributesFragment;
    private ArrayList<Attribute> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView attributeNameView;
        public EditText attributeEdit;
        public ViewHolder(View view) {
            super(view);
            this.cardView = view;
            this.attributeNameView = (TextView) view.findViewById(R.id.attributeNameView);
            this.attributeEdit = (EditText) view.findViewById(R.id.attributeEdit);
        }
    }

    public AttributesAdapter(AttributesFragment attributesFragment, ArrayList<Attribute> dataset) {
        this.attributesFragment = attributesFragment;
        this.mDataset = dataset;
    }

    @Override
    public AttributesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(attributesFragment.getContext()).inflate(R.layout.cardview_attributes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.attributeNameView.setText(mDataset.get(position).getName());
        holder.attributeEdit.setHint(mDataset.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
