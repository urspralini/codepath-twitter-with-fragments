package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.adapters.TweetsAdapter;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pbabu on 8/11/16.
 */
public abstract class TweetListFragment extends Fragment{

    protected List<Tweet> mTweets;
    protected TweetsAdapter mTweetsAdapter;
    protected TwitterClient mClient = RestApplication.getRestClient();
    protected RecyclerView mRvTweets;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTweets = new ArrayList<>();
        mTweetsAdapter = new TweetsAdapter(getActivity(), mTweets);
        fetchTweets();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_twitter_list, container, false);
        configureRecycleView(view);
        return view;
    }

    private void configureRecycleView(View view) {
        mRvTweets = (RecyclerView)view.findViewById(R.id.rvTweets);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvTweets.setLayoutManager(layoutManager);
        mRvTweets.setAdapter(mTweetsAdapter);
    }

    protected abstract void fetchTweets();
}
