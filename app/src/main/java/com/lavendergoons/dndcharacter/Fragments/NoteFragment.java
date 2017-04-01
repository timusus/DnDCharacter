package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Utils;

public class NoteFragment extends Fragment {

    public static final String TAG = "NOTE_FRAG";

    private OnFragmentInteractionListener mListener;
    private EditText titleEdit, contentEdit;

    private static final String NOTE_KEY = "NOTE";
    private static final String INDEX_KEY = "INDEX";
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStop() {
        if (!Utils.isStringEmpty(titleEdit.getText().toString())) {
            note.setTitle(titleEdit.getText().toString());
        }
        if (!Utils.isStringEmpty(contentEdit.getText().toString())) {
            note.setContent(contentEdit.getText().toString());
        }
        try {
            ((OnFragmentInteractionListener) getActivity()).passBackNote(note, index);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onStop();
    }

    public interface OnFragmentInteractionListener {
        void passBackNote(Note note, int index);
    }
}
