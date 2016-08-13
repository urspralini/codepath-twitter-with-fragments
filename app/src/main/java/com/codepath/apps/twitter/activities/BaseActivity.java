package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.twitter.viewholders.TweetItemViewHolder;

/**
 * Created by pbabu on 8/13/16.
 */
public class BaseActivity extends AppCompatActivity implements TweetItemViewHolder.onImageClickListener {

    @Override
    public void showUserProfile(String screenName) {
        Intent userProfileIntent = new Intent(this, ProfileActivity.class);
        userProfileIntent.putExtra(ProfileActivity.SCREEN_NAME_KEY, screenName);
        startActivity(userProfileIntent);
    }
}
