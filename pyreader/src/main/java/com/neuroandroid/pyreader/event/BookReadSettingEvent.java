package com.neuroandroid.pyreader.event;

import com.neuroandroid.pyreader.bean.BookReadThemeBean;

/**
 * Created by NeuroAndroid on 2017/7/28.
 */

public class BookReadSettingEvent extends BaseEvent {
    private BookReadThemeBean bookReadThemeBean;

    private boolean fromColorPickerDialog;

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

    public boolean isFromColorPickerDialog() {
        return fromColorPickerDialog;
    }

    public BookReadSettingEvent setFromColorPickerDialog(boolean fromColorPickerDialog) {
        this.fromColorPickerDialog = fromColorPickerDialog;
        return this;
    }
}
