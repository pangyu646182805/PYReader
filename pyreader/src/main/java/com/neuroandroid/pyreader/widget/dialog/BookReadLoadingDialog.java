package com.neuroandroid.pyreader.widget.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.dialog.base.PYDialog;

/**
 * Created by NeuroAndroid on 2017/8/10.
 */

public class BookReadLoadingDialog extends PYDialog<BookReadLoadingDialog> {
    private AnimationDrawable mBookReadLoading;

    public BookReadLoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_book_read_loading_dialog;
    }

    @Override
    protected void initView() {
        boolean darkMode = ThemeUtils.isDarkMode();

        ImageView ivBookReadLoading = getView(R.id.iv_book_read_loading);
        mBookReadLoading = (AnimationDrawable) ivBookReadLoading.getDrawable();

        LinearLayout llContainer = getView(R.id.ll_container);
        if (darkMode) {
            llContainer.setBackgroundResource(R.drawable.border_loading_dialog_dark);
            ivBookReadLoading.setColorFilter(UIUtils.getColor(R.color.backgroundColor));
        }
    }

    @Override
    public BookReadLoadingDialog showDialog() {
        mBookReadLoading.start();
        return super.showDialog();
    }

    @Override
    public BookReadLoadingDialog dismissDialog() {
        mBookReadLoading.stop();
        return super.dismissDialog();
    }
}
