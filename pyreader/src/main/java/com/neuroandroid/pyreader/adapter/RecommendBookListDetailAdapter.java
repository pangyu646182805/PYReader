package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class RecommendBookListDetailAdapter extends BaseRvAdapter<BookListDetail.BookListBean.BooksBean> {
    public RecommendBookListDetailAdapter(Context context, List<BookListDetail.BookListBean.BooksBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, BookListDetail.BookListBean.BooksBean item, int position, int viewType) {
        int mainColor = ThemeUtils.getMainColor();
        int subColor = ThemeUtils.getSubColor();
        int threeLevelColor = ThemeUtils.getThreeLevelColor();

        holder.getView(R.id.view_split).setBackgroundColor(ThemeUtils.getSplitColor());

        BookListDetail.BookListBean.BooksBean.BookBean book = item.getBook();
        ImageView ivBookCover = holder.getView(R.id.iv_book_cover);
        ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + book.getCover(), R.mipmap.cover_default, ivBookCover);

        holder.setText(R.id.tv_title, book.getTitle())
                .setTextColor(R.id.tv_title, mainColor)
                .setText(R.id.tv_author, book.getAuthor())
                .setTextColor(R.id.tv_author, subColor)
                .setText(R.id.tv_recommend_count, String.format(UIUtils.getString(
                        R.string.subject_book_list_detail_book_lately_follower), book.getLatelyFollower()))
                .setTextColor(R.id.tv_recommend_count, threeLevelColor)
                .setText(R.id.tv_word_count, String.format(
                        UIUtils.getString(R.string.subject_book_list_detail_book_word_count), book.getWordCount() / 10000))
                .setTextColor(R.id.tv_word_count, threeLevelColor)
                .setText(R.id.tv_book_detail_desc, book.getLongIntro())
                .setTextColor(R.id.tv_book_detail_desc, subColor);
    }
}
