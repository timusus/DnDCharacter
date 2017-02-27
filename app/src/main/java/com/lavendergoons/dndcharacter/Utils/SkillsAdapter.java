package com.lavendergoons.dndcharacter.Utils;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Fragments.SkillsFragment;
import com.lavendergoons.dndcharacter.Objects.Skill;
import com.lavendergoons.dndcharacter.R;

import java.util.ArrayList;

/**
 * Adapter for skill RecyclerView
 */

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    private SkillsFragment skillsFragment;
    private ArrayList<Skill> mDataset;

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

        public ViewHolder(View view, TotalTextWatcher totalWatcher, ModTextWatcher modWatcher, RankTextWatcher rankWatcher, MiscTextWatcher miscWatcher) {
            super(view);
            this.cardView = view;
            skillCheckBox = (CheckBox) view.findViewById(R.id.skillCheckBox);
            skillModTypeText = (TextView) view.findViewById(R.id.skillModTypeText);

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

    public SkillsAdapter(SkillsFragment skillsFragment, ArrayList<Skill> dataset) {
        this.skillsFragment = skillsFragment;
        this.mDataset = dataset;
    }

    @Override
    public SkillsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_skills, parent, false);
        return new ViewHolder(view, new TotalTextWatcher(), new ModTextWatcher(), new RankTextWatcher(), new MiscTextWatcher());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.skillCheckBox.setText(mDataset.get(position).getName());
        holder.skillCheckBox.setChecked(mDataset.get(position).isTrained());

        holder.skillModTypeText.setText(mDataset.get(position).getModType());

        holder.skillTotalEdit.setText(String.valueOf(mDataset.get(position).getTotal()));
        holder.skillModEdit.setText(String.valueOf(mDataset.get(position).getMod()));
        holder.skillRankEdit.setText(String.valueOf(mDataset.get(position).getRank()));
        holder.skillMiscEdit.setText(String.valueOf(mDataset.get(position).getMisc()));
        Log.d("SKILL_BIND", mDataset.get(position).toString());

        holder.totalTextWatcher.updatePosition(position);
        holder.modTextWatcher.updatePosition(position);
        holder.rankTextWatcher.updatePosition(position);
        holder.miscTextWatcher.updatePosition(position);

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
        //TODO Clean Up
        Vibrator v = (Vibrator) skillsFragment.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Constants.LONG_CLICK_VIBRATION);
        ConfirmationDialog.showConfirmDialog(skillsFragment.getContext(), "Skills?", skillsFragment, null);
        return true;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public ArrayList<Skill> getSkillList() {
        return mDataset;
    }

    private class TotalTextWatcher implements TextWatcher {
        private int position;

        public void updatePosition(int pos) {
            this.position = pos;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            }catch (Exception ex) {
                ex.printStackTrace();
                Log.e("PARSE", "Error parsing int in SkillsAdapter");
            }
            mDataset.get(position).setTotal(value);
            Log.d("SKILL_TEXT", mDataset.get(position).toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class ModTextWatcher implements TextWatcher {
        private int position;

        public void updatePosition(int pos) {
            this.position = pos;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            }catch (Exception ex) {
                ex.printStackTrace();
                Log.e("PARSE", "Error parsing int in SkillsAdapter");
            }
            mDataset.get(position).setMod(value);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class RankTextWatcher implements TextWatcher {
        private int position;

        public void updatePosition(int pos) {
            this.position = pos;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            }catch (Exception ex) {
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

        public void updatePosition(int pos) {
            this.position = pos;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int value = -1;
            try {
                value = Integer.parseInt(charSequence.toString());
            }catch (Exception ex) {
                ex.printStackTrace();
                Log.e("PARSE", "Error parsing int in SkillsAdapter");
            }
            mDataset.get(position).setMisc(value);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
