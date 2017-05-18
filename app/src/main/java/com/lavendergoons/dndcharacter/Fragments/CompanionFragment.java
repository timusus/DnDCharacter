package com.lavendergoons.dndcharacter.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Objects.Companion;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.CharacterManager;

public class CompanionFragment extends Fragment {

    public static final String TAG = "COMPANION_FRAG";

    public static final String COMPANION_KEY = "COMPANION";
    public static final String INDEX_KEY = "INDEX";

    private CharacterManager characterManager;

    private Companion companion;
    private int index = -1;

    public CompanionFragment() {
        // Required empty public constructor
    }

    public static CompanionFragment newInstance(Companion companion, int index) {
        CompanionFragment fragment = new CompanionFragment();
        Bundle args = new Bundle();
        args.putParcelable(COMPANION_KEY, companion);
        args.putInt(INDEX_KEY, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companion = getArguments().getParcelable(COMPANION_KEY);
            index = getArguments().getInt(INDEX_KEY);
        }
        characterManager = CharacterManager.getInstance();
        Log.d(TAG, companion.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_companion, container, false);
        return rootView;
    }
}
