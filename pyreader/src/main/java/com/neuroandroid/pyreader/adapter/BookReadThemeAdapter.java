package com.neuroandroid.pyreader.adapter;

import android.content.Context;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.bean.BookReadThemeBean;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

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
        boolean darkMode = ThemeUtils.isDarkMode();
        int blackColor = UIUtils.getColor(R.color.black);
        holder.setText(R.id.tv_book_read_theme, item.getBookReadThemeName())
                .setTextColor(R.id.tv_book_read_theme, darkMode ?
                        (item.getBookReadInterfaceBackgroundColor() == blackColor) ?
                                item.getBookReadFontColor() : item.getBookReadInterfaceBackgroundColor()
                        : item.getBookReadFontColor())
                .setBackgroundColor(R.id.tv_book_read_theme, darkMode ?
                        blackColor : item.getBookReadInterfaceBackgroundColor());
    }
}
