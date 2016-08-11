package com.codepath.apps.twitter.handlers;

import android.content.Intent;
import android.view.View;

import com.codepath.apps.twitter.activities.ComposeTweetActivity;

/**
 * Created by pbabu on 8/6/16.
 */
public class Handlers {

    public void onClickCompose(View view){
        Intent intent = new Intent(view.getContext(), ComposeTweetActivity.class);
        view.getContext().startActivity(intent);
    }
}
