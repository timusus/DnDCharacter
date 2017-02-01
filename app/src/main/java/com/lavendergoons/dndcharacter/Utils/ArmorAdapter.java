package com.lavendergoons.dndcharacter.Utils;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Dialogs.ArmorDialog;
import com.lavendergoons.dndcharacter.Fragments.ArmorFragment;
import com.lavendergoons.dndcharacter.Objects.Armor;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;


/**
 * Adapter for Armor Recycler View.
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
            this.cardView = view;
            cardArmorName = (TextView) view.findViewById(R.id.cardArmorName);
            cardArmorType = (TextView) view.findViewById(R.id.cardArmorType);
            cardArmorAC = (TextView) view.findViewById(R.id.cardArmorAC);
            cardArmorDex = (TextView) view.findViewById(R.id.cardArmorDex);
            cardArmorCheck = (TextView) view.findViewById(R.id.cardArmorCheck);
            cardArmorSpell = (TextView) view.findViewById(R.id.cardArmorSpell);
            cardArmorSpeed = (TextView) view.findViewById(R.id.cardArmorSpeed);
            cardArmorWeight = (TextView) view.findViewById(R.id.cardArmorWeight);
        }
    }

    public ArmorAdapter(ArmorFragment fragment, ArrayList<Armor> dataset) {
        this.armorFragment = fragment;
        this.mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_armor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cardArmorName.setText(mDataset.get(position).getName());
        holder.cardArmorType.setText(mDataset.get(position).getType());
        holder.cardArmorAC.setText(String.valueOf(mDataset.get(position).getAcBonus()));
        holder.cardArmorDex.setText(String.valueOf(mDataset.get(position).getMaxDex()));
        holder.cardArmorCheck.setText(String.valueOf(mDataset.get(position).getCheckPenalty()));
        holder.cardArmorSpell.setText(String.valueOf(mDataset.get(position).getSpellFailure()));
        holder.cardArmorSpeed.setText(String.valueOf(mDataset.get(position).getSpeed()));
        holder.cardArmorWeight.setText(String.valueOf(mDataset.get(position).getWeight()));

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onCardLongClick(mDataset.get(position));
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(mDataset.get(position));
            }
        });
    }

    private boolean onCardLongClick(Armor armor) {
        Vibrator v = (Vibrator) armorFragment.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);
        armorFragment.deleteArmor(armor);
        return true;
    }

    private void onCardClick(Armor armor) {
        ArmorDialog.showArmorDialog(armorFragment.getActivity(), armorFragment, armor);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
