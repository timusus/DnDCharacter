package com.lavendergoons.dndcharacter.Utils;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Activities.CharacterListActivity;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

/**
 * RecyclerView list adapter for Character list.
 */
public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private ArrayList<Character> mDataset;
    private Context mContext;

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

    public CharacterListAdapter(Context context, ArrayList<Character> dataset) {
        this.mContext = context;
        this.mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_character_list, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNameTextView.setText(mDataset.get(position).getName());
        holder.mLevelTextView.setText(String.valueOf(mDataset.get(position).getLevel()));
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
        String name = holder.mNameTextView.getText().toString();
        CharacterListActivity activity = (CharacterListActivity) mContext;
        activity.onCharacterClick(name);
    }

    private void onCardLongClick(ViewHolder holder) {
        //TODO Clean up click functions
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);
        ConfirmationDialog.showConfirmDialog(mContext, "Are you sure long click?", (CharacterListActivity)mContext);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static interface OnCharacterClickListener {
        void onCharacterClick(String name);
    }
}
