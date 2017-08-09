package com.neuroandroid.pyreader.utils;

import android.support.annotation.ColorRes;

import com.neuroandroid.pyreader.R;

import org.polaric.colorful.Colorful;

/**
 * Created by NeuroAndroid on 2017/8/8.
 */

public class ThemeUtils {
    /**
     * 是否是暗色主题
     */
    public static boolean isDarkMode() {
        return Colorful.getThemeDelegate().isDark();
    }

    /**
     * 获取亮色/暗色主题下分割线颜色
     */
    public static int getSplitColor() {
        return UIUtils.getColor(getSplitColorRes());
    }

    /**
     * 获取亮色/暗色主题下分割线颜色资源
     */
    public static int getSplitColorRes() {
        return isDarkMode() ? R.color.white_1 : R.color.split;
    }

    /**
     * 获取亮色/暗色主题下的主颜色
     */
    public static int getMainColor() {
        return getThemeColor(R.color.white_9, R.color.colorGray333);
    }

    /**
     * 获取亮色/暗色主题下的背景色
     */
    public static int getBackgroundColor() {
        return UIUtils.getColor(getBackgroundColorRes());
    }

    /**
     * 获取亮色/暗色主题下的背景色资源
     */
    public static int getBackgroundColorRes() {
        return isDarkMode() ? R.color.backgroundColorDark : R.color.backgroundColor;
    }

    /**
     * 获取亮色/暗色主题下的副颜色
     */
    public static int getSubColor() {
        return getThemeColor(R.color.white_6, R.color.colorGray666);
    }

    /**
     * 获取亮色/暗色主题下的第三颜色
     */
    public static int getThreeLevelColor() {
        return getThemeColor(R.color.white_3, R.color.colorGray999);
    }

    /**
     * 获取亮色/暗色主题下的颜色
     *
     * @param darkModeColor    暗色主题的颜色资源
     * @param notDarkModeColor 亮色主题的颜色资源
     * @return
     */
    public static int getThemeColor(@ColorRes int darkModeColor, @ColorRes int notDarkModeColor) {
        if (isDarkMode()) {
            return UIUtils.getColor(darkModeColor);
        } else {
            return UIUtils.getColor(notDarkModeColor);
        }
    }
}
