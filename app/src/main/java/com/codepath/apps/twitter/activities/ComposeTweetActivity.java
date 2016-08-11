package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.twitter.Constants;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.beans.NewTweet;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.databinding.ActivityComposeBinding;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeTweetActivity extends AppCompatActivity {

    private ActivityComposeBinding binding;
    private ImageView mIvComposeUserProfile;
    private NewTweet mNewTweet = new NewTweet();
    private User mCurrentUser;
    private TwitterClient mClient = RestApplication.getRestClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = getIntent().getParcelableExtra(Constants.CURRENT_USER_KEY);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compose);
        binding.setNewTweet(mNewTweet);
        mIvComposeUserProfile = binding.composeToolBar.ivComposeUserProfile;
        setSupportActionBar(binding.composeToolBar.composeToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Picasso.with(ComposeTweetActivity.this)
                .load(mCurrentUser.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(3,1))
                .into(mIvComposeUserProfile);
    }

    public void onClickTweet(View view){
        if(!mNewTweet.isEmpty()){
            postTweet();
        }
    }

    public void onClickCancel(View view) {
        sendResult(null);
    }

    private void postTweet(){
        mClient.postTweet(mNewTweet.text.get(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Log.d("DEBUG", "timeline: " + jsonObject.toString());
                Tweet tweet = new Tweet(jsonObject);
                sendResult(tweet);
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

    private void sendResult(Tweet tweet){
        Intent resultIntent = new Intent();
        if(tweet != null) {
            resultIntent.putExtra(Constants.NEW_TWEET_KEY, tweet);
        }
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
