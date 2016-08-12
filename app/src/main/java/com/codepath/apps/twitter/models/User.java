package com.codepath.apps.twitter.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pbabu on 8/4/16.
 */
public class User implements Parcelable {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private int mFollowingCount = 0;
    private int mFriendsCount = 0;
    private String tagLine = "";

    public static User fromJSON(JSONObject object) throws JSONException {
        User user = new User();
        user.name = object.getString("name");
        user.uid = object.getLong("id");
        user.screenName = object.getString("screen_name");
        user.profileImageUrl = object.getString("profile_image_url");
        user.mFollowingCount = object.getInt("followers_count");
        user.mFriendsCount = object.getInt("friends_count");
        user.tagLine = object.getString("description");
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getmFollowingCount() {
        return mFollowingCount;
    }

    public void setmFollowingCount(int mFollowingCount) {
        this.mFollowingCount = mFollowingCount;
    }

    public int getmFriendsCount() {
        return mFriendsCount;
    }

    public void setmFriendsCount(int mFriendsCount) {
        this.mFriendsCount = mFriendsCount;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profileImageUrl);
        dest.writeInt(this.mFollowingCount);
        dest.writeInt(this.mFriendsCount);
        dest.writeString(this.tagLine);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readLong();
        this.screenName = in.readString();
        this.profileImageUrl = in.readString();
        this.mFollowingCount = in.readInt();
        this.mFriendsCount = in.readInt();
        this.tagLine = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
