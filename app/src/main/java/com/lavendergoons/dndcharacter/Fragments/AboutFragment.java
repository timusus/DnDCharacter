package com.lavendergoons.dndcharacter.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lavendergoons.dndcharacter.R;


public class AboutFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "ABOUT_FRAG";

    private ImageButton nolanInstagram;
    private TextView emailText, changeLogView;

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
        changeLogView = (TextView) rootView.findViewById(R.id.aboutChangeLogTitle);
        changeLogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChangeDialog().showDialog();
            }
        });
        //emailText.setMovementMethod(LinkMovementMethod.getInstance());
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_instagram_link)));
        startActivity(browserIntent);
    }

    public class ChangeDialog {
        public void showDialog() {
            AboutFragment fragment = AboutFragment.this;

            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());

            LinearLayout dialogLayout = new LinearLayout(fragment.getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final WebView webView = new WebView(fragment.getContext());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.loadUrl("file:///android_asset/info.html");

            dialogLayout.setLayoutParams(params);
            dialogLayout.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            builder.setTitle(getString(R.string.announcement_changelog))
                    .setView(dialogLayout)
                    .setPositiveButton(R.string.ok, null)
                    .create().show();
        }
    }
}
