package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.ProfileHeaderFragment;
import com.codepath.apps.twitter.fragments.UserTimeLineFragment;
import com.codepath.apps.twitter.models.User;

public class ProfileActivity extends BaseActivity implements ProfileHeaderFragment.UserInfoListener{

    public static final String SCREEN_NAME_KEY = "screen_name";
    private Toolbar mToolBar;
    private TextView mToolBarTitle;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String screenName = getIntent().getStringExtra(SCREEN_NAME_KEY);
        setupToolBar();
        UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(screenName);
        ProfileHeaderFragment profileHeaderFragment = ProfileHeaderFragment.newInstance(screenName, this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flUserTimeLine, userTimeLineFragment)
                .add(R.id.flProfileHeader, profileHeaderFragment)
                .commit();
    }

    private void setupToolBar() {
        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        mToolBarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        setSupportActionBar(mToolBar);
        //Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void sendUser(User user) {
        mUser = user;
        updateToolBarTitle(user.getName());
    }

    private void updateToolBarTitle(String text) {
        mToolBarTitle.setText(text);
    }
}
