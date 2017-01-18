package com.lavendergoons.dndcharacter.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lavendergoons.dndcharacter.Activities.CharacterListActivity;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * RecyclerView list adapter for Character list.
 */
public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {


    private ArrayList<Character> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public TextView mNameTextView;
        public TextView mLevelTextView;
        public ViewHolder(View view) {
            super(view);
            this.cardView = view;
            mNameTextView = (TextView) view.findViewById(R.id.characterName);
            mLevelTextView = (TextView) view.findViewById(R.id.characterLevel);
        }
    }

    public CharacterListAdapter(Context context, ArrayList<Character> dataset) {
        this.context = context;
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_character_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNameTextView.setText(mDataset.get(position).getName());
        holder.mLevelTextView.setText(String.valueOf(mDataset.get(position).getLevel()));
        //holder.cardView.setOnLongClickListener(this);
        //holder.cardView.setOnClickListener(this);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(holder);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onCardLongClick(holder);
                return true;
            }
        });
    }

    private void onCardClick(ViewHolder holder) {
        //TODO Clean up click functions
        int position = holder.getAdapterPosition();
        String name = holder.mNameTextView.getText().toString();
        Toast.makeText(context, "pos "+position+" name "+name, Toast.LENGTH_SHORT).show();
        CharacterListActivity activity = (CharacterListActivity) context;
        activity.onCharacterClick(name);
    }

    private void onCardLongClick(ViewHolder holder) {
        //TODO Clean up click functions
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);

        int position = holder.getAdapterPosition();
        String name = holder.mNameTextView.getText().toString();
        Toast.makeText(context, "Long - pos "+position+" name "+name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static interface OnCharacterClickListener {
        void onCharacterClick(String name);
    }
}
