package com.neuroandroid.pyreader.ui.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.mmin18.widget.RealtimeBlurView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.SearchAdapter;
import com.neuroandroid.pyreader.adapter.SearchBooksAdapter;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.base.BaseResponse;
import com.neuroandroid.pyreader.manager.CacheManager;
import com.neuroandroid.pyreader.model.response.HotWord;
import com.neuroandroid.pyreader.model.response.SearchBooks;
import com.neuroandroid.pyreader.mvp.contract.ISearchContract;
import com.neuroandroid.pyreader.mvp.presenter.SearchPresenter;
import com.neuroandroid.pyreader.ui.activity.MainActivity;
import com.neuroandroid.pyreader.utils.NavigationUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.SoftKeyboardStateWatcher;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/22.
 */

public class SearchFragment extends BaseFragment<ISearchContract.Presenter> implements
        MainActivity.MainActivityFragmentCallbacks, ISearchContract.View {
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.blur_view)
    RealtimeBlurView mBlurView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
    @BindView(R.id.et_search)
    ClearEditText mEtSearch;
    @BindView(R.id.rv_search_result)
    RecyclerView mRvSearchResult;
    @BindView(R.id.ll_loading)
    LinearLayout mLlLoading;
    @BindView(R.id.sv_nested)
    NestedScrollView mSvNested;

    private int mAppBarLayoutHeight;
    private SearchAdapter mSearchAdapter;
    private List<BaseResponse> mSearchDataList = new ArrayList<>();
    private InputMethodManager mInputMethodManager;
    private SearchBooksAdapter mSearchBooksAdapter;
    private String mSearchStr;
    private boolean mSearch;

    @Override
    protected void initPresenter() {
        mPresenter = new SearchPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        setStatusBar(mStatusBar);
        mRvSearch.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        mSearchAdapter = new SearchAdapter(mContext, null, null);
        mSearchAdapter.setSearchFragment(this);
        mSearchAdapter.clearRvAnim(mRvSearch);
        mRvSearch.setAdapter(mSearchAdapter);

        mSearchBooksAdapter = new SearchBooksAdapter(mContext, null, R.layout.item_search_result);
        mRvSearchResult.setAdapter(mSearchBooksAdapter);
    }

    @Override
    protected void initData() {
        mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        mSearchDataList.add(null);
        mSearchDataList.add(null);
        mSearchAdapter.replaceAll(mSearchDataList);
        mPresenter.getHotWord();

        refreshSearchHistory();
    }

    /**
     * 刷新历史记录列表
     */
    private void refreshSearchHistory() {
        mSearchAdapter.set(SearchAdapter.VIEW_TYPE_SEARCH_HISTORY, CacheManager.getSearchHistoryList(mContext));
    }

    @Override
    protected void initListener() {
        mRootView.setOnTouchListener((view, motionEvent) -> true);
        mAppBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAppBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mAppBarLayoutHeight = mAppBarLayout.getHeight();
                mAppBarLayout.setTranslationY(-mAppBarLayoutHeight);
                ViewCompat.animate(mAppBarLayout).translationY(0).setDuration(600).setInterpolator(new DecelerateInterpolator()).start();

                ValueAnimator animator = ValueAnimator.ofInt(0, 80);
                animator.addUpdateListener(valueAnimator -> {
                    int blurRadius = (int) valueAnimator.getAnimatedValue();
                    mBlurView.setBlurRadius(blurRadius);
                });
                animator.setInterpolator(new DecelerateInterpolator());
                animator.setDuration(600);
                animator.start();
            }
        });
        mIvBack.setOnClickListener(view -> mActivity.onBackPressed());
        mEtSearch.setOnTouchListener((view, motionEvent) -> {
            mSvNested.smoothScrollTo(0, 0);
            return false;
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (UIUtils.isEmpty(editable.toString())) {
                    mSearchBooksAdapter.clear();
                }
            }
        });
        mEtSearch.setOnKeyListener((view, keyCode, keyEvent) -> {
            // 修改键盘回车键功能
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                mSearchStr = mEtSearch.getText().toString();
                if (UIUtils.isEmpty(mSearchStr)) {
                    ShowUtils.showToast("请输入搜索关键字");
                } else {
                    mSearch = true;
                    saveSearchHistory(mSearchStr);
                    hideSoftKeyboard();
                }
                return true;
            }
            return false;
        });
        SoftKeyboardStateWatcher watcher = new SoftKeyboardStateWatcher(mRootView, mContext);
        watcher.addSoftKeyboardStateListener(new SoftKeyboardStateWatcher.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
            }

            @Override
            public void onSoftKeyboardClosed() {
                mEtSearch.clearFocus();
                if (mSearch) {
                    searchBooks(mSearchStr);
                    mSearch = false;
                }
            }
        });
        mSearchBooksAdapter.setOnItemClickListener((holder, position, item) -> NavigationUtils.goToBookDetailPage(mActivity, item.getBookId()));
    }

    /**
     * 联网搜索书籍
     */
    public void searchBooks(String query) {
        showLoading();
        mPresenter.searchBooks(query);
    }

    /**
     * 保存搜索历史记录
     */
    public void saveSearchHistory(String history) {
        CacheManager.saveSearchHistory(mContext, history);

        refreshSearchHistory();
    }

    @Override
    public boolean handleBackPress() {
        return false;
    }

    @Override
    public void showHotWord(HotWord hotWord) {
        mSearchAdapter.set(SearchAdapter.VIEW_TYPE_HOT_WORD, hotWord);
    }

    @Override
    public void showSearchResult(SearchBooks searchBooks) {
        hideLoading();
        mSearchBooksAdapter.replaceAll(searchBooks.getBooks());
        UIUtils.getHandler().postDelayed(() -> mSvNested.smoothScrollTo(0, mRvSearch.getHeight()), 250);
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
        hideLoading();
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        if (mActivity.getCurrentFocus() != null) {
            if (mActivity.getCurrentFocus().getApplicationWindowToken() != null) {
                mInputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void showLoading() {
        if (mLlLoading != null) mLlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mLlLoading != null) mLlLoading.setVisibility(View.GONE);
    }
}
