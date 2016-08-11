package com.codepath.apps.twitter.beans;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by pbabu on 8/6/16.
 */
public class NewTweet {
    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableInt remainingCharCount = new ObservableInt();

    public NewTweet() {
        remainingCharCount.set(140);
    }

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String newText = s.toString();
            String currentText = text.get();
            if(!newText.equals(currentText)) {
                text.set(newText);
                if(!isEmpty()){
                    remainingCharCount.set(140 - text.get().length());
                }
            }
        }
    };

    public boolean isEmpty() {
        final String str = text.get();
        return str == null || str.isEmpty();
    }
}
