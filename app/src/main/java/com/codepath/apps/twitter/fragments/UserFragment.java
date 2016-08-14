package com.codepath.apps.twitter.fragments;

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
import com.codepath.apps.twitter.adapters.FollowUserAdapter;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pbabu on 8/13/16.
 */
public abstract class UserFragment extends Fragment {

    protected RecyclerView mRvUsers;
    protected FollowUserAdapter mFollowUserAdapter;
    protected List<User> mUsers;
    protected TwitterClient mClient = RestApplication.getRestClient();
    protected Long mPreviousCursor;
    protected Long mNextCursor;
    protected String mScreenName;

    protected JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
            Log.d("DEBUG", "timeline: " + jsonObject.toString());
            try {
                mPreviousCursor = jsonObject.getLong("previous_cursor");
                mNextCursor = jsonObject.getLong("next_cursor");
                final ArrayList<User> users = User.fromJSON(jsonObject.getJSONArray("users"));
                mFollowUserAdapter.addAll(users);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenName = getArguments().getString(Constants.SCREEN_NAME_KEY);
        mUsers = new ArrayList<>();
        mFollowUserAdapter = new FollowUserAdapter(getActivity(), mUsers);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_friends_following_list, container, false);
        configureRecyclerView(view);
        FloatingActionButton fabCompose = (FloatingActionButton) view.findViewById(R.id.fabCompose);
        fabCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment.this.onClickCompose(view);
            }
        });
        return view;
    }

    public void onClickCompose(View view){
        Intent intent = new Intent(view.getContext(), ComposeTweetActivity.class);
        startActivityForResult(intent, Constants.COMPOSE_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchUsers(null);
    }

    private void configureRecyclerView(View view){
        mRvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvUsers.setLayoutManager(layoutManager);
        mRvUsers.setAdapter(mFollowUserAdapter);
    }

    protected abstract void fetchUsers(Long cursor);
}
