package com.neuroandroid.pyreader.adapter;

import android.content.Context;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.bean.BookReadThemeBean;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/28.
 */

public class BookReadThemeAdapter extends BaseRvAdapter<BookReadThemeBean> {
    public BookReadThemeAdapter(Context context, List<BookReadThemeBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, BookReadThemeBean item, int position, int viewType) {
        holder.setText(R.id.tv_book_read_theme, item.getBookReadThemeName())
                .setTextColor(R.id.tv_book_read_theme, item.getBookReadFontColor())
                .setBackgroundColor(R.id.tv_book_read_theme, item.getBookReadInterfaceBackgroundColor());
    }
}
