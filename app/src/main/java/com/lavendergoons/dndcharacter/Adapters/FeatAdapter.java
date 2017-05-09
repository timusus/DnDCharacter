package com.lavendergoons.dndcharacter.Adapters;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Dialogs.FeatDialog;
import com.lavendergoons.dndcharacter.Objects.Feat;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;



/**
 * Adapter for Feats
 */

public class FeatAdapter extends RecyclerView.Adapter<FeatAdapter.ViewHolder> {

    private ArrayList<Feat> mDataset;
    private Context context;
    private Fragment fragment;
    private FeatAdapterListener listener;

    public FeatAdapter(Fragment fragment, ArrayList<Feat> mDataset) {
        this.mDataset = mDataset;
        this.context = fragment.getContext();
        this.fragment = fragment;
        if (fragment instanceof FeatAdapterListener) {
            this.listener = (FeatAdapterListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement FeatAdapterListener");
        }
    }


    public interface FeatAdapterListener {
        void removeFeat(Feat feat);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView featContent, featType;
        public ViewHolder(View view) {
            super(view);
            cardView = view;
            featContent = (TextView) view.findViewById(R.id.featContent);
            featType = (TextView) view.findViewById(R.id.featType);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_feat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.featContent.setText(mDataset.get(position).getContent());
        holder.featType.setText(mDataset.get(position).getType());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(mDataset.get(position));
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onCardLongClick(mDataset.get(position));
            }
        });
    }

    private void onCardClick(Feat feat) {
        new FeatDialog(fragment).showDialog(feat);
    }

    private boolean onCardLongClick(Feat feat) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);
        listener.removeFeat(feat);
        return true;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
