package com.codepath.apps.twitter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;

/**
 * Created by pbabu on 8/13/16.
 */
public class FollowUserViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivProfileImage;
    public TextView tvName;
    public TextView tvScreenName;
    public TextView tvTagLine;
    public TextView tvFollowingCount;
    public TextView tvFriendsCount;
    public TextView tvFollowing;
    public TextView tvFriends;


    public FollowUserViewHolder(View view) {
        super(view);
        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvScreenName = (TextView)view.findViewById(R.id.tvScreenName);
        tvTagLine = (TextView)view.findViewById(R.id.tvTagLine);
        tvFollowing = (TextView)view.findViewById(R.id.tvFollowing);
        tvFollowingCount = (TextView)view.findViewById(R.id.tvFollowingCount);
        tvFriends = (TextView)view.findViewById(R.id.tvFriends);
        tvFriendsCount = (TextView)view.findViewById(R.id.tvFriendsCount);
    }
}
