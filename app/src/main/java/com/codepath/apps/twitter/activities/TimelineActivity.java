package com.codepath.apps.twitter.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.databinding.ActivityTimelineBinding;
import com.codepath.apps.twitter.fragments.HomeTimeLineFragment;
import com.codepath.apps.twitter.fragments.MentionsTimeLineFragment;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private ImageView mIvToolBarImage;
    private TextView mToolBarTitle;
    private TwitterClient mClient = RestApplication.getRestClient();
    private ActivityTimelineBinding mBinding;
    private User mCurrentUser;
    private ViewPager mVPager;
    private PagerSlidingTabStrip mTabs;
    private FragmentPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
        setupToolBar();
        mVPager = mBinding.vPager;
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private String tabTitles[] = new String[] {"Home", "Mentions"};
            @Override
            public Fragment getItem(int position) {
                if(position == 0) {
                    return new HomeTimeLineFragment();
                }else if (position == 1) {
                    return new MentionsTimeLineFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return tabTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        };
        mVPager.setAdapter(mPagerAdapter);
        mTabs = mBinding.tabs;
        mTabs.setViewPager(mVPager);
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

    private void setupToolBar() {
        mToolBar = mBinding.appbar.toolbar;
        mToolBarTitle = mBinding.appbar.tvToolbarTitle;
        mIvToolBarImage = mBinding.appbar.ivUserImage;
        setSupportActionBar(mToolBar);
        //Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getCurrentUser();
    }
}
