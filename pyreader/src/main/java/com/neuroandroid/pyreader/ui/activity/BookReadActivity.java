package com.neuroandroid.pyreader.ui.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BookReadAdapter;
import com.neuroandroid.pyreader.base.BaseActivity;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.ChapterRead;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.mvp.contract.IBookReadContract;
import com.neuroandroid.pyreader.mvp.presenter.BookReadPresenter;
import com.neuroandroid.pyreader.widget.reader.BookReadFactory;
import com.neuroandroid.pyreader.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/7/6.
 * 小说阅读界面
 */
public class BookReadActivity extends BaseActivity<IBookReadContract.Presenter> implements IBookReadContract.View {
    @BindView(R.id.rv_book_read)
    RecyclerViewPager mRvBookRead;

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

        mRvBookRead.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBookReadAdapter = new BookReadAdapter(this, null, null);
        mRvBookRead.setAdapter(mBookReadAdapter);
    }

    @Override
    protected void initData() {
        mBookReadFactory = BookReadFactory.getInstance();
        mBookReadFactory.initBookReadMap();
        mFromSD = getIntent().getBooleanExtra(Constant.INTENT_FROM_SD, false);
        mBooksBean = (Recommend.BooksBean) getIntent().getSerializableExtra(Constant.INTENT_BOOK_BEAN);
        mBookId = mBooksBean.getBookId();
        mPresenter.getBookMixAToc(mBookId);
    }

    @Override
    public void showBookToc(List<BookMixAToc.MixToc.Chapters> list) {
        mChapterList = list;
        mPresenter.getChapterRead(mChapterList.get(0).getLink(), 0);
        mPresenter.getChapterRead(mChapterList.get(1).getLink(), 1);
        mPresenter.getChapterRead(mChapterList.get(2).getLink(), 2);
        mPresenter.getChapterRead(mChapterList.get(3).getLink(), 3);
    }

    @Override
    public void showChapterRead(ChapterRead.Chapter data, int chapter) {
        mBookReadFactory.setChapterContent(mBookReadAdapter, data, chapter);
    }
}
