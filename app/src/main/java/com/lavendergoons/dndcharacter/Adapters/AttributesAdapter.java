package com.lavendergoons.dndcharacter.Adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Adapter for attributes RecyclerView
 */

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder> {

    public static final String TAG = "ATTRIBUTES_ADAPTER";

    private Fragment fragment;
    private ArrayList<String> mDataset = new ArrayList<>(Arrays.asList(Constants.ATTRIBUTES));
    private ArrayList<String> attributeList;
    private SimpleCharacter simpleCharacter;

    private final int NAME = 0;
    private final int LEVEL = 2;
    private final int XP = 3;
    private final int AGE = 8;
    private final int HEIGHT = 10;
    private final int WEIGHT = 11;

    public static final String Name = "Name";
    public static final String Level = "Level";
    public static final String Xp = "XP";
    public static final String Age = "Age";
    public static final String Height = "Height";
    public static final String Weight = "Weight";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView attributeNameView;
        public EditText attributeEdit;
        public AttributeTextWatcher textWatcher;
        public ViewHolder(View view, AttributeTextWatcher watcher) {
            super(view);
            this.cardView = view;
            this.attributeNameView = (TextView) view.findViewById(R.id.attributeNameView);
            this.attributeEdit = (EditText) view.findViewById(R.id.attributeEdit);
            this.textWatcher = watcher;
            attributeEdit.addTextChangedListener(this.textWatcher);
        }
    }

    public AttributesAdapter(Fragment fragment, ArrayList<String> dataset, SimpleCharacter simpleCharacter) {
        this.fragment = fragment;
        this.attributeList = dataset;
        this.simpleCharacter = simpleCharacter;
        if (!(fragment instanceof AttributesAdapterListener)) {
            throw new RuntimeException(fragment.toString()
                    + " must implement AttributesAdapterListener");
        }
    }

    public interface AttributesAdapterListener { }

    @Override
    public AttributesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.cardview_attributes, parent, false);
        return new ViewHolder(view, new AttributeTextWatcher());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.attributeNameView.setText(mDataset.get(holder.getAdapterPosition()));
        holder.attributeEdit.setHint(mDataset.get(holder.getAdapterPosition()));
        holder.textWatcher.updatePosition(holder.getAdapterPosition());

        if (attributeList.size() > 0) {
            if (mDataset.get(holder.getAdapterPosition()).equals(Name)) {
                holder.attributeEdit.setText(simpleCharacter.getName());
            } else if (mDataset.get(holder.getAdapterPosition()).equals(Level)) {
                holder.attributeEdit.setText(String.valueOf(simpleCharacter.getLevel()));
            } else {
                holder.attributeEdit.setText(attributeList.get(holder.getAdapterPosition()));
            }

            // Set InputType of Numeric Attributes
            switch (mDataset.get(holder.getAdapterPosition())) {
                case Xp:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case Age:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case Height:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case Weight:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case Level:
                    holder.attributeEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                default:
                    holder.attributeEdit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<String> getAttributeList() {
        return attributeList;
    }

    // Stores EditText data on text change
    // Stops attributes from moving to the wrong card
    private class AttributeTextWatcher implements TextWatcher {
        private int position;

        public void updatePosition(int pos) {
            this.position = pos;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            attributeList.set(position, charSequence.toString());
            if (position == NAME) {
                simpleCharacter.setName(charSequence.toString());
            }
            if (position == LEVEL) {
                int lvl = simpleCharacter.getLevel();
                try {
                    lvl = Integer.parseInt(charSequence.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    FirebaseCrash.log(ex.toString());
                }
                simpleCharacter.setLevel(lvl);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
