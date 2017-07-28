package com.neuroandroid.pyreader.utils;

import android.content.Context;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.bean.BookReadThemeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/27.
 */

public class BookReadSettingUtils {
    public static final int FOLLOW_SYSTEM = -1;
    private static List<BookReadThemeBean> sBookReadThemeBeanList = new ArrayList<>();

    static {
        sBookReadThemeBeanList.clear();
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.white), UIUtils.getColor(R.color.black), "白纸黑字"));
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.eye_protection_green), UIUtils.getColor(R.color.colorGray333), "护眼绿"));
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.girl_dream), UIUtils.getColor(R.color.colorGray333), "少女梦"));
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.cherry_powder), UIUtils.getColor(R.color.colorGray333), "樱花粉"));
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.scholarly), UIUtils.getColor(R.color.colorGray333), "书香紫"));
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.asahi_fade_out), UIUtils.getColor(R.color.colorGray333), "朝霞渐出"));
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.star_blue), UIUtils.getColor(R.color.backgroundPanel), "星空蓝"));
        sBookReadThemeBeanList.add(new BookReadThemeBean(
                UIUtils.getColor(R.color.black), UIUtils.getColor(R.color.white), "黑纸白字"));
    }

    public static List<BookReadThemeBean> getBookReadThemeBeanList() {
        return sBookReadThemeBeanList;
    }

    /**
     * 保存阅读界面主题
     * 以主题名字,背景颜色,字体颜色保存
     *
     * @param bookReadThemeBean 选择的主题
     */
    public static void saveBookReadTheme(Context context, BookReadThemeBean bookReadThemeBean) {
        String saveData = bookReadThemeBean.getBookReadThemeName() + ","
                + bookReadThemeBean.getBookReadInterfaceBackgroundColor() + ","
                + bookReadThemeBean.getBookReadFontColor();
        SPUtils.putString(context, getBookReadThemeKey(), saveData);
    }

    private static String getBookReadThemeKey() {
        return "book_read_theme";
    }

    /**
     * 获取阅读界面的主题
     */
    public static BookReadThemeBean getBookReadTheme(Context context) {
        String saveData = SPUtils.getString(context, getBookReadThemeKey(), null);
        BookReadThemeBean bookReadThemeBean;
        if (UIUtils.isEmpty(saveData)) {
            bookReadThemeBean = getBookReadThemeBeanList().get(0);
        } else {
            bookReadThemeBean = new BookReadThemeBean();
            String[] split = saveData.split(",");
            bookReadThemeBean.setBookReadThemeName(split[0])
                    .setBookReadInterfaceBackgroundColor(Integer.parseInt(split[1]))
                    .setBookReadFontColor(Integer.parseInt(split[2]));
        }
        return bookReadThemeBean;
    }

    private static String getScreenBrightnessKey() {
        return "ScreenBrightness";
    }

    /**
     * 保存阅读时候的屏幕亮度
     *
     * @param screenBrightness == -1 ? 跟随系统 : 实际的值
     */
    public static void saveScreenBrightness(Context context, int screenBrightness) {
        SPUtils.putInt(context, getScreenBrightnessKey(), screenBrightness);
    }

    /**
     * 获取阅读时候的屏幕亮度
     */
    public static int getScreenBrightness(Context context) {
        return SPUtils.getInt(context, getScreenBrightnessKey(), FOLLOW_SYSTEM);
    }
}
