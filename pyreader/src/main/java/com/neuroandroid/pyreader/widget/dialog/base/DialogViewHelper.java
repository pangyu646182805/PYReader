package com.neuroandroid.pyreader.widget.dialog.base;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neuroandroid.pyreader.utils.UIUtils;

import java.lang.ref.WeakReference;

/**
 * Created by NeuroAndroid on 2017/6/16.
 */

public class DialogViewHelper {
    private View mDialogView;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(View contentView) {
        mDialogView = contentView;
        mViews = new SparseArray<>();
    }

    public void setText(@IdRes int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv != null) tv.setText(UIUtils.isEmpty(text) ? "" : text);
    }

    public void setTextColor(@IdRes int viewId, int color) {
        TextView tv = getView(viewId);
        if (tv != null) tv.setTextColor(UIUtils.getColor(color));
    }

    public void setImageResource(@IdRes int viewId, int res) {
        ImageView iv = getView(viewId);
        if (iv != null) iv.setImageResource(res);
    }

    public void setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) view.setVisibility(visibility);
    }

    public void setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (view != null) view.setOnClickListener(onClickListener);
    }

    public <T extends View> T getView(int viewId) {
        View view = null;
        WeakReference<View> reference = mViews.get(viewId);
        if (reference != null) {
            view = reference.get();
        }
        if (view == null) {
            view = mDialogView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }
}
