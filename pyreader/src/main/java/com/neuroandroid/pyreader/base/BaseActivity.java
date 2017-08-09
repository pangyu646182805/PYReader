package com.neuroandroid.pyreader.base;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.utils.SystemUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.StateLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.polaric.colorful.Colorful;

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

    private String mThemeString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mThemeString = Colorful.getThemeString();
        this.setTheme(Colorful.getThemeDelegate().getStyle());
        if (Build.VERSION.SDK_INT >= 21) {
            if (Colorful.getThemeDelegate().isTranslucent()) {
                this.getWindow().addFlags(67108864);
            }

            ActivityManager.TaskDescription tDesc =
                    new ActivityManager.TaskDescription(null, null, this.getResources().getColor(Colorful.getThemeDelegate().getPrimaryColor().getColorRes()));
            this.setTaskDescription(tDesc);
        }

        setContentView(attachLayoutRes());
        if (useEventBus()) EventBus.getDefault().register(this);
        mUnBinder = ButterKnife.bind(this);
        // 沉浸式状态栏相关
        if (supportImmersive()) {
            mImmersive = SystemUtils.setTranslateStatusBar(this);
            if (mImmersive) {
                mStatusBarHeight = SystemUtils.getStatusHeight(this);
                if (ThemeUtils.isDarkMode()) {
                    mStatusBar.setBackgroundColor(translateStatusBar() ? Color.TRANSPARENT :
                            UIUtils.getColor(R.color.backgroundColorDark));
                }
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
     * 设置返回按钮
     */
    protected void setDisplayHomeAsUpEnabled() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setToolbarTitle(CharSequence title) {
        if (mToolbar != null) getSupportActionBar().setTitle(title);
    }

    protected void setToolbarTitle(@StringRes int resId) {
        if (mToolbar != null) getSupportActionBar().setTitle(resId);
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

    protected void initView() {
    }

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

    protected View getStatusBar() {
        return mStatusBar;
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

    /**
     * 是否是透明状态栏
     */
    protected boolean translateStatusBar() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeTheme();
    }

    public void changeTheme() {
        if (!Colorful.getThemeString().equals(this.mThemeString)) {
            Log.d("Colorful", "Theme change detected, restarting activity");
            this.recreate();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
