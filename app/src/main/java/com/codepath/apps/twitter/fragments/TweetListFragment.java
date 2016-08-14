package com.codepath.apps.twitter.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitter.Constants;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.activities.ComposeTweetActivity;
import com.codepath.apps.twitter.adapters.TweetsAdapter;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.viewholders.TweetItemViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pbabu on 8/11/16.
 */
public abstract class TweetListFragment extends Fragment implements TweetItemViewHolder.onImageClickListener{

    protected List<Tweet> mTweets;
    protected TweetsAdapter mTweetsAdapter;
    protected TwitterClient mClient = RestApplication.getRestClient();
    protected RecyclerView mRvTweets;
    protected Long mMaxId;
    private TweetItemViewHolder.onImageClickListener mListener;
    protected JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
            Log.d("DEBUG", "timeline: " + jsonArray.toString());
            ArrayList<Tweet> tweets = Tweet.fromJSON(jsonArray);
            mTweetsAdapter.addAll(tweets);
            mMaxId = mTweetsAdapter.getLastTweetId();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
        errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTweets = new ArrayList<>();
        mTweetsAdapter = new TweetsAdapter(getActivity(), mTweets, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getTimeLineTweets();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_twitter_list, container, false);
        configureRecycleView(view);
        FloatingActionButton fabCompose = (FloatingActionButton) view.findViewById(R.id.fabCompose);
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TweetListFragment.this.onClickCompose(view);
            }
        });
        return view;
    }

    @Override
    public void showUserProfile(String screenName) {
        mListener.showUserProfile(screenName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof TweetItemViewHolder.onImageClickListener) {
            mListener = (TweetItemViewHolder.onImageClickListener)context;
        }else {
            throw new ClassCastException(context.toString()
                    + " must implement TweetItemViewHolder.onImageClickListener");
        }
    }

    public void onClickCompose(View view){
        Intent intent = new Intent(view.getContext(), ComposeTweetActivity.class);
        startActivityForResult(intent, Constants.COMPOSE_REQUEST_CODE);
    }

    private void configureRecycleView(View view) {
        mRvTweets = (RecyclerView)view.findViewById(R.id.rvTweets);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvTweets.setLayoutManager(layoutManager);
        mRvTweets.setAdapter(mTweetsAdapter);
    }

    protected abstract void fetchTweets();

    private void getTimeLineTweets(){
        if(mTweetsAdapter.getItemCount() > 60) return;
        fetchTweets();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.COMPOSE_REQUEST_CODE) {
                Tweet newTweet = data.getParcelableExtra(Constants.NEW_TWEET_KEY);
                if(newTweet != null) {
                    mTweetsAdapter.addToFirstPosition(newTweet);
                    mRvTweets.scrollToPosition(0);
                }
            }
        }
    }
}
