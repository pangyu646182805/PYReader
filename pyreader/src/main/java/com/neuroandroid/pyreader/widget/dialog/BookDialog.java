package com.neuroandroid.pyreader.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.neuroandroid.pyreader.widget.dialog.base.PYDialog;

import java.util.Arrays;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/20.
 */

public class BookDialog extends PYDialog<BookDialog> {
    private static final List<String> FROM_SDCARD = Arrays.asList("删除", "批量管理");
    private static final List<String> FROM_NET = Arrays.asList("书籍详情", "缓存全本", "删除", "批量管理");
    /**
     * book是否来自于本地
     */
    private boolean isFromSDCard;
    private RecyclerView mRv;
    private BookDialogAdapter mBookDialogAdapter;
    private NoPaddingTextView mTvBookTitle;

    public BookDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_book_dialog;
    }

    @Override
    protected void initView() {
        mTvBookTitle = getViewHelper().getView(R.id.tv_book_title);
        mRv = getViewHelper().getView(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mBookDialogAdapter = new BookDialogAdapter(mContext, null, R.layout.item_book_dialog);
        mRv.setAdapter(mBookDialogAdapter);
    }

    public BookDialog setFromSDCard(boolean fromSDCard) {
        this.isFromSDCard = fromSDCard;
        mBookDialogAdapter.replaceAll(fromSDCard ? FROM_SDCARD : FROM_NET);
        return this;
    }

    public boolean isFromSDCard() {
        return this.isFromSDCard;
    }

    public BookDialog setBookTitle(String bookTitle) {
        mTvBookTitle.setText(bookTitle);
        return this;
    }

    public BookDialog setOnDialogItemClickListener(OnDialogItemClickListener<BookDialog, String> onDialogItemClickListener) {
        mBookDialogAdapter.setOnItemClickListener((holder, position, item) -> {
            if (onDialogItemClickListener != null) {
                onDialogItemClickListener.onDialogItemClick(this, holder, position, item);
            }
        });
        return this;
    }

    private class BookDialogAdapter extends BaseRvAdapter<String> {
        public BookDialogAdapter(Context context, List<String> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, String item, int position, int viewType) {
            holder.setText(R.id.tv, item);
        }
    }
}
