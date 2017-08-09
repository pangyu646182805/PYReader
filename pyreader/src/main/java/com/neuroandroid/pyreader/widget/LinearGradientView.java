package com.neuroandroid.pyreader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.materialcab.Util;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.utils.ColorUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

/**
 * Created by NeuroAndroid on 2017/8/4.
 */

public class LinearGradientView extends View {
    private Context mContext;
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private int mColor;

    public LinearGradientView(Context context) {
        this(context, null);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        if (ThemeUtils.isDarkMode()) {
            mColor = UIUtils.getColor(R.color.backgroundColorDark);
        } else {
            mColor = Util.resolveColor(mContext, R.attr.colorPrimary, 0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLinearGradient = new LinearGradient(0, 0, 0, getMeasuredHeight(),
                new int[]{ColorUtils.adjustAlpha(mColor, 0.95f),
                        ColorUtils.adjustAlpha(mColor, 0.55f),
                        ColorUtils.adjustAlpha(mColor, 0.25f)}, null, Shader.TileMode.MIRROR);
        mPaint.setShader(mLinearGradient);
    }
}
