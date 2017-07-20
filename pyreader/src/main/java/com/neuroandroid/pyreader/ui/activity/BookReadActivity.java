package com.neuroandroid.pyreader.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BookReadAdapter;
import com.neuroandroid.pyreader.base.BaseActivity;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.manager.CacheManager;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.ChapterRead;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.mvp.contract.IBookReadContract;
import com.neuroandroid.pyreader.mvp.presenter.BookReadPresenter;
import com.neuroandroid.pyreader.provider.PYReaderStore;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.TimeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.reader.BookReadFactory;
import com.neuroandroid.pyreader.widget.reader.BookReadView;
import com.neuroandroid.pyreader.widget.recyclerviewpager.RecyclerViewPager;

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
public class BookReadActivity extends BaseActivity<IBookReadContract.Presenter> implements IBookReadContract.View {
    private static final int MSG_UPDATE_SYSTEM_TIME = 34;

    /**
     * 加载当前章节的后n章
     */
    public static final int ONE_TIME_LOAD_CHAPTER = 3;

    @BindView(R.id.rv_book_read)
    RecyclerViewPager mRvBookRead;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

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
        UIUtils.fullScreen(this, true);
        setDisplayHomeAsUpEnabled();
        setToolbarTitle("");

        mRvBookRead.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBookReadAdapter = new BookReadAdapter(this, null, null);
        mBookReadAdapter.clearRvAnim(mRvBookRead);
        mBookReadAdapter.setRvBookRead(mRvBookRead);
        mRvBookRead.setAdapter(mBookReadAdapter);
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
            }
        });
    }

    @Override
    public void showBookToc(List<BookMixAToc.MixToc.Chapters> list) {
        mChapterList = list;
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
    protected void onDestroy() {
        super.onDestroy();
        mBookReadHandler.removeCallbacksAndMessages(null);
        mBookReadHandler = null;
        if (mBatteryReceiver != null) {
            unregisterReceiver(mBatteryReceiver);
            mBatteryReceiver = null;
        }
    }
}
