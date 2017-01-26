package com.lavendergoons.dndcharacter.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Fragments.AttributesFragment;

import java.util.ArrayList;

/**
 * Created by rtas on 2017-01-25.
 */

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    private AttributesFragment attributesFragment;
    private ArrayList<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public ViewHolder(View view) {
            super(view);
            this.cardView = view;

        }
    }

    public AttributesAdapter(AttributesFragment attributesFragment, ArrayList<String> dataset) {
        this.attributesFragment = attributesFragment;
        this.mDataset = dataset;
    }

    @Override
    public AttributesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
