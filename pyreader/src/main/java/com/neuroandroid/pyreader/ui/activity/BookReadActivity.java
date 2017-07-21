package com.neuroandroid.pyreader.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BookReadAdapter;
import com.neuroandroid.pyreader.base.BaseActivity;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.manager.CacheManager;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.ChapterRead;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.mvp.contract.IBookReadContract;
import com.neuroandroid.pyreader.mvp.presenter.BookReadPresenter;
import com.neuroandroid.pyreader.provider.PYReaderStore;
import com.neuroandroid.pyreader.ui.fragment.BookDetailCommunityFragment;
import com.neuroandroid.pyreader.ui.fragment.ChapterListFragment;
import com.neuroandroid.pyreader.utils.FragmentUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.TimeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.reader.BookReadFactory;
import com.neuroandroid.pyreader.widget.reader.BookReadView;
import com.neuroandroid.pyreader.widget.recyclerviewpager.RecyclerViewPager;
import com.xw.repo.BubbleSeekBar;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/7/6.
 * 小说阅读界面
 * 加载逻辑：
 * 加载当前章节
 * 加载当前章节的前一章
 * 加载当前章节的后n章
 * 如果当前章节已经缓存则直接加载缓存中的章节
 * <p>
 * 总共加载(1 + 1 + n)章内容
 * 保存的阅读位置：[3, 11]
 * 表示阅读到了第3章的第11页
 */
