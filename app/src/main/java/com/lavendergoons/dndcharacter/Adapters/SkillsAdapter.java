package com.lavendergoons.dndcharacter.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Fragments.SkillsFragment;
import com.lavendergoons.dndcharacter.Objects.Abilities;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

/**
 * Adapter for skill RecyclerView
 */

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    private SkillsFragment skillsFragment;
    private ArrayList<Skill> mDataset;
    private Abilities abilities;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public CheckBox skillCheckBox;
        public TextView skillModTypeText;
        public EditText skillTotalEdit;
        public EditText skillModEdit;
        public EditText skillRankEdit;
        public EditText skillMiscEdit;
        public TotalTextWatcher totalTextWatcher;
        public ModTextWatcher modTextWatcher;
        public RankTextWatcher rankTextWatcher;
        public MiscTextWatcher miscTextWatcher;
        public CheckBoxListener mCheckBoxListener;

        public ViewHolder(View view, TotalTextWatcher totalWatcher, ModTextWatcher modWatcher, RankTextWatcher rankWatcher, MiscTextWatcher miscWatcher, CheckBoxListener checkBoxListener) {
            super(view);
            this.cardView = view;
            skillCheckBox = (CheckBox) view.findViewById(R.id.skillCheckBox);
            skillModTypeText = (TextView) view.findViewById(R.id.skillModTypeText);
            this.mCheckBoxListener = checkBoxListener;
            skillCheckBox.setOnCheckedChangeListener(this.mCheckBoxListener);

            skillTotalEdit = (EditText) view.findViewById(R.id.skillTotalEdit);
            this.totalTextWatcher = totalWatcher;
            skillTotalEdit.addTextChangedListener(this.totalTextWatcher);

            skillModEdit = (EditText) view.findViewById(R.id.skillModEdit);
            this.modTextWatcher = modWatcher;
            skillModEdit.addTextChangedListener(this.modTextWatcher);

            skillRankEdit = (EditText) view.findViewById(R.id.skillRankEdit);
            this.rankTextWatcher = rankWatcher;
            skillRankEdit.addTextChangedListener(this.rankTextWatcher);

            skillMiscEdit = (EditText) view.findViewById(R.id.skillMiscEdit);
            this.miscTextWatcher = miscWatcher;
            skillMiscEdit.addTextChangedListener(this.miscTextWatcher);
        }
    }

    public SkillsAdapter(SkillsFragment skillsFragment, ArrayList<Skill> dataset, Abilities abilities) {
        this.skillsFragment = skillsFragment;
        this.mDataset = dataset;
        this.abilities = abilities;
    }

    @Override
    public SkillsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_skills, parent, false);
        return new ViewHolder(view, new TotalTextWatcher(), new ModTextWatcher(), new RankTextWatcher(), new MiscTextWatcher(), new CheckBoxListener());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Update position before setting text
        int mod = modTypeToInt(mDataset.get(position).getModType());
        holder.totalTextWatcher.updatePosition(position, holder);
        holder.modTextWatcher.updatePosition(position, holder);
        holder.rankTextWatcher.updatePosition(position, holder);
        holder.miscTextWatcher.updatePosition(position, holder);
        holder.mCheckBoxListener.updatePostion(position);

        holder.skillCheckBox.setText(mDataset.get(position).getName());
        holder.skillCheckBox.setChecked(mDataset.get(position).isTrained());
        holder.skillModTypeText.setText(mDataset.get(position).getModType());

        holder.skillTotalEdit.setText(String.valueOf(mDataset.get(position).getTotal()));
        holder.skillModEdit.setText(String.valueOf(abilities.getMod(mod)));
        holder.skillRankEdit.setText(String.valueOf(mDataset.get(position).getRank()));
        holder.skillMiscEdit.setText(String.valueOf(mDataset.get(position).getMisc()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick();
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onCardLongClick();
            }
        });
    }

    private void onCardClick() {

    }

    private boolean onCardLongClick() {
        return true;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<Skill> getSkillList() {
        return mDataset;
    }

    public int modTypeToInt(String type) {
        int mod = 0;
        switch (type) {
            case "STR":
                mod = Abilities.STR;
                break;
            case "DEX":
                mod = Abilities.DEX;
                break;
            case "CON":
                mod = Abilities.CON;
                break;
            case "INT":
                mod = Abilities.INT;
                break;
            case "WIS":
                mod = Abilities.WIS;
                break;
            case "CHA":
                mod = Abilities.CHA;
                break;
        }
        return mod;
    }

    // TextWatchers to save values
    private class TotalTextWatcher implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int pos, ViewHolder holder) {
            this.position = pos;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("PARSE", "Error parsing int in SkillsAdapter");
            }
            mDataset.get(position).setTotal(value);
            Log.d("SKILL_TEXT", mDataset.get(position).toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class ModTextWatcher implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int pos, ViewHolder holder) {
            this.position = pos;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("PARSE", "Error parsing int in SkillsAdapter");
            }
            mDataset.get(position).setMod(value);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class RankTextWatcher implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int pos, ViewHolder holder) {
            this.position = pos;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("PARSE", "Error parsing int in SkillsAdapter");
            }
            mDataset.get(position).setRank(value);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class MiscTextWatcher implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public void updatePosition(int pos, ViewHolder holder) {
            this.position = pos;
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("PARSE", "Error parsing int in SkillsAdapter");
            }
            mDataset.get(position).setMisc(value);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        private int position;

        public void updatePostion(int pos) {
            this.position = pos;
        }
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Log.d("SKILLS", mDataset.get(position).getName()+" isChecked "+b);
            mDataset.get(position).setTrained(b);
        }
    }
}
