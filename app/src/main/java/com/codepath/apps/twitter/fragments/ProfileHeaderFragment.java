package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pbabu on 8/11/16.
 */
public class ProfileHeaderFragment extends Fragment {

    public static final String SCREEN_NAME_KEY = "screen_name";
    private ImageView ivProfileImage;
    private TextView tvScreenName;
    private TextView tvTagLine;
    private TextView tvFollowingCount;
    private TextView tvFriendsCount;

    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
            Log.d("DEBUG", "user info: " + object.toString());
            try {
                mUser = User.fromJSON(object);
                Picasso.with(getActivity())
                        .load(mUser.getProfileImageUrl())
                        .into(ivProfileImage);
                tvScreenName.setText(mUser.getScreenName());
                tvTagLine.setText(mUser.getTagLine());
                tvFriendsCount.setText(String.valueOf(mUser.getFriendsCount()));
                tvFollowingCount.setText(String.valueOf(mUser.getFollowingCount()));
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
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };
    private User mUser;
    private TwitterClient mClient = RestApplication.getRestClient();

    public static ProfileHeaderFragment newInstance(String screenName) {

        Bundle args = new Bundle();
        args.putString(SCREEN_NAME_KEY, screenName);
        ProfileHeaderFragment fragment = new ProfileHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_profile_header, container, false);
        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        tvScreenName = (TextView)view.findViewById(R.id.tvScreenName);
        tvTagLine = (TextView)view.findViewById(R.id.tvTagLine);
        tvFollowingCount = (TextView)view.findViewById(R.id.tvFollowingCount);
        tvFriendsCount = (TextView)view.findViewById(R.id.tvFriendsCount);
        return view;
    }

    private void getUserInfo(){
        final String screenName = getArguments().getString(SCREEN_NAME_KEY);
        mClient.getUserInfo(screenName, handler);
    }
}
