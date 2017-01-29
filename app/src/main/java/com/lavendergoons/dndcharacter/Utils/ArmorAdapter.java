package com.lavendergoons.dndcharacter.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Fragments.ArmorFragment;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

/**
 * Created by rtas on 2017-01-28.
 */
public class ArmorAdapter extends RecyclerView.Adapter<ArmorAdapter.ViewHolder> {

    private ArrayList<Armor> mDataset;
    private ArmorFragment armorFragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public TextView cardArmorName;
        public TextView cardArmorType;
        public TextView cardArmorAC;
        public TextView cardArmorDex;
        public TextView cardArmorCheck;
        public TextView cardArmorSpell;
        public TextView cardArmorSpeed;
        public TextView cardArmorWeight;
        public ViewHolder(View view) {
            super(view);
            cardArmorName = (TextView) view.findViewById(R.id.cardArmorName);
        }
    }

    public ArmorAdapter(ArmorFragment fragment, ArrayList<Armor> dataset) {
        this.armorFragment = fragment;
        this.mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
