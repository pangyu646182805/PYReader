package com.neuroandroid.pyreader.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.utils.UIUtils;

/**
 * Created by NeuroAndroid on 2017/6/20.
 */

public class CustomRefreshHeader extends FrameLayout implements IHeaderView {
    private Context mContext;
    private ImageView mIvRefreshArrow;
    private ImageView mIvLoading;
    private float mRefreshHeight;

    public CustomRefreshHeader(@NonNull Context context) {
        this(context, null);
    }

    public CustomRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_custom_refresh_header, this);
        mIvRefreshArrow = (ImageView) findViewById(R.id.iv_refresh_arrow);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);
        mRefreshHeight = UIUtils.getDimen(R.dimen.y244);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        float rotation = fraction * 180;
        if (rotation >= 180) rotation = 180;
        mIvRefreshArrow.setRotation(rotation);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        float rotation = fraction * 180;
        if (rotation >= 180) rotation = 180;
        mIvRefreshArrow.setRotation(rotation);
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        mIvRefreshArrow.setVisibility(View.GONE);
        mIvLoading.setVisibility(View.VISIBLE);
        ((AnimationDrawable) mIvLoading.getDrawable()).start();
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {
        mIvRefreshArrow.setVisibility(View.VISIBLE);
        mIvLoading.setVisibility(View.GONE);
        mIvRefreshArrow.setRotation(0f);
    }
}
