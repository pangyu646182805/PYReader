package com.neuroandroid.pyreader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.utils.ColorPickerUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.UIUtils;

/**
 * Created by NeuroAndroid on 2017/7/31.
 */

public class ColorPickerView extends View {
    private final Context mContext;

    private float[] mColorHSV = new float[]{0f, 1f, 1f};

    /**
     * 颜色选择器圆盘的半径
     */
    private float mCircleRadius;

    /**
     * 颜色选择器圆盘的x坐标
     */
    private float mCircleX;

    /**
     * 颜色选择器圆盘的y坐标
     */
    private float mCircleY;

    /**
     * 绘制圆盘的画笔
     */
    private Paint mSweepGradientPaint;

    /**
     * 圆盘旁边选择器的宽度
     */
    private float mLinearGradientAreaWidth;

    /**
     * 圆盘旁边选择器的高度
     */
    private float mLinearGradientAreaHeight;

    /**
     * 绘制圆盘旁边选择器的画笔
     */
    private Paint mLinearGradientPaint;

    /**
     * 圆盘旁边选择器的区域
     */
    private RectF mLinearGradientArea = new RectF();

    private LinearGradient mLinearGradient;

    private int[] mLinearGradientColors = ColorPickerUtils.DEFAULT_LINEAR_GRADIENT_COLORS;

    public ColorPickerView(Context context) {
        this(context, null);
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mCircleRadius = UIUtils.getDimen(R.dimen.x160);

        mSweepGradientPaint = new Paint();
        mSweepGradientPaint.setDither(true);
        mSweepGradientPaint.setAntiAlias(true);

        mLinearGradientPaint = new Paint(mSweepGradientPaint);
        mLinearGradientPaint.setStyle(Paint.Style.FILL);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mSweepGradientPaint);

        canvas.drawRect(mLinearGradientArea, mLinearGradientPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCircleX = getMeasuredWidth() * 0.4f;
        mCircleY = getMeasuredHeight() * 0.5f;

        generateDefaultLinearGradient();

        SweepGradient sweepGradient = new SweepGradient(mCircleX, mCircleY, new int[]{
                0xffff0000, 0xffff00ff, 0xff0000ff, 0xff00ffff, 0xff00ff00, 0xffffff00, 0xffff0000
        }, new float[]{
                0f, 1 / 6f, 1 / 3f, 1 / 2f, 2 / 3f, 5 / 6f, 1f
        });
        mSweepGradientPaint.setShader(sweepGradient);
    }

    /**
     * 生成默认的LinearGradient
     * white--black--black
     */
    private void generateDefaultLinearGradient() {
        float gap = UIUtils.getDimen(R.dimen.x48);
        mLinearGradientArea.set(mCircleX + mCircleRadius + gap,
                mCircleY - mCircleRadius,
                mCircleX + mCircleRadius + 2.5f * gap,
                mCircleY + mCircleRadius);
        mLinearGradient = new LinearGradient(mLinearGradientArea.left,
                mLinearGradientArea.top, mLinearGradientArea.right, mLinearGradientArea.bottom,
                ColorPickerUtils.DEFAULT_LINEAR_GRADIENT_COLORS,
                ColorPickerUtils.LINEAR_GRADIENT_POSITIONS, Shader.TileMode.CLAMP);

        mLinearGradientPaint.setShader(mLinearGradient);
    }

    public void generateLinearGradient(int color) {
        mLinearGradientColors[1] = color;
        mLinearGradient = new LinearGradient(mLinearGradientArea.left,
                mLinearGradientArea.top, mLinearGradientArea.right, mLinearGradientArea.bottom,
                mLinearGradientColors, ColorPickerUtils.LINEAR_GRADIENT_POSITIONS, Shader.TileMode.CLAMP);

        mLinearGradientPaint.setShader(mLinearGradient);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                int cx = (int) (x - mCircleX);
                int cy = (int) (y - mCircleY);
                double d = Math.sqrt(cx * cx + cy * cy);

                if (d <= mCircleRadius) {
                    // 在圆内滑动
                    float degress = (float) (Math.toDegrees(Math.atan2(cy, cx)));
                    if (degress < 0) {
                        degress = Math.abs(degress);
                    } else {
                        degress = 360f - degress;
                    }
                    L.e("degress : " + degress);
                    mColorHSV[0] = degress;
                    int color = Color.HSVToColor(mColorHSV);
                    generateLinearGradient(color);
                    if (mColorPickerListener != null) {
                        mColorPickerListener.onColorPicker(color);
                    }
                }
                if (mLinearGradientArea.contains(x, y)) {
                    float percent = (y - mLinearGradientArea.top) / mLinearGradientArea.height();
                    int color = ColorPickerUtils.getColor(mLinearGradientColors, percent);
                    if (mColorPickerListener != null) {
                        mColorPickerListener.onColorPicker(color);
                    }
                }
                break;
        }
        return true;
    }

    private OnColorPickerListener mColorPickerListener;

    public void setColorPickerListener(OnColorPickerListener colorPickerListener) {
        mColorPickerListener = colorPickerListener;
    }

    public interface OnColorPickerListener {
        void onColorPicker(int color);
    }
}
