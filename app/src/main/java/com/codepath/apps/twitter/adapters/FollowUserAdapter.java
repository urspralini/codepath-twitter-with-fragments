package com.codepath.apps.twitter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.User;
import com.codepath.apps.twitter.viewholders.FollowUserViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pbabu on 8/13/16.
 */
public class FollowUserAdapter extends RecyclerView.Adapter<FollowUserViewHolder> {

    private final List<User> mUsers;
    private final Context mContext;

    public FollowUserAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    @Override
    public FollowUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.fragment_profile_header, parent, false);
        return new FollowUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowUserViewHolder holder, int position) {
        final User user = mUsers.get(position);
        Picasso.with(mContext)
                .load(user.getProfileImageUrl())
                .into(holder.ivProfileImage);
        holder.tvName.setText(user.getName());
        holder.tvScreenName.setText("@"+user.getScreenName());
        holder.tvTagLine.setText(user.getTagLine());
        holder.tvFollowing.setText(R.string.following);
        holder.tvFriends.setText(R.string.followers);
        holder.tvFriendsCount.setText(String.valueOf(user.getFriendsCount()));
        holder.tvFollowingCount.setText(String.valueOf(user.getFollowingCount()));
    }

    public void addAll(List<User> users){
        mUsers.addAll(users);
        notifyDataSetChanged();
    }
}
