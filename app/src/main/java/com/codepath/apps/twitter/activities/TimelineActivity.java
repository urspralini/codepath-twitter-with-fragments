package com.codepath.apps.twitter.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.databinding.ActivityTimelineBinding;
import com.codepath.apps.twitter.fragments.HomeTimeLineFragment;
import com.codepath.apps.twitter.fragments.MentionsTimeLineFragment;
import com.codepath.apps.twitter.utils.Helper;

public class TimelineActivity extends BaseActivity{

    private Toolbar mToolBar;
    private ImageView mIvToolBarImage;
    private TextView mToolBarTitle;
    private ActivityTimelineBinding mBinding;
    private ViewPager mVPager;
    private TabLayout mTabs;
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
        mTabs.setupWithViewPager(mVPager);
    }

    private void setupToolBar() {
        mToolBar = mBinding.appbar.toolbar;
        mToolBarTitle = mBinding.appbar.tvToolbarTitle;
        mIvToolBarImage = mBinding.appbar.ivUserImage;
        setSupportActionBar(mToolBar);
        mToolBarTitle.setText(R.string.timeline_home);
        //Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getCurrentUser();
    }

    public void showUserProfile(MenuItem item) {
        showUserProfile(mCurrentUser.getScreenName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Helper.isNetworkAvailable(this) || !Helper.isOnline()){
            Helper.showSnackBar(mVPager, this);
        }else {
            getCurrentUser();
        }
    }

    @Override
    public void updateUIWithCurrentUser() {
        Glide.with(TimelineActivity.this)
                .load(mCurrentUser.getProfileImageUrl())
                .into(mIvToolBarImage);
    }
}