public class BookReadActivity extends BaseActivity<IBookReadContract.Presenter>
        implements IBookReadContract.View, ChapterListFragment.OnDrawerPageChangeListener {
    private static final int MSG_UPDATE_SYSTEM_TIME = 34;

    /**
     * 加载当前章节的后n章
     */
    public static final int ONE_TIME_LOAD_CHAPTER = 3;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rv_book_read)
    RecyclerViewPager mRvBookRead;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.ll_bottom_control)
    LinearLayout mLlBottomControl;
    @BindView(R.id.ll_catalog)
    LinearLayout mLlCatalog;
    @BindView(R.id.sb_progress)
    BubbleSeekBar mSbProgress;
    @BindView(R.id.view_cover)
    View mViewCover;

    // 书籍是否来自SD卡
    private boolean mFromSD;
    /**
     * 书籍id
     */
    private String mBookId;
    private Recommend.BooksBean mBooksBean;

    /**
     * 章节列表
     */
    private List<BookMixAToc.MixToc.Chapters> mChapterList;

    private BookReadFactory mBookReadFactory;
    private BookReadAdapter mBookReadAdapter;
    private PYReaderStore mPYReaderStore;
    private String mBookTitle;
    private BookReadHandler mBookReadHandler;
    private String mPreUpdateTime;
    private BatteryBroadcastReceiver mBatteryReceiver;
    private ChapterListFragment mChapterListFragment;

    private int mAppBarHeight, mBottomControlHeight;
    /**
     * ToolBar和底部控制台是否显示
     */
    private boolean mShowAppBarAndBottomControl;

    private BaseFragment mCurrentFragment;

    @Override
    public void onPageSelected(boolean isLast) {
        if (isLast) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else if (mDrawerLayout.getDrawerLockMode(GravityCompat.START) == DrawerLayout.LOCK_MODE_UNLOCKED) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }
    }

    private static class BookReadHandler extends Handler {
        private WeakReference<BookReadActivity> mActivity;

        public BookReadHandler(BookReadActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final BookReadActivity bookReadActivity = mActivity.get();
            if (bookReadActivity != null) {
                switch (msg.what) {
                    case MSG_UPDATE_SYSTEM_TIME:
                        bookReadActivity.updateSystemTime();
                        break;
                }
            }
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter = new BookReadPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_book_read;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle("");
        UIUtils.fullScreen(this, true);

        mViewCover.setVisibility(View.GONE);

        mRvBookRead.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBookReadAdapter = new BookReadAdapter(this, null, null);
        mBookReadAdapter.clearRvAnim(mRvBookRead);
        mBookReadAdapter.setRvBookRead(mRvBookRead);
        mRvBookRead.setAdapter(mBookReadAdapter);

        mChapterListFragment = (ChapterListFragment) getSupportFragmentManager().findFragmentById(R.id.left_menu);
        mChapterListFragment.setImmersive(mImmersive);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void initData() {
        mBookReadHandler = new BookReadHandler(this);
        updateSystemTime();
        registerBatteryBroadcastReceiver();
        mBookReadFactory = BookReadFactory.getInstance();
        mBookReadFactory.initBookReadMap();
        mPYReaderStore = PYReaderStore.getInstance(this);
        mFromSD = getIntent().getBooleanExtra(Constant.INTENT_FROM_SD, false);
        mBooksBean = (Recommend.BooksBean) getIntent().getSerializableExtra(Constant.INTENT_BOOK_BEAN);
        mBookTitle = mBooksBean.getTitle();
        mBookId = mBooksBean.getBookId();
        List<BookMixAToc.MixToc.Chapters> chapterList = CacheManager.getChapterList(this, mBookId);
        if (chapterList == null) {
            mPresenter.getBookMixAToc(mBookId);
        } else {
            L.e("从缓存加载章节列表");
            mChapterListFragment.setChaptersList(chapterList);
            String json = new Gson().toJson(chapterList);
            L.e("章节列表 : " + json);
            mChapterList = chapterList;
            loadChapterLogic();
        }
    }

    @Override
    protected void initListener() {
        mBookReadAdapter.setReadStateChangeListener(new BookReadView.OnReadStateChangeListener() {
            @Override
            public void onChapterChanged(int oldChapter, int newChapter) {
                L.e("oldChapter : " + oldChapter + " newChapter : " + newChapter);
            }

            @Override
            public void onPageChanged(int currentChapter, int page) {
                L.e("currentChapter : " + currentChapter + " page : " + page);
                CacheManager.saveReadPosition(BookReadActivity.this, mBookId, currentChapter, page);
            }

            @Override
            public void onCenterClick() {
                L.e("点击了中心区域");
                if (!mShowAppBarAndBottomControl) {
                    // 如果没有显示则显示
                    showAppBarAndBottomControl();
                } else {
                    hideAppBarAndBottomControl();
                }
            }

            @Override
            public void onNextPage() {
                L.e("下一页");
            }

            @Override
            public void onPrePage() {
                L.e("上一页");
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                UIUtils.fullScreen(BookReadActivity.this, false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                UIUtils.fullScreen(BookReadActivity.this, true);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });
        mDrawerLayout.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDrawerLayout.closeDrawers();
                    break;
            }
            return false;
        });
        mAppBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAppBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mAppBarHeight = mAppBarLayout.getHeight();
                mBottomControlHeight = mLlBottomControl.getHeight();
                mAppBarLayout.setTranslationY(-mAppBarHeight);
                mLlBottomControl.setTranslationY(mBottomControlHeight);
            }
        });
        mLlCatalog.setOnClickListener(view ->
                hideAppBarAndBottomControl(() -> {
                    mChapterListFragment.setCurrentItem(ChapterListFragment.CATALOG_POSITION);
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }));
        mViewCover.setOnClickListener(view -> hideAppBarAndBottomControl());
    }

    @Override
    public void showBookToc(List<BookMixAToc.MixToc.Chapters> list) {
        mChapterList = list;
        mChapterListFragment.setChaptersList(list);
        CacheManager.saveChapterList(this, mBookId, mChapterList);
        loadChapterLogic();
    }

    /**
     * 加载逻辑
     */
    private void loadChapterLogic() {
        int[] readPosition = CacheManager.getReadPosition(this, mBookId);
        int readChapter = readPosition[0];
        int readPage = readPosition[1];
        for (int i = 0; i < 2 + ONE_TIME_LOAD_CHAPTER; i++) {
            if (readChapter == 0) {
                loadChapterContent(i);
            } else {
                // 共16章  0, 1...11, 12, 13, 14, 15
                int diff = readChapter - (mChapterList.size() - ONE_TIME_LOAD_CHAPTER - 1);
                if (diff > 0) {
                    // 当前阅读到第13章, 加载12, 13, 14, 15章  diff = 1
                    // 当前阅读到第14章, 加载13, 14, 15章  diff = 2
                    // 当前阅读到第15章, 加载14, 15章  diff = 3
                    if (i >= diff) {
                        loadChapterContent(readChapter + (i - 2));
                    }
                } else {
                    // 当前阅读到n(1-12)章, 加载n-1, n, 后面3章
                    // 比如当前阅读到第1章, 加载0, 1, 2, 3, 4章
                    loadChapterContent(readChapter + (i - 1));
                }
            }
        }
    }

    /**
     * 如果有缓存则加载缓存中的章节内容
     * 否则直接网络加载章节内容
     *
     * @param chapter 加载第几章节
     */
    private void loadChapterContent(int chapter) {
        if (mPYReaderStore.findChapterByBookId(chapter, mBookId)) {
            mPresenter.getChapterRead(mChapterList.get(chapter).getLink(), chapter);
        } else {
            mBookReadFactory.setChapterContent(mBookReadAdapter, mPYReaderStore.getChapter(chapter, mBookId),
                    chapter, mChapterList.get(chapter).getTitle(), mBookTitle);
        }
    }

    @Override
    public void showChapterRead(ChapterRead.Chapter data, int chapter) {
        // 缓存
        mPYReaderStore.addChapter(chapter, mBookId, data.getBody());
        mBookReadFactory.setChapterContent(mBookReadAdapter, mPYReaderStore.getChapter(chapter, mBookId),
                chapter, mChapterList.get(chapter).getTitle(), mBookTitle);
    }

    /**
     * 显示ToolBar和底部控制栏
     */
    private void showAppBarAndBottomControl() {
        mViewCover.setVisibility(View.VISIBLE);
        ViewCompat.animate(mAppBarLayout).translationY(0).setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        mShowAppBarAndBottomControl = true;
                        UIUtils.fullScreen(BookReadActivity.this, false);
                    }
                }).start();
        ViewCompat.animate(mLlBottomControl).translationY(0).setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .setUpdateListener(view -> mSbProgress.correctOffsetWhenContainerOnScrolling()).start();
    }

    private void hideAppBarAndBottomControl() {
        hideAppBarAndBottomControl(null);
    }

    private void hideAppBarAndBottomControl(OnAppBarAndBottomControlActivityListener appBarAndBottomControlActivityListener) {
        mViewCover.setVisibility(View.GONE);
        ViewCompat.animate(mAppBarLayout).translationY(-mAppBarHeight).setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        mShowAppBarAndBottomControl = false;
                        UIUtils.fullScreen(BookReadActivity.this, true);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        if (appBarAndBottomControlActivityListener != null) {
                            appBarAndBottomControlActivityListener.onAppBarAndBottomControlClosed();
                        }
                    }
                }).start();
        ViewCompat.animate(mLlBottomControl).translationY(mBottomControlHeight).setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .setUpdateListener(view -> mSbProgress.correctOffsetWhenContainerOnScrolling()).start();
    }

    /**
     * 打开BookDetailCommunityFragment
     * index : (0 : 讨论  1 : 书评)
     */
    public void openBookDetailCommunityFragment(int index) {
        UIUtils.fullScreen(this, false);
        mCurrentFragment = new BookDetailCommunityFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BOOK_ID, mBookId);
        bundle.putString(Constant.BOOK_TITLE, mBookTitle);
        bundle.putInt(Constant.BOOK_DETAIL_COMMUNITY_INDEX, index);
        mCurrentFragment.setArguments(bundle);
        FragmentUtils.replaceFragment(getSupportFragmentManager(), mCurrentFragment, R.id.fl_container, false);
    }

    private void updateSystemTime() {
        String updateTime = TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm");
        if (UIUtils.isEmpty(mPreUpdateTime)) {
            mBookReadAdapter.setUpdateTime(updateTime);
        }
        if (!UIUtils.isEmpty(mPreUpdateTime) && !mPreUpdateTime.equals(updateTime)) {
            L.e("更新UI : " + updateTime);
            mBookReadAdapter.setUpdateTime(updateTime);
        }
        mPreUpdateTime = updateTime;
        mBookReadHandler.sendEmptyMessageDelayed(MSG_UPDATE_SYSTEM_TIME, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_read, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:

                break;
            case R.id.action_bookmark:

                break;
            case R.id.action_community:
                hideAppBarAndBottomControl(() -> openBookDetailCommunityFragment(0));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 注册更新电量系统广播
     */
    private void registerBatteryBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mBatteryReceiver = new BatteryBroadcastReceiver();
        registerReceiver(mBatteryReceiver, filter);
    }

    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            updateBatteryImage(level);
        }
    }

    private void updateBatteryImage(int level) {
        L.e("level : " + level);
        if (level < 0) level = 0;
        if (level > 100) level = 100;
        mBookReadAdapter.setBatteryLevel(level);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null) {
            UIUtils.fullScreen(this, true);
            FragmentUtils.removeFragment(mCurrentFragment);
            mCurrentFragment = null;
            return;
        }
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookReadHandler.removeCallbacksAndMessages(null);
        mBookReadHandler = null;
        if (mBatteryReceiver != null) {
            unregisterReceiver(mBatteryReceiver);
            mBatteryReceiver = null;
        }
    }

    public interface OnAppBarAndBottomControlActivityListener {
        void onAppBarAndBottomControlClosed();
    }
}
