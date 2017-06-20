package com.neuroandroid.pyreader.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.widget.StateLayout;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by NeuroAndroid on 2017/6/13.
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IView<P> {
    /**
     * 把 LoadingLayout 放在基类统一处理，@Nullable 表明 View 可以为 null
     */
    @Nullable
    @BindView(R.id.state_layout)
    StateLayout mStateLayout;

    @Nullable
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    protected P mPresenter;
    private Unbinder mUnBinder;

    protected Activity mActivity;
    protected Context mContext;
    protected View mRootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            mUnBinder = ButterKnife.bind(this, mRootView);
            initPresenter();
            if (mToolbar != null) {
                getBaseActivity().setSupportActionBar(mToolbar);
                if (useOptionsMenu()) setHasOptionsMenu(true);
            }
            initView();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus()) EventBus.getDefault().register(this);
        initData();
        initListener();
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

    protected BaseActivity getBaseActivity() {
        if (mActivity instanceof BaseActivity) {
            return (BaseActivity) mActivity;
        }
        return null;
    }

    /**
     * 绑定布局文件
     */
    protected abstract int attachLayoutRes();

    protected void initPresenter() {}

    /**
     * 初始化视图控件
     */
    protected void initView() {}

    protected void initData() {}

    protected void initListener() {}

    /**
     * 是否使用EventBus(默认不适用)
     * 如果需要使用子类实现此方法并且返回true
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * Fragment是否使用ToolBar菜单
     * setHasOptionsMenu(true) 表示加载ToolBar菜单
     * 默认为false， 如需求子类实现此方法并且返回true
     */
    protected boolean useOptionsMenu() {
        return false;
    }
}
