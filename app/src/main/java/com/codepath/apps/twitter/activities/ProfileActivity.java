package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.UserTimeLineFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance("pralini");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flUserTimeLine, userTimeLineFragment)
                .commit();
    }
}
