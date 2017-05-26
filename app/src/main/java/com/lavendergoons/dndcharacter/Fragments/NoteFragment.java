package com.lavendergoons.dndcharacter.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.util.ArrayList;

public class NoteFragment extends Fragment {

    public static final String TAG = "NOTE_FRAG";

    private CharacterManager characterManager;
    private EditText titleEdit, contentEdit;

    private static final String NOTE_KEY = "NOTE";
    private static final String INDEX_KEY = "INDEX";
    private ArrayList<Note> noteList = new ArrayList<>();
    private Note note;
    private int index = -1;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(Note note, int index) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_KEY, note);
        args.putInt(INDEX_KEY, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE_KEY);
            index = getArguments().getInt(INDEX_KEY);
        }
        characterManager = CharacterManager.getInstance();
        noteList = characterManager.getCharacterNotes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        titleEdit = (EditText) rootView.findViewById(R.id.noteTitleEdit);
        titleEdit.setText(note.getTitle());
        contentEdit = (EditText) rootView.findViewById(R.id.noteContentEdit);
        contentEdit.setText(note.getContent());
        return rootView;
    }


    @Override
    public void onStop() {
        if (!Utils.isStringEmpty(titleEdit.getText().toString())) {
            note.setTitle(titleEdit.getText().toString());
        } else {
            note.setContent("N/A");
        }
        if (!Utils.isStringEmpty(contentEdit.getText().toString())) {
            note.setContent(contentEdit.getText().toString());
        } else {
            note.setContent("N/A");
        }
        if (index >= 0) {
            noteList.set(index, note);
            characterManager.setCharacterNotes(noteList);
        } else {
            FirebaseCrash.log(TAG+": Note Index Out of Bounds");
        }
        super.onStop();
    }
}
