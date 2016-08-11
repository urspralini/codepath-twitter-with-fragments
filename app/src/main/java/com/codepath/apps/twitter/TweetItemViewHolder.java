package com.codepath.apps.twitter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pbabu on 8/4/16.
 */
public class TweetItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivUserProfileImage;
    public TextView tvUserHandle;
    public TextView tvTweetText;
    public TextView tvRelativeTimeStamp;
    public TextView tvName;

    public TweetItemViewHolder(View view) {
        super(view);
        ivUserProfileImage = (ImageView)view.findViewById(R.id.ivUserProfileImage);
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvRelativeTimeStamp = (TextView)view.findViewById(R.id.tvRelativeTimeStamp);
        tvUserHandle = (TextView)view.findViewById(R.id.tvUserHandle);
        tvTweetText = (TextView)view.findViewById(R.id.tvTweetText);
    }
}
