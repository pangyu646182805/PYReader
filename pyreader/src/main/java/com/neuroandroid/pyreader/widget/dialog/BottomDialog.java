package com.neuroandroid.pyreader.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.widget.dialog.base.PYDialog;

/**
 * Created by NeuroAndroid on 2017/6/16.
 */

public class BottomDialog extends PYDialog<BottomDialog> {
    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_bottom_dialog;
    }
}
