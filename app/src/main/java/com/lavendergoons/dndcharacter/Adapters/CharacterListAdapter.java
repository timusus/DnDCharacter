package com.lavendergoons.dndcharacter.Adapters;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Fragments.CharacterListFragment;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;

/**
 * RecyclerView list adapter for SimpleCharacter list.
 */
public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private ArrayList<SimpleCharacter> mDataset;
    private Context mContext;
    private CharacterListFragment fragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public TextView mNameTextView;
        public TextView mLevelTextView;
        public ViewHolder(View view) {
            super(view);
            this.cardView = view;
            mNameTextView = (TextView) view.findViewById(R.id.characterListName);
            mLevelTextView = (TextView) view.findViewById(R.id.characterListLevel);
        }
    }

    public CharacterListAdapter(Context context, ArrayList<SimpleCharacter> dataset) {
        this.mContext = context;
        this.mDataset = dataset;
    }

    public CharacterListAdapter(CharacterListFragment fragment, ArrayList<SimpleCharacter> dataset) {
        this.fragment = fragment;
        this.mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_character_list, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mNameTextView.setText(mDataset.get(position).getName());
        holder.mLevelTextView.setText(String.valueOf(mDataset.get(position).getLevel()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(holder, position);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onCardLongClick(holder, position);
                return true;
            }
        });
    }

    private void onCardClick(ViewHolder holder, int position) {
        String name = holder.mNameTextView.getText().toString();
        fragment.onCharacterClick(name);
    }

    private void onCardLongClick(ViewHolder holder, int position) {
        Vibrator v = (Vibrator) fragment.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);
        fragment.deleteCharacter(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static interface OnCharacterClickListener {
        void onCharacterClick(String name);
    }
}
