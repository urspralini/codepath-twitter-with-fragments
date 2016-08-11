package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.Constants;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.adapters.TweetsAdapter;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.databinding.ActivityTimelineBinding;
import com.codepath.apps.twitter.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private List<Tweet> mTweets;
    private TweetsAdapter mTweetsAdapter;
    private Toolbar mToolBar;
    private ImageView mIvToolBarImage;
    private TextView mToolBarTitle;
    private TwitterClient mClient = RestApplication.getRestClient();
    private ActivityTimelineBinding mBinding;
    private User mCurrentUser;
    private RecyclerView mRvTweets;
    private Long mMaxId;
    private SwipeRefreshLayout mSwipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        mToolBar = mBinding.appbar.toolbar;
        mToolBarTitle = mBinding.appbar.tvToolbarTitle;
        mIvToolBarImage = mBinding.appbar.ivUserImage;
        setSupportActionBar(mToolBar);
        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeContainer.setRefreshing(true);
                fetchTweets(null);
            }
        });
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        //Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        configureRecycleView();
        getCurrentUser();
        fetchTweets(null);
    }

    private void getCurrentUser() {
        mClient.getCurrentUser(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    mCurrentUser = new User();
                    mCurrentUser.setName(response.getString("name"));
                    mCurrentUser.setScreenName(response.getString("screen_name"));
                    mCurrentUser.setProfileImageUrl(response.getString("profile_image_url"));
                    mCurrentUser.setUid(response.getLong("id"));
                    Glide.with(TimelineActivity.this)
                            .load(mCurrentUser.getProfileImageUrl())
                            .into(mIvToolBarImage);
                    mToolBarTitle.setText("@"+mCurrentUser.getScreenName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    private void fetchTweets(Long maxId){
        if(mTweetsAdapter.getItemCount() > 60) return;
        mClient.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                Log.d("DEBUG", "timeline: " + jsonArray.toString());
                ArrayList<Tweet> tweets = Tweet.fromJSON(jsonArray);
                mTweetsAdapter.addAll(tweets);
                mMaxId = mTweetsAdapter.getLastTweetId();
                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mSwipeContainer.setRefreshing(false);
            }


        });
    }

    public void onClickCompose(View view){
        Intent intent = new Intent(view.getContext(), ComposeTweetActivity.class);
        intent.putExtra(Constants.CURRENT_USER_KEY, mCurrentUser);
        startActivityForResult(intent, Constants.COMPOSE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == Constants.COMPOSE_REQUEST_CODE) {
                Tweet newTweet = data.getParcelableExtra(Constants.NEW_TWEET_KEY);
                if(newTweet != null) {
                    mTweetsAdapter.addToFirstPosition(newTweet);
                    mRvTweets.scrollToPosition(0);
                }
            }
        }
    }

    private void configureRecycleView() {
        mRvTweets = (RecyclerView)findViewById(R.id.rvTweets);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvTweets.setLayoutManager(layoutManager);
        mTweets = new ArrayList<>();
        mTweetsAdapter = new TweetsAdapter(this, mTweets);
        mRvTweets.setAdapter(mTweetsAdapter);
        mRvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchTweets(mMaxId-1);
            }
        });
    }
}
