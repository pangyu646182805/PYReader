package com.neuroandroid.pyreader.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.bean.BookReadThemeBean;
import com.neuroandroid.pyreader.event.BookReadSettingEvent;
import com.neuroandroid.pyreader.interfaces.SimpleTextWatcher;
import com.neuroandroid.pyreader.utils.BookReadSettingUtils;
import com.neuroandroid.pyreader.utils.ColorUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.ColorPickerView;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.neuroandroid.pyreader.widget.dialog.base.DialogViewHelper;
import com.neuroandroid.pyreader.widget.dialog.base.PYDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by NeuroAndroid on 2017/7/31.
 */

public class ColorPickerDialog extends PYDialog<ColorPickerDialog> {
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_BACKGROUND = 2;

    private EditText mEtR;
    private EditText mEtG;
    private EditText mEtB;
    private ColorPickerView mColorPickerView;

    private boolean mFromColorPickerView;
    private int mCurrentType = TYPE_TEXT;
    private BookReadThemeBean mBookReadTheme;

    public ColorPickerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_color_picker_dialog;
    }

    @Override
    protected void initView() {
        DialogViewHelper viewHelper = getViewHelper();
        mEtR = viewHelper.getView(R.id.et_r);
        mEtG = viewHelper.getView(R.id.et_g);
        mEtB = viewHelper.getView(R.id.et_b);
        mColorPickerView = viewHelper.getView(R.id.picker_view);

        mBookReadTheme = BookReadSettingUtils.getBookReadTheme(mContext);
        int bookReadFontColor = mBookReadTheme.getBookReadFontColor();
        int[] toRGB = ColorUtils.colorToRGB(bookReadFontColor);
        mEtR.setText(String.valueOf(toRGB[0]));
        mEtG.setText(String.valueOf(toRGB[1]));
        mEtB.setText(String.valueOf(toRGB[2]));
        transformRGB(false);

        mColorPickerView.setColorPickerListener(color -> {
            mFromColorPickerView = true;
            int[] colorToRGB = ColorUtils.colorToRGB(color);
            mEtR.setText(String.valueOf(colorToRGB[0]));
            mEtG.setText(String.valueOf(colorToRGB[1]));
            mEtB.setText(String.valueOf(colorToRGB[2]));

            notifyColorPick(color);
        });

        mEtR.setOnTouchListener(new MyTouchListener(mEtR));
        mEtG.setOnTouchListener(new MyTouchListener(mEtG));
        mEtB.setOnTouchListener(new MyTouchListener(mEtB));

        mEtR.addTextChangedListener(new MyTextWatcher());
        mEtG.addTextChangedListener(new MyTextWatcher());
        mEtB.addTextChangedListener(new MyTextWatcher());

        NoPaddingTextView tvText = viewHelper.getView(R.id.tv_text);
        NoPaddingTextView tvBackground = viewHelper.getView(R.id.tv_background);
        tvText.setOnClickListener(view -> {
            if (mCurrentType == TYPE_BACKGROUND) {
                tvText.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                tvBackground.setTextColor(UIUtils.getColor(R.color.white));
                mCurrentType = TYPE_TEXT;
            }
        });
        tvBackground.setOnClickListener(view -> {
            if (mCurrentType == TYPE_TEXT) {
                tvText.setTextColor(UIUtils.getColor(R.color.white));
                tvBackground.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                mCurrentType = TYPE_BACKGROUND;
            }
        });
    }

    /**
     * 防止R，G，B的值超过255
     */
    private void preventOver255(EditText et, int value) {
        if (value > 255) {
            et.setText("255");
        }
    }

    private class MyTouchListener implements View.OnTouchListener {
        private EditText mEt;

        public MyTouchListener(EditText et) {
            mEt = et;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    L.e("onTouch ACTION_UP");
                    mFromColorPickerView = false;
                    mEt.clearFocus();
                    mEt.requestFocus();
                    break;
            }
            return false;
        }
    }

    private class MyTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!mFromColorPickerView) {
                transformRGB(true);
            }
        }
    }

    /**
     * 通知颜色改变
     */
    private void notifyColorPick(int color) {
        if (mCurrentType == TYPE_TEXT) {
            mBookReadTheme.setBookReadFontColor(color);
        } else {
            mBookReadTheme.setBookReadInterfaceBackgroundColor(color);
        }

        EventBus.getDefault().post(new BookReadSettingEvent()
                .setBookReadThemeBean(mBookReadTheme).setFromColorPickerDialog(true));
    }

    private void transformRGB(boolean notify) {
        String rValue = mEtR.getText().toString();
        String gValue = mEtG.getText().toString();
        String bValue = mEtB.getText().toString();

        int r;
        int g;
        int b;
        if (UIUtils.isEmpty(rValue)) {
            rValue = "0";
            r = 0;
            mEtR.setText(rValue);
        } else {
            r = Integer.parseInt(rValue);
            if (r > 255) mEtR.setText("255");
        }
        if (UIUtils.isEmpty(gValue)) {
            gValue = "0";
            g = 0;
            mEtG.setText(gValue);
        } else {
            g = Integer.parseInt(gValue);
            if (g > 255) mEtG.setText("255");
        }
        if (UIUtils.isEmpty(bValue)) {
            bValue = "0";
            b = 0;
            mEtB.setText(bValue);
        } else {
            b = Integer.parseInt(bValue);
            if (b > 255) mEtB.setText("255");
        }

        int color = ColorUtils.rgbToColor(r, g, b);
        mColorPickerView.generateLinearGradient(color);
        if (notify) notifyColorPick(color);
    }
}
