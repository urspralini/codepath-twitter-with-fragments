package com.codepath.apps.twitter.fragments;

import android.os.Bundle;

/**
 * Created by pbabu on 8/11/16.
 */
public class UserTimeLineFragment extends TweetListFragment{

    public static final String SCREEN_NAME_KEY = "screen_name";

    public static UserTimeLineFragment newInstance(String screenName) {
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME_KEY, screenName);
        UserTimeLineFragment fragment = new UserTimeLineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void fetchTweets() {
        final String screenName = getArguments().getString(SCREEN_NAME_KEY);
        mClient.getUserTimeline(screenName, handler);
    }
}
