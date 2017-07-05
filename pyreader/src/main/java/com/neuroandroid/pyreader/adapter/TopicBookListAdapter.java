package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicBookListAdapter extends BaseRvAdapter<BookList.BookListsBean> {
    public TopicBookListAdapter(Context context, List<BookList.BookListsBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, BookList.BookListsBean item, int position, int viewType) {
        ImageView ivBookCover = holder.getView(R.id.iv_book_cover);
        ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + item.getCover(), R.mipmap.cover_default, ivBookCover);
        holder.setText(R.id.tv_book_title, item.getTitle())
                .setText(R.id.tv_book_author, UIUtils.isEmpty(item.getAuthor()) ? "未知" : item.getAuthor())
                .setText(R.id.tv_book_intro, item.getDesc())
                .setText(R.id.tv_follower, String.format(
                        UIUtils.getString(R.string.topic_book_count), item.getBookCount()))
                .setText(R.id.tv_word_count, String.format(
                        UIUtils.getString(R.string.topic_book_collection), item.getCollectorCount()));
    }
}
