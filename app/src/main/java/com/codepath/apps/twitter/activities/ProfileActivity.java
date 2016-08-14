package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.fragments.FollowersFragment;
import com.codepath.apps.twitter.fragments.FriendsFragment;
import com.codepath.apps.twitter.fragments.ProfileHeaderFragment;
import com.codepath.apps.twitter.fragments.UserTimeLineFragment;
import com.codepath.apps.twitter.models.User;

public class ProfileActivity extends BaseActivity implements ProfileHeaderFragment.UserInfoListener{

    public static final String SCREEN_NAME_KEY = "screen_name";
    private Toolbar mToolBar;
    private TextView mToolBarTitle;
    private ViewPager mVPager;
    private TabLayout mTabs;
    private FragmentPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String screenName = getIntent().getStringExtra(SCREEN_NAME_KEY);
        setupToolBar();
        mVPager = (ViewPager)findViewById(R.id.vPager);
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private String tabTitles[] = new String[] {"TimeLine", "Followers", "Friends"};
            @Override
            public Fragment getItem(int position) {
                if(position == 0) {
                    return UserTimeLineFragment.newInstance(screenName);
                }else if (position == 1) {
                    return FollowersFragment.newInstance(screenName);
                }else if (position == 2) {
                    return FriendsFragment.newInstance(screenName);
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
        mTabs = (TabLayout)findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mVPager);
        ProfileHeaderFragment profileHeaderFragment = ProfileHeaderFragment.newInstance(screenName, this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flProfileHeader, profileHeaderFragment)
                .commit();
    }

    private void setupToolBar() {
        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        mToolBarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        setSupportActionBar(mToolBar);
        //Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void sendUser(User user) {
        updateToolBarTitle(user.getName());
    }

    private void updateToolBarTitle(String text) {
        mToolBarTitle.setText(text);
    }
}
