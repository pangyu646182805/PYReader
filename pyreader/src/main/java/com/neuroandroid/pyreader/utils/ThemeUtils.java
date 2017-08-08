package com.neuroandroid.pyreader.utils;

import android.support.annotation.ColorRes;

import com.neuroandroid.pyreader.R;

import org.polaric.colorful.Colorful;

/**
 * Created by NeuroAndroid on 2017/8/8.
 */

public class ThemeUtils {
    private static boolean isDarkMode() {
        return Colorful.getThemeDelegate().isDark();
    }

    public static int getSplitColor() {
        return UIUtils.getColor(getSplitColorRes());
    }

    public static int getSplitColorRes() {
        return isDarkMode() ? R.color.white_1 : R.color.split;
    }

    public static int getMainColor() {
        return getMainColor(R.color.white_9, R.color.colorGray333);
    }

    public static int getBackgroundColor() {
        return UIUtils.getColor(getBackgroundColorRes());
    }

    public static int getBackgroundColorRes() {
        return isDarkMode() ? R.color.backgroundColorDark : R.color.backgroundColor;
    }

    public static int getSubColor() {
        return getMainColor(R.color.white_6, R.color.colorGray666);
    }

    public static int getThreeLevelColor() {
        return getMainColor(R.color.white_3, R.color.colorGray999);
    }

    public static int getMainColor(@ColorRes int darkModeColor, @ColorRes int notDarkModeColor) {
        if (isDarkMode()) {
            return UIUtils.getColor(darkModeColor);
        } else {
            return UIUtils.getColor(notDarkModeColor);
        }
    }
}
