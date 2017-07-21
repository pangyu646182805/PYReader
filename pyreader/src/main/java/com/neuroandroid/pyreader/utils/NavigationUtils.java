package com.neuroandroid.pyreader.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.ui.activity.BookDetailActivity;
import com.neuroandroid.pyreader.ui.activity.BookReadActivity;

/**
 * Created by NeuroAndroid on 2017/6/22.
 */

public class NavigationUtils {
    /**
     * 跳转到书籍详情界面
     * fromBookRead : 是否从书籍阅读界面跳转而来
     */
    public static void goToBookDetailPage(@NonNull final Activity activity, final String bookId, final boolean fromBookRead) {
        final Intent intent = new Intent(activity, BookDetailActivity.class);
        intent.putExtra(Constant.INTENT_BOOK_ID, bookId);
        intent.putExtra(Constant.FROM_BOOK_READ, fromBookRead);
        activity.startActivity(intent);
    }

    /**
     * 跳转到书籍阅读界面
     */
    public static void goToBookReadPage(@NonNull final Activity activity, @NonNull final Recommend.BooksBean booksBean) {
        goToBookReadPage(activity, false, booksBean);
    }

    /**
     * 跳转到书籍阅读界面
     */
    public static void goToBookReadPage(@NonNull final Activity activity, boolean fromSD,
                                        @NonNull final Recommend.BooksBean booksBean) {
        final Intent intent = new Intent(activity, BookReadActivity.class);
        intent.putExtra(Constant.INTENT_BOOK_BEAN, booksBean);
        intent.putExtra(Constant.INTENT_FROM_SD, fromSD);
        activity.startActivity(intent);
    }
}
