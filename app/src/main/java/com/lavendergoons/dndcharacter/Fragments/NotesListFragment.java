package com.lavendergoons.dndcharacter.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.SimpleCharacter;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Adapters.NotesAdapter;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.util.ArrayList;

public class NotesListFragment extends Fragment
        implements ConfirmationDialog.ConfirmationDialogInterface, View.OnClickListener, NotesAdapter.NotesAdapterListener {

    public static final String TAG = "NOTES_LIST_FRAG";

    private RecyclerView mNotesRecyclerView;
    private NotesAdapter mNotesRecyclerAdapter;
    private RecyclerView.LayoutManager mNotesLayoutManager;
    private FloatingActionButton addNotesFAB;
    private CharacterManager characterManager;

    private SimpleCharacter simpleCharacter;
    private ArrayList<Note> notesList = new ArrayList<>();
    long characterId = -1;

    public NotesListFragment() {
        // Required empty public constructor
    }

    public static NotesListFragment newInstance(SimpleCharacter charIn, long characterId) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, charIn);
        args.putLong(Constants.CHARACTER_ID, characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            characterId = getArguments().getLong(Constants.CHARACTER_ID);
            simpleCharacter = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        characterManager = CharacterManager.getInstance(this.getContext());
        notesList = characterManager.getCharacterNotes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notes_list, container, false);
        // Inflate the layout for this fragment
        mNotesRecyclerView = (RecyclerView) rootView.findViewById(R.id.notesRecyclerView);

        // Keeps View same size on content change
        mNotesRecyclerView.setHasFixedSize(true);

        mNotesLayoutManager = new LinearLayoutManager(this.getContext());
        mNotesRecyclerView.setLayoutManager(mNotesLayoutManager);

        mNotesRecyclerAdapter = new NotesAdapter(this, notesList);
        mNotesRecyclerView.setAdapter(mNotesRecyclerAdapter);
        addNotesFAB = (FloatingActionButton) rootView.findViewById(R.id.addNotesFAB);
        addNotesFAB.setOnClickListener(this);
        return rootView;
    }



    private void writeNotes() {
        characterManager.setCharacterNotes(notesList);
    }

    @Override
    public void onStop() {
        writeNotes();
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNotesFAB:
                new NotesDialog().showDialog();
                break;
        }
    }

    @Override
    public void remoteNote(Note note) {
        ConfirmationDialog.showConfirmDialog(this.getContext(), getString(R.string.confirm_delete_note), this, note);
    }

    @Override
    public void ConfirmDialogOk(Object o) {
        if (o instanceof Note) {
            notesList.remove(o);
        }
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void ConfirmDialogCancel(Object o) {

    }


    private int addNote(Note note) {
        int i = -1;
        if (note != null) {
            notesList.add(note);
            i = notesList.indexOf(note);
            mNotesRecyclerAdapter.notifyDataSetChanged();
        }
        return i;
    }

    // Simple Dialog to Create New Note
    public class NotesDialog  {

        public void showDialog() {
            final NotesListFragment fragment = NotesListFragment.this;
            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
            LinearLayout dialogLayout = new LinearLayout(fragment.getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final EditText noteTitle;

            dialogLayout.setOrientation(LinearLayout.VERTICAL);
            dialogLayout.setLayoutParams(params);
            dialogLayout.setPadding(2, 2, 2, 2);

            noteTitle = new EditText(fragment.getActivity());
            noteTitle.setHint(R.string.hint_name);
            noteTitle.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            dialogLayout.addView(noteTitle, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            builder.setTitle(fragment.getString(R.string.title_note_dialog));
            builder.setView(dialogLayout);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String name = noteTitle.getText().toString();

                    if (!Utils.isStringEmpty(name)) {
                        Note note = new Note(name);
                        int index = fragment.addNote(note);
                        FragmentTransaction fragTransaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
                        fragTransaction.replace(R.id.content_character_nav, NoteFragment.newInstance(note, index), NoteFragment.TAG).addToBackStack(NoteFragment.TAG).commit();
                    } else {
                        Toast.makeText(fragment.getContext(), fragment.getString(R.string.warning_enter_required_fields), Toast.LENGTH_LONG).show();
                    }
                }
            }).setNegativeButton(R.string.cancel, null).create().show();
        }
    }
}
