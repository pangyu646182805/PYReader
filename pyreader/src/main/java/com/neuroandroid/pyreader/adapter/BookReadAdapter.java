package com.neuroandroid.pyreader.adapter;

import android.content.Context;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.IMultiItemViewType;
import com.neuroandroid.pyreader.widget.reader.BookReadBean;
import com.neuroandroid.pyreader.widget.reader.BookReadView;
import com.neuroandroid.pyreader.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/7.
 */

public class BookReadAdapter extends BaseRvAdapter<BookReadBean> {
    private static final int VIEW_TYPE_HAS_CONTENT = 12;
    private static final int VIEW_TYPE_HAS_NO_CONTENT = 13;

    private int mPreChapter;
    private BookReadView.OnReadStateChangeListener mReadStateChangeListener;
    private String mUpdateTime;
    private int mBatteryLevel;

    public void setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
        notifyDataSetChanged();
    }

    public void setBatteryLevel(int batteryLevel) {
        mBatteryLevel = batteryLevel;
        notifyDataSetChanged();
    }

    public void setReadStateChangeListener(BookReadView.OnReadStateChangeListener readStateChangeListener) {
        mReadStateChangeListener = readStateChangeListener;
    }

    public void setRvBookRead(RecyclerViewPager rvBookRead) {
        rvBookRead.addOnPageChangedListener((oldPosition, newPosition) -> {
            BookReadBean item = getItem(newPosition);
            int currentChapter = item.getCurrentChapter();
            int currentPage = item.getCurrentPage();
            if (currentChapter == mPreChapter + 1 || currentChapter == mPreChapter - 1) {
                if (mReadStateChangeListener != null) {
                    // 章节发生变化
                    mReadStateChangeListener.onChapterChanged(mPreChapter, currentChapter);
                }
            }
            if (mReadStateChangeListener != null) {
                mReadStateChangeListener.onPageChanged(currentChapter, currentPage);
            }
            mPreChapter = currentChapter;
        });
    }

    public BookReadAdapter(Context context, List<BookReadBean> dataList, IMultiItemViewType<BookReadBean> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void convert(BaseViewHolder holder, BookReadBean item, int position, int viewType) {
        if (item != null) {
            BookReadView bookReadView = holder.getView(R.id.book_read_view);
            bookReadView.setBookReadBean(item, mUpdateTime, mBatteryLevel);
            if (mReadStateChangeListener != null) {
                bookReadView.setReadStateChangeListener(mReadStateChangeListener);
            }
        }
    }

    @Override
    protected IMultiItemViewType<BookReadBean> provideMultiItemViewType() {
        return new IMultiItemViewType<BookReadBean>() {
            @Override
            public int getItemViewType(int position, BookReadBean bookReadBean) {
                return bookReadBean == null ? VIEW_TYPE_HAS_NO_CONTENT : VIEW_TYPE_HAS_CONTENT;
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == VIEW_TYPE_HAS_CONTENT) {
                    return R.layout.item_book_read;
                } else {
                    return R.layout.item_book_read_error;
                }
            }
        };
    }
}
