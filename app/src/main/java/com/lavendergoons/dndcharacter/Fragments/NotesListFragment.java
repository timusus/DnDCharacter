package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.Objects.Note;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.NotesAdapter;

import java.util.ArrayList;


public class NotesListFragment extends Fragment implements ConfirmationDialog.ConfirmationDialogInterface, View.OnClickListener {

    private RecyclerView mNotesRecyclerView;
    private NotesAdapter mNotesRecyclerAdapter;
    private RecyclerView.LayoutManager mNotesLayoutManager;
    private FloatingActionButton addNotesFAB;

    private OnFragmentInteractionListener mListener;
    private DBAdapter dbAdapter;
    private Character character;
    private ArrayList<Note> notesList;
    long characterId = -1;

    public NotesListFragment() {
        // Required empty public constructor
    }

    public static NotesListFragment newInstance(Character charIn, long characterId) {
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
            character = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notes_list, container, false);
        // Inflate the layout for this fragment
        mNotesRecyclerView = (RecyclerView) rootView.findViewById(R.id.skillsRecyclerView);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNotesFAB:

                break;
        }
    }

    @Override
    public void ConfirmDialogOk(Object o) {
        if (o instanceof Note) {
            notesList.remove(o);
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
