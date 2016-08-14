package com.codepath.apps.twitter.fragments;

/**
 * Created by pbabu on 8/11/16.
 */
public class HomeTimeLineFragment extends TweetListFragment {

    @Override
    protected void fetchTweets() {
        mClient.getHomeTimeline(mMaxId!= null? mMaxId-1:null, handler);
    }
}
