package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.viewholders.TweetItemViewHolder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pbabu on 8/13/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements TweetItemViewHolder.onImageClickListener {
    protected TwitterClient mClient = RestApplication.getRestClient();
    protected User mCurrentUser;
    @Override
    public void showUserProfile(String screenName) {
        if(!(this instanceof ProfileActivity)){
            Intent userProfileIntent = new Intent(this, ProfileActivity.class);
            userProfileIntent.putExtra(ProfileActivity.SCREEN_NAME_KEY, screenName);
            startActivity(userProfileIntent);
        }
    }

    protected void getCurrentUser() {
        mClient.getCurrentUser(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    mCurrentUser = new User();
                    mCurrentUser.setName(response.getString("name"));
                    mCurrentUser.setScreenName(response.getString("screen_name"));
                    mCurrentUser.setProfileImageUrl(response.getString("profile_image_url"));
                    mCurrentUser.setUid(response.getLong("id"));
                    updateUIWithCurrentUser();
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

    public void updateUIWithCurrentUser(){

    }
}
