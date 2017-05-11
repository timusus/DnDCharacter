package com.lavendergoons.dndcharacter.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.R;


public class AboutFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "ABOUT_FRAG";

    private ImageButton nolanInstagram;
    private TextView emailText;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        nolanInstagram = (ImageButton) rootView.findViewById(R.id.skullImageBtn);
        nolanInstagram.setOnClickListener(this);
        emailText = (TextView) rootView.findViewById(R.id.aboutIssuesEmail);
        //emailText.setMovementMethod(LinkMovementMethod.getInstance());
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_instagram_link)));
        startActivity(browserIntent);
    }
}
