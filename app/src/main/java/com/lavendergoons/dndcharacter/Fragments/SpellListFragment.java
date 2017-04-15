package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.Objects.Spell;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Adapters.SpellAdapter;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SpellListFragment extends Fragment implements View.OnClickListener, ConfirmationDialog.ConfirmationDialogInterface {

    public static final String TAG = "SPELL_LIST_FRAG";

    private Gson gson = new Gson();

    private RecyclerView mSpellRecyclerView;
    private RecyclerView.Adapter mSpellRecyclerAdapter;
    private RecyclerView.LayoutManager mSpellRecyclerLayoutManager;
    private FloatingActionButton fab;
    private OnFragmentInteractionListener mListener;

    private ArrayList<Spell> spellList = new ArrayList<>();
    private DBAdapter dbAdapter;
    private SimpleCharacter simpleCharacter;
    private long characterId = -1;


    public SpellListFragment() {
        // Required empty public constructor
    }

    public static SpellListFragment newInstance(SimpleCharacter simpleCharacter, long characterId) {
        SpellListFragment frag = new SpellListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, simpleCharacter);
        args.putLong(Constants.CHARACTER_ID, characterId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            characterId = getArguments().getLong(Constants.CHARACTER_ID);
            simpleCharacter = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        getSpells();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_spell_list, container, false);
        mSpellRecyclerView = (RecyclerView) rootView.findViewById(R.id.spellRecyclerView);

        // Keeps View same size on content change
        mSpellRecyclerView.setHasFixedSize(true);

        mSpellRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mSpellRecyclerView.setLayoutManager(mSpellRecyclerLayoutManager);

        mSpellRecyclerAdapter = new SpellAdapter(this, spellList);
        mSpellRecyclerView.setAdapter(mSpellRecyclerAdapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.addSpellFAB);
        fab.setOnClickListener(this);
        return rootView;
    }

    private void getSpells() {
        if (dbAdapter != null && characterId != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_SPELL, characterId);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_SPELL));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Spell>>(){}.getType();
                    spellList = gson.fromJson(json, attributeType);
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    private void writeSpells() {
        String json = gson.toJson(spellList);
        if (dbAdapter != null) {
            dbAdapter.fillColumn(characterId, DBAdapter.COLUMN_SPELL, json);
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addSpellFAB:
                SpellDialog.newInstance(this);
                break;
        }
    }

    @Override
    public void ConfirmDialogOk(Object spell) {
        if (spell instanceof Spell) {
            spellList.remove(spell);
            mSpellRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {

    }

    public void deleteSpell(Spell spell) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_spell), this, spell);
    }

    private int addSpell(Spell spell) {
        int i = -1;
        if (spell != null) {
            spellList.add(spell);
            i = spellList.indexOf(spell);
            mSpellRecyclerAdapter.notifyDataSetChanged();
        }
        return i;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStop() {
        writeSpells();
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {}

    public void retrieveSpell(Spell spell, int index) {
        if (spell != null && index != -1) {
            spellList.set(index, spell);
        }
        mSpellRecyclerAdapter.notifyDataSetChanged();
    }

    public static class SpellDialog extends DialogFragment {

        public static void newInstance(final SpellListFragment fragment) {
            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
            LinearLayout dialogLayout = new LinearLayout(fragment.getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final EditText spellDialogName, spellDialogLevel;

            dialogLayout.setOrientation(LinearLayout.VERTICAL);
            dialogLayout.setLayoutParams(params);
            dialogLayout.setPadding(2, 2, 2, 2);

            spellDialogName = new EditText(fragment.getActivity());
            spellDialogName.setHint(R.string.hint_name);
            spellDialogName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            spellDialogLevel = new EditText(fragment.getActivity());
            spellDialogLevel.setInputType(InputType.TYPE_CLASS_NUMBER);
            spellDialogLevel.setHint(R.string.hint_level);

            dialogLayout.addView(spellDialogName, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialogLayout.addView(spellDialogLevel, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            builder.setTitle(fragment.getString(R.string.title_spell_dialog));
            builder.setView(dialogLayout);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean exceptionCheck = false;
                    String name = spellDialogName.getText().toString();
                    int level = 0;
                    try {
                        level = Integer.parseInt(spellDialogLevel.getText().toString());
                    }catch (Exception ex) {
                        ex.printStackTrace();
                        exceptionCheck = true;
                    }
                    if (!Utils.isStringEmpty(name) && !exceptionCheck) {
                        Spell spell = new Spell(name, level);
                        int index = fragment.addSpell(spell);
                        FragmentTransaction fragTransaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
                        fragTransaction.replace(R.id.content_character_nav, SpellFragment.newInstance(spell, index), SpellFragment.TAG).addToBackStack(SpellFragment.TAG).commit();
                    } else {
                        Toast.makeText(fragment.getContext(), fragment.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                    }
                }
            }).setNegativeButton(R.string.cancel, null).create().show();
        }
    }
}
