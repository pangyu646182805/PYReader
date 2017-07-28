package com.neuroandroid.pyreader.bean;

/**
 * Created by NeuroAndroid on 2017/7/27.
 */

public class BookReadThemeBean {
    private int bookReadInterfaceBackgroundColor;  // 阅读界面的背景颜色
    private int bookReadFontColor;  // 阅读界面的字体颜色
    private String bookReadThemeName;  // 主题名字

    public BookReadThemeBean() {
    }

    public BookReadThemeBean(int bookReadInterfaceBackgroundColor, int bookReadFontColor, String bookReadThemeName) {
        this.bookReadInterfaceBackgroundColor = bookReadInterfaceBackgroundColor;
        this.bookReadFontColor = bookReadFontColor;
        this.bookReadThemeName = bookReadThemeName;
    }

    public int getBookReadInterfaceBackgroundColor() {
        return bookReadInterfaceBackgroundColor;
    }

    public BookReadThemeBean setBookReadInterfaceBackgroundColor(int bookReadInterfaceBackgroundColor) {
        this.bookReadInterfaceBackgroundColor = bookReadInterfaceBackgroundColor;
        return this;
    }

    public int getBookReadFontColor() {
        return bookReadFontColor;
    }

    public BookReadThemeBean setBookReadFontColor(int bookReadFontColor) {
        this.bookReadFontColor = bookReadFontColor;
        return this;
    }

    public String getBookReadThemeName() {
        return bookReadThemeName;
    }

    public BookReadThemeBean setBookReadThemeName(String bookReadThemeName) {
        this.bookReadThemeName = bookReadThemeName;
        return this;
    }
}
