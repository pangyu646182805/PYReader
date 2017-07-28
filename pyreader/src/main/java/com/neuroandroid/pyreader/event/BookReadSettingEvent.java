package com.neuroandroid.pyreader.event;

import com.neuroandroid.pyreader.bean.BookReadThemeBean;

/**
 * Created by NeuroAndroid on 2017/7/28.
 */

public class BookReadSettingEvent extends BaseEvent {
    private BookReadThemeBean bookReadThemeBean;

    public BookReadSettingEvent() {
        setEventFlag(EVENT_BOOK_READ_SETTING);
    }

    public BookReadThemeBean getBookReadThemeBean() {
        return bookReadThemeBean;
    }

    public BookReadSettingEvent setBookReadThemeBean(BookReadThemeBean bookReadThemeBean) {
        this.bookReadThemeBean = bookReadThemeBean;
        return this;
    }
}
