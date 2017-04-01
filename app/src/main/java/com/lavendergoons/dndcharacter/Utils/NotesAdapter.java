package com.lavendergoons.dndcharacter.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Fragments.NotesListFragment;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private NotesListFragment fragment;
    private ArrayList<Note> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView notesTitleView;

        public ViewHolder(View view) {
            super(view);
            this.cardView = view;
            notesTitleView = (TextView) view.findViewById(R.id.notesTitleView);
        }
    }

    public NotesAdapter(NotesListFragment fragment, ArrayList<Note> notes) {
        this.fragment = fragment;
        this.mDataset = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.notesTitleView.setText(mDataset.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(position);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onCardLongClick(position);
            }
        });
    }

    private void onCardClick(int position) {

    }

    private boolean onCardLongClick(int position) {
        return true;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
