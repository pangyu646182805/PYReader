package com.neuroandroid.pyreader.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;

import com.neuroandroid.pyreader.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import org.polaric.colorful.Colorful;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class DividerUtils {
    /**
     * 默认RecyclerView分割线 (横向)
     * 2px - #eeeeee
     */
    public static HorizontalDividerItemDecoration defaultHorizontalDivider(Context context) {
        return generateHorizontalDivider(context, R.dimen.x2,
                Colorful.getThemeDelegate().isDark() ? R.color.white_1 : R.color.split);
    }

    public static HorizontalDividerItemDecoration generateHorizontalDivider(
            Context context, @DimenRes int sizeId, @ColorRes int colorId) {
        return new HorizontalDividerItemDecoration.Builder(context)
                .sizeResId(sizeId).colorResId(colorId).build();
    }

    /**
     * 默认RecyclerView分割线 (纵向)
     * 2px - #eeeeee
     */
    public static VerticalDividerItemDecoration defaultVerticalDivider(Context context) {
        return generateVerticalDivider(context, R.dimen.x2,
                Colorful.getThemeDelegate().isDark() ? R.color.white_1 : R.color.split);
    }

    public static VerticalDividerItemDecoration generateVerticalDivider(
            Context context, @DimenRes int sizeId, @ColorRes int colorId) {
        return new VerticalDividerItemDecoration.Builder(context)
                .sizeResId(sizeId).colorResId(colorId).build();
    }
}
