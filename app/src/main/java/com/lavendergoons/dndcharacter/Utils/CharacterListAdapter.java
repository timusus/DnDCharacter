package com.lavendergoons.dndcharacter.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

/**
 * Created by rtas on 2017-01-13.
 */
public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private ArrayList<Character> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public TextView mLevelTextView;
        public ViewHolder(View view) {
            super(view);
            mNameTextView = (TextView) view.findViewById(R.id.characterName);
            mLevelTextView = (TextView) view.findViewById(R.id.characterLevel);
        }
    }

    public CharacterListAdapter(ArrayList<Character> dataset) {
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_character_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNameTextView.setText(mDataset.get(position).name);
        holder.mNameTextView.setText(mDataset.get(position).level);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
