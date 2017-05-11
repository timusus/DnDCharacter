package com.lavendergoons.dndcharacter.Adapters;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Dialogs.ItemGeneralDialog;
import com.lavendergoons.dndcharacter.Objects.Item;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;


/**
 * Adapter for ItemsGeneral Recycler View.
 */

public class ItemsGeneralAdapter extends RecyclerView.Adapter<ItemsGeneralAdapter.ViewHolder> {

    private ArrayList<Item> mDataset;
    private Fragment fragment;
    private Context context;
    private ItemsGeneralAdapterListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardview;
        public TextView itemCardNameValue, itemCardQuantityValue, itemCardWeightValue;
        public ViewHolder(View view) {
            super(view);
            this.cardview = view;
            itemCardNameValue = (TextView) view.findViewById(R.id.itemCardNameValue);
            itemCardQuantityValue = (TextView) view.findViewById(R.id.itemCardQuantityValue);
            itemCardWeightValue = (TextView) view.findViewById(R.id.itemCardWeightValue);
        }
    }


    public ItemsGeneralAdapter(Fragment fragment, ArrayList<Item> dataset) {
        this.fragment = fragment;
        this.mDataset = dataset;
        this.context = fragment.getContext();
        if (fragment instanceof ItemsGeneralAdapterListener) {
            this.listener = (ItemsGeneralAdapterListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString()
                + " must implement ItemsGeneralAdapterListener.");
        }
    }

    public interface ItemsGeneralAdapterListener {
        void removeItem(Item item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_general, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemCardNameValue.setText(mDataset.get(position).getName());
        holder.itemCardQuantityValue.setText(String.valueOf(mDataset.get(position).getQuantity()));
        holder.itemCardWeightValue.setText(String.valueOf(mDataset.get(position).getWeight()));

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(mDataset.get(position));
            }
        });
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onCardLongClick(mDataset.get(position));
            }
        });
    }


    private void onCardClick(Item item) {
        new ItemGeneralDialog(fragment, item).showDialog();
    }

    private boolean onCardLongClick(Item item) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);
        listener.removeItem(item);
        return true;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
