package com.codepath.apps.twitter.fragments;

import android.os.Bundle;

import com.codepath.apps.twitter.Constants;

/**
 * Created by pbabu on 8/14/16.
 */
public class FollowersFragment extends UserFragment {

    public static FollowersFragment newInstance(String screenName) {

        Bundle args = new Bundle();
        args.putString(Constants.SCREEN_NAME_KEY, screenName);
        FollowersFragment fragment = new FollowersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void fetchUsers(Long cursor) {
        mClient.getFollowers(mScreenName, cursor, handler);
    }
}
