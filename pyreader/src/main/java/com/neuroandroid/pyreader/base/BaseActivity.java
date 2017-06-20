package com.neuroandroid.pyreader.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.utils.SystemUtils;
import com.neuroandroid.pyreader.widget.StateLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by NeuroAndroid on 2017/6/13.
 */

public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IView<P> {
    /**
     * 把 LoadingLayout 放在基类统一处理，@Nullable 表明 View 可以为 null
     */
    @Nullable
    @BindView(R.id.state_layout)
    StateLayout mStateLayout;

    @Nullable
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Nullable
    @BindView(R.id.status_bar)
    View mStatusBar;

    protected P mPresenter;
    private Unbinder mUnBinder;
    /**
     * 是否支持沉浸式状态栏
     */
    public boolean mImmersive;
    /**
     * 状态栏高度
     */
    protected int mStatusBarHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        if (useEventBus()) EventBus.getDefault().register(this);
        mUnBinder = ButterKnife.bind(this);
        // 沉浸式状态栏相关
        if (supportImmersive()) {
            mImmersive = SystemUtils.setTranslateStatusBar(this);
            if (mImmersive) {
                mStatusBarHeight = SystemUtils.getStatusHeight(this);
                setStatusBar(mStatusBarHeight);
            }
        }
        initPresenter();
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        initView();
        initData();
        initListener();
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 是否支持沉浸式状态栏(默认支持)
     */
    protected boolean supportImmersive() {
        return true;
    }

    /**
     * 是否使用EventBus(默认不适用)
     * 如果需要使用子类实现此方法并且返回true
     */
    protected boolean useEventBus() {
        return false;
    }

    protected void initPresenter() {
    }

    protected void initView() {}

    protected void initData() {
    }

    protected void initListener() {
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        if (mStateLayout != null) {
            mStateLayout.setStatus(StateLayout.STATE_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (mStateLayout != null) {
            mStateLayout.hide();
        }
    }

    @Override
    public void showError(StateLayout.OnRetryListener onRetryListener) {
        if (mStateLayout != null) {
            mStateLayout.setStatus(StateLayout.STATE_ERROR);
            mStateLayout.setOnRetryListener(onRetryListener);
        }
    }

    @Override
    public void showTip(String tip) {

    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 设置状态栏高度
     */
    private void setStatusBar(int statusBarHeight) {
        if (mStatusBar != null) {
            mStatusBar.getLayoutParams().height = statusBarHeight;
            mStatusBar.requestLayout();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源
        if (mPresenter != null) mPresenter.onDestroy();
        if (mUnBinder != Unbinder.EMPTY) mUnBinder.unbind();
        if (useEventBus()) EventBus.getDefault().unregister(this);
        this.mUnBinder = null;
        this.mPresenter = null;
    }
}
