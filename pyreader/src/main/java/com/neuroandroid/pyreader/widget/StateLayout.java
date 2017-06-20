package com.neuroandroid.pyreader.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.neuroandroid.pyreader.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by NeuroAndroid on 2017/6/13.
 */

public class StateLayout extends FrameLayout implements View.OnClickListener {
    public static final int STATE_HIDE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;

    private Context mContext;
    private int mCurrentStatus = STATE_HIDE;
    private LinearLayout mLlError;
    private LinearLayout mLlLoading;
    private ImageView mIvError;
    private NoPaddingTextView mTvError;
    private NoPaddingTextView mBtnReload;
    private int mImgRes;
    private String mErrorText, mReloadBtnText;
    private int mImgWidth;
    private int mImgHeight;

    public StateLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
        mImgRes = typedArray.getResourceId(R.styleable.StateLayout_state_layout_img_res, -1);
        mErrorText = typedArray.getString(R.styleable.StateLayout_state_layout_error_text);
        mReloadBtnText = typedArray.getString(R.styleable.StateLayout_state_layout_reload_text);
        mImgWidth = (int) typedArray.getDimension(R.styleable.StateLayout_state_layout_img_width, -1);
        mImgHeight = (int) typedArray.getDimension(R.styleable.StateLayout_state_layout_img_height, -1);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_state, this);
        mLlError = (LinearLayout) findViewById(R.id.ll_error);
        mLlLoading = (LinearLayout) findViewById(R.id.ll_loading);
        mIvError = (ImageView) findViewById(R.id.iv_error);
        mTvError = (NoPaddingTextView) findViewById(R.id.tv_error);
        mBtnReload = (NoPaddingTextView) findViewById(R.id.btn_reload);
        switchView();
        mBtnReload.setOnClickListener(this);
        if (mImgRes != -1) {
            setImageResource(mImgRes);
        }
        if (!TextUtils.isEmpty(mErrorText)) {
            setErrorText(mErrorText);
        }
        if (!TextUtils.isEmpty(mReloadBtnText)) {
            setReloadBtnText(mReloadBtnText);
        }
        if (mImgWidth != -1) {
            mIvError.getLayoutParams().width = mImgWidth;
        }
        if (mImgHeight != -1) {
            mIvError.getLayoutParams().height = mImgHeight;
        }
        mIvError.requestLayout();
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mCurrentStatus = STATE_HIDE;
        switchView();
    }

    /**
     * 设置状态
     */
    public void setStatus(@LoadingState int status) {
        mCurrentStatus = status;
        switchView();
    }

    /**
     * 设置图片资源
     */
    public void setImageResource(int resId) {
        mIvError.setImageResource(resId);
    }

    /**
     * 设置提示文本
     */
    public void setErrorText(String errorText) {
        mTvError.setText(errorText);
    }

    /**
     * 设置重新加载文本
     */
    public void setReloadBtnText(String reloadBtnText) {
        mBtnReload.setText(reloadBtnText);
    }

    /**
     * 获取状态
     */
    public int getStatus() {
        return mCurrentStatus;
    }

    /**
     * 切换不同状态
     */
    private void switchView() {
        switch (mCurrentStatus) {
            case STATE_LOADING:
                setVisibility(VISIBLE);
                mLlError.setVisibility(GONE);
                mLlLoading.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                setVisibility(VISIBLE);
                mLlError.setVisibility(VISIBLE);
                mLlLoading.setVisibility(View.GONE);
                break;
            case STATE_HIDE:
                setVisibility(GONE);
                break;
        }
    }

    private OnRetryListener mOnRetryListener;

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }

    @Override
    public void onClick(View view) {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_HIDE, STATE_LOADING, STATE_ERROR})
    public @interface LoadingState {}
}
