package com.neuroandroid.pyreader.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.ui.activity.BookDetailActivity;

/**
 * Created by NeuroAndroid on 2017/6/22.
 */

public class NavigationUtils {
    public static void goToBookDetailPage(@NonNull final Activity activity, final String bookId) {
        final Intent intent = new Intent(activity, BookDetailActivity.class);
        intent.putExtra(Constant.INTENT_BOOK_ID, bookId);
        activity.startActivity(intent);
    }
}
