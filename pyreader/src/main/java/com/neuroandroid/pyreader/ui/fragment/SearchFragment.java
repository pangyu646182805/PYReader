package com.neuroandroid.pyreader.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.ui.activity.MainActivity;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/22.
 */

public class SearchFragment extends BaseFragment implements MainActivity.MainActivityFragmentCallbacks {
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;

    private int mAppBarLayoutHeight, mLlSearchHeight;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        setStatusBar(mStatusBar);
    }

    @Override
    protected void initListener() {
        mAppBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAppBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mAppBarLayoutHeight = mAppBarLayout.getHeight();
                mAppBarLayout.setTranslationY(-mAppBarLayoutHeight);
                ViewCompat.animate(mAppBarLayout).translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();

                mLlSearchHeight = mLlSearch.getHeight();
                mLlSearch.setTranslationY(mLlSearchHeight);
                ViewCompat.animate(mLlSearch).translationY(0).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();
            }
        });
    }

    @Override
    public boolean handleBackPress() {
        return false;
    }
}
