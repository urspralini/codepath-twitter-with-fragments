package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.ProfileHeaderFragment;
import com.codepath.apps.twitter.fragments.UserTimeLineFragment;

public class ProfileActivity extends BaseActivity {

    public static final String SCREEN_NAME_KEY = "screen_name";
    private Toolbar mToolBar;
    private TextView mToolBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String screenName = getIntent().getStringExtra(SCREEN_NAME_KEY);
        setupToolBar(screenName);
        UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(screenName);
        ProfileHeaderFragment profileHeaderFragment = ProfileHeaderFragment.newInstance(screenName);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flUserTimeLine, userTimeLineFragment)
                .add(R.id.flProfileHeader, profileHeaderFragment)
                .commit();
    }

    private void setupToolBar(String screenName) {
        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        mToolBarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        setSupportActionBar(mToolBar);
        //Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBarTitle.setText("@" + screenName);
    }
}
