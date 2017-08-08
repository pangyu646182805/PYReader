package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BooksByTag;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BooksByTagAdapter extends BaseRvAdapter<BooksByTag.BooksBean> {
    public BooksByTagAdapter(Context context, List<BooksByTag.BooksBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, BooksByTag.BooksBean item, int position, int viewType) {
        String tags = "";
        int tagSize = item.getTags().size();
        for (int i = 0; i < tagSize; i++) {
            String tag = item.getTags().get(i);
            if (UIUtils.isEmpty(tag)) continue;
            if (i != tagSize - 1) {
                tags += tag + " | ";
            } else {
                tags += tag;
            }
        }

        ImageView ivCover = holder.getView(R.id.iv_book_cover);
        ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + item.getCover(), R.mipmap.cover_default, ivCover);
        holder.setText(R.id.tv_book_title, item.getTitle())
                .setTextColor(R.id.tv_book_title, ThemeUtils.getMainColor())
                .setText(R.id.tv_book_intro, item.getShortIntro())
                .setTextColor(R.id.tv_book_intro, ThemeUtils.getSubColor())
                .setText(R.id.tv_book_tags, tags.trim())
                .setTextColor(R.id.tv_book_tags, ThemeUtils.getThreeLevelColor());
    }
}
