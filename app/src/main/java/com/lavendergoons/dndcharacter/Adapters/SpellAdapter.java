package com.lavendergoons.dndcharacter.Adapters;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Fragments.SpellFragment;
import com.lavendergoons.dndcharacter.Fragments.SpellListFragment;
import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;


/**
 * Adapter for Spell RecyclerView
 */

public class SpellAdapter extends RecyclerView.Adapter<SpellAdapter.ViewHolder> {

    private SpellListFragment spellListFragment;
    private ArrayList<Spell> mDataset;

    public SpellAdapter(SpellListFragment fragment, ArrayList<Spell> dataset) {
        this.spellListFragment = fragment;
        this.mDataset = dataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View cardview;
        TextView cardSpellNameValue, cardSpellLevelValue, cardSpellRangeValue, cardSpellDurationValue, cardSpellTypeValue;
        public ViewHolder(View view) {
            super(view);
            this.cardview = view;
            cardSpellNameValue = (TextView) view.findViewById(R.id.cardSpellNameValue);
            cardSpellLevelValue = (TextView) view.findViewById(R.id.cardSpellLevelValue);
            cardSpellTypeValue = (TextView) view.findViewById(R.id.cardSpellTypeValue);
            cardSpellRangeValue = (TextView) view.findViewById(R.id.cardSpellRangeValue);
            cardSpellDurationValue = (TextView) view.findViewById(R.id.cardSpellDurationValue);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_spell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cardSpellNameValue.setText(mDataset.get(position).getName());
        holder.cardSpellLevelValue.setText(String.valueOf(mDataset.get(position).getLevel()));
        holder.cardSpellTypeValue.setText(mDataset.get(position).getType());
        holder.cardSpellRangeValue.setText(mDataset.get(position).getRange());
        holder.cardSpellDurationValue.setText(mDataset.get(position).getDuration());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(mDataset.get(position), position);
            }
        });

        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onCardLongClick(mDataset.get(position));
            }
        });
    }

    private void onCardClick(Spell spell, int i) {
        launchSpellFragment(spell, i);
    }

    private boolean onCardLongClick(Spell spell) {
        Vibrator v = (Vibrator) spellListFragment.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);
        spellListFragment.deleteSpell(spell);
        return true;
    }

    private void launchSpellFragment(Spell spell, int i) {
        FragmentTransaction fragTransaction = spellListFragment.getActivity().getSupportFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_character_nav, SpellFragment.newInstance(spell, i), SpellFragment.TAG).addToBackStack(SpellFragment.TAG).commit();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
