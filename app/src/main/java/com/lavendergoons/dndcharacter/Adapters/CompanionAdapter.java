package com.lavendergoons.dndcharacter.Adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Objects.Companion;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

/**
 * Adapter for Companion RecyclerView
 */

public class CompanionAdapter extends RecyclerView.Adapter<CompanionAdapter.ViewHolder> {

    private Fragment fragment;
    private CompanionAdapterListener listener;
    private ArrayList<Companion> mDataset;

    public CompanionAdapter(Fragment fragment, ArrayList<Companion> dataset) {
        this.mDataset = dataset;
        this.fragment = fragment;
        if (fragment instanceof CompanionAdapterListener) {
            this.listener = (CompanionAdapterListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString()
                + " must implement CompanionAdapterListener.");
        }
    }

    public interface CompanionAdapterListener {
        void removeCompanion(Companion companion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_simple_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
