package com.neuroandroid.pyreader.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.afollestad.materialcab.Util;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.SelectAdapter;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.provider.PYReaderStore;
import com.neuroandroid.pyreader.ui.activity.BookReadActivity;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/7/21.
 */

public class CatalogFragment extends BaseFragment {
    @BindView(R.id.fl_order)
    FrameLayout mFlOrder;
    @BindView(R.id.fl_jump_current)
    FrameLayout mFlJumpCurrent;
    @BindView(R.id.rv_catalog)
    FastScrollRecyclerView mRvCatalog;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;

    private List<BookMixAToc.MixToc.Chapters> mChaptersList;
    private CatalogAdapter mCatalogAdapter;
    private int mReadChapter;
    private LinearLayoutManager mLayoutManager;
    /**
     * 顺序还是逆序
     * 默认顺序
     */
    private boolean mOrderFlag = true;
    private PYReaderStore mPyReaderStore;

    private String mBookId;

    public void setChaptersList(List<BookMixAToc.MixToc.Chapters> chaptersList) {
        mChaptersList = chaptersList;
        if (mCatalogAdapter != null) {
            if (mCatalogAdapter.getItemCount() <= 0) {
                mCatalogAdapter.replaceAll(mChaptersList);
            }
        }
    }

    /**
     * 设置当前阅读到第几章节
     */
    public void setReadChapter(int readChapter) {
        this.mReadChapter = readChapter;
        if (mCatalogAdapter != null) {
            mCatalogAdapter.select(readChapter);
            mRvCatalog.scrollToPosition(readChapter);
        }
    }

    /**
     * 设置bookId
     */
    public void setBookId(String bookId) {
        this.mBookId = bookId;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_catalog;
    }

    @Override
    protected void initView() {
        if (ThemeUtils.isDarkMode()) {
            mLlBottom.setBackgroundColor(UIUtils.getColor(R.color.backgroundColorDark));
        }

        mLayoutManager = new LinearLayoutManager(mContext);
        mRvCatalog.setLayoutManager(new LinearLayoutManager(mContext));
        mRvCatalog.addItemDecoration(DividerUtils.generateHorizontalDivider(mContext, R.dimen.y1, ThemeUtils.getSplitColorRes()));
        mCatalogAdapter = new CatalogAdapter(mContext, mChaptersList, R.layout.item_catalog);
        mCatalogAdapter.clearRvAnim(mRvCatalog);
        mCatalogAdapter.longTouchSelectModeEnable(false);
        mRvCatalog.setAdapter(mCatalogAdapter);
    }

    @Override
    protected void initData() {
        mPyReaderStore = PYReaderStore.getInstance(mContext);
    }

    @Override
    protected void initListener() {
        mFlOrder.setOnLongClickListener(view -> {
            ShowUtils.showToast("排序");
            return true;
        });
        mFlJumpCurrent.setOnLongClickListener(view -> {
            ShowUtils.showToast("跳转至当前章节");
            return true;
        });
        mFlJumpCurrent.setOnClickListener(view -> mRvCatalog.scrollToPosition(mReadChapter));
        mFlOrder.setOnClickListener(view -> {
            if (mOrderFlag) {
                reverseOrder();
            } else {
                order();
            }
        });
        mCatalogAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<BookMixAToc.MixToc.Chapters>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, BookMixAToc.MixToc.Chapters chapters) {
                if (isSelected) {
                    position = mOrderFlag ? position : mChaptersList.size() - 1 - position;
                    BookReadActivity bookReadActivity = (BookReadActivity) mActivity;
                    bookReadActivity.closeDrawer();
                    bookReadActivity.jumpToTargetChapter(position);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     * 顺序
     */
    public void order() {
        if (mCatalogAdapter != null) {
            mOrderFlag = true;
            mReadChapter = mChaptersList.size() - 1 - mReadChapter;
            Collections.reverse(mChaptersList);
            mCatalogAdapter.replaceAll(mChaptersList);
            mRvCatalog.scrollToPosition(mReadChapter);
        }
    }

    /**
     * 逆序
     */
    public void reverseOrder() {
        if (mCatalogAdapter != null) {
            mOrderFlag = false;
            mReadChapter = mChaptersList.size() - 1 - mReadChapter;
            Collections.reverse(mChaptersList);
            mCatalogAdapter.replaceAll(mChaptersList);
            mRvCatalog.scrollToPosition(mReadChapter);
        }
    }

    /**
     * 恢复顺序
     */
    public void restoreOrder() {
        if (!mOrderFlag) {
            // 是逆序才去恢复为顺序
            reverseOrder();
        }
    }

    private class CatalogAdapter extends SelectAdapter<BookMixAToc.MixToc.Chapters> {
        public CatalogAdapter(Context context, List<BookMixAToc.MixToc.Chapters> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, BookMixAToc.MixToc.Chapters item, int position, int viewType) {
            boolean noCached;
            if (mOrderFlag) {
                noCached = mPyReaderStore.findChapterByBookId(position, mBookId);
            } else {
                noCached = mPyReaderStore.findChapterByBookId(mChaptersList.size() - 1 - position, mBookId);
            }
            int mainColor = ThemeUtils.getMainColor();
            int threeLevelColor = ThemeUtils.getThreeLevelColor();

            holder.setText(R.id.tv_catalog, item.getTitle())
                    .setTextColor(R.id.tv_catalog, item.isSelected() ?
                            Util.resolveColor(this.mContext, R.attr.colorPrimary, 0) : noCached ?
                            threeLevelColor : mainColor);
        }
    }
}
