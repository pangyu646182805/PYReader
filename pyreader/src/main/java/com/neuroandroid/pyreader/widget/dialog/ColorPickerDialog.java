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

        mBookReadTheme = BookReadSettingUtils.getBookReadTheme(mContext);
        mColorPickerView = viewHelper.getView(R.id.picker_view);
        mColorPickerView.setColorPickerListener(color -> {
            mFromColorPickerView = true;
            int[] colorToRGB = ColorUtils.colorToRGB(color);
            mEtR.setText(String.valueOf(colorToRGB[0]));
            mEtG.setText(String.valueOf(colorToRGB[1]));
            mEtB.setText(String.valueOf(colorToRGB[2]));

            notifyColorPick(color);
        });

        mEtR.setOnTouchListener(mTouchListener);
        mEtG.setOnTouchListener(mTouchListener);
        mEtB.setOnTouchListener(mTouchListener);

        mEtR.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mFromColorPickerView)
                    transformRGB();
            }
        });
        mEtG.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mFromColorPickerView)
                    transformRGB();
            }
        });
        mEtB.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mFromColorPickerView)
                    transformRGB();
            }
        });

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

    private View.OnTouchListener mTouchListener = (view, motionEvent) -> {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            mFromColorPickerView = false;
        }
        return true;
    };

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

    private void transformRGB() {
        int r = Integer.parseInt(mEtR.getText().toString());
        int g = Integer.parseInt(mEtG.getText().toString());
        int b = Integer.parseInt(mEtB.getText().toString());

        int color = ColorUtils.rgbToColor(r, g, b);
        mColorPickerView.generateLinearGradient(color);
    }
}
