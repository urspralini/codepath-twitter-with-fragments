package com.codepath.apps.twitter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TweetItemViewHolder;
import com.codepath.apps.twitter.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by pbabu on 8/4/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Tweet> mTweets;
    private final Context mContext;

    public TweetsAdapter(Context mContext, List<Tweet> mTweets) {
        this.mContext = mContext;
        this.mTweets = mTweets;
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tweet_list_item, parent, false);
        return new TweetItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        TweetItemViewHolder tweetItemViewHolder = (TweetItemViewHolder)holder;
        tweetItemViewHolder.tvTweetText.setText(tweet.getBody());
        tweetItemViewHolder.tvName.setText(tweet.getUser().getName());
        tweetItemViewHolder.tvUserHandle.setText("@"+tweet.getUser().getScreenName());
        tweetItemViewHolder.tvRelativeTimeStamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        Glide.with(mContext)
                .load(tweet.getUser().getProfileImageUrl())
                .fitCenter()
                .into(tweetItemViewHolder.ivUserProfileImage);

    }

    public void addAll(List<Tweet> tweets){
        mTweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clear(){
        mTweets.clear();
        notifyDataSetChanged();
    }

    public void addToFirstPosition(Tweet tweet){
        mTweets.add(0,tweet);
        notifyDataSetChanged();
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


    public long getLastTweetId(){
        final Tweet tweet = mTweets.get(mTweets.size() - 1);
        return tweet.getId();
    }
}
