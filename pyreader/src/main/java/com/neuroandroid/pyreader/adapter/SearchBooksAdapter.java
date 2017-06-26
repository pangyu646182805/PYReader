package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.SearchBooks;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.List;

import static com.neuroandroid.pyreader.utils.UIUtils.getString;

/**
 * Created by NeuroAndroid on 2017/6/26.
 */

public class SearchBooksAdapter extends BaseRvAdapter<SearchBooks.Book> {
    public SearchBooksAdapter(Context context, List<SearchBooks.Book> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, SearchBooks.Book item, int position, int viewType) {
        ImageView ivBookCover = holder.getView(R.id.iv_book_cover);
        ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + item.getCover(), R.mipmap.cover_default, ivBookCover);
        holder.setText(R.id.tv_book_title, item.getTitle())
                .setText(R.id.tv_follower, String.format(getString(R.string.search_result_lately_follower), item.getLatelyFollower()))
                .setText(R.id.tv_retention_ratio, (TextUtils.isEmpty(item.getRetentionRatio()) ? String.format(getString(R.string.search_result_retention_ratio), "0")
                        : String.format(getString(R.string.search_result_retention_ratio), item.getRetentionRatio())))
                .setText(R.id.tv_book_author, String.format(UIUtils.getString(R.string.search_result_author), item.getAuthor()));
    }
}
