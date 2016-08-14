package com.codepath.apps.twitter.fragments;

/**
 * Created by pbabu on 8/11/16.
 */
public class MentionsTimeLineFragment extends TweetListFragment {

    @Override
    protected void fetchTweets() {
        mClient.getMentionsTimeline(mMaxId!= null? mMaxId-1:null, handler);
    }
}
