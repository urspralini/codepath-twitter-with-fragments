package com.codepath.apps.twitter.fragments;

import android.util.Log;

import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pbabu on 8/11/16.
 */
public class HomeTimeLineFragment extends TweetListFragment {

    private Long mMaxId;

    @Override
    protected void fetchTweets() {
        if(mTweetsAdapter.getItemCount() > 60) return;
        mClient.getHomeTimeline(mMaxId, new JsonHttpResponseHandler() {
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
