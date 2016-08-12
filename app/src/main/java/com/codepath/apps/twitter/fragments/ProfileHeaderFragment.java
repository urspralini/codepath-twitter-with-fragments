package com.codepath.apps.twitter.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.databinding.FragmentProfileHeaderBinding;
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
    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
            Log.d("DEBUG", "user info: " + object.toString());
            try {
                mUser = User.fromJSON(object);
                Picasso.with(getActivity())
                        .load(mUser.getProfileImageUrl())
                        .into(mBinding.ivProfileImage);
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
    private FragmentProfileHeaderBinding mBinding;
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_header, container, false);
        View view = mBinding.getRoot();
        mBinding.setUser(mUser);
        return view;
    }

    private void getUserInfo(){
        final String screenName = getArguments().getString(SCREEN_NAME_KEY);
        mClient.getUserInfo(screenName, handler);
    }
}
