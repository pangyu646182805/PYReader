package com.neuroandroid.pyreader.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;

import com.neuroandroid.pyreader.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class DividerUtils {
    /**
     * 默认RecyclerView分割线
     * 2px - #eeeeee
     */
    public static HorizontalDividerItemDecoration defaultHorizontalDivider(Context context) {
        return generateHorizontalDivider(context, R.dimen.x2, R.color.split);
    }

    public static HorizontalDividerItemDecoration generateHorizontalDivider(
            Context context, @DimenRes int sizeId, @ColorRes int colorId) {
        return new HorizontalDividerItemDecoration.Builder(context)
                .sizeResId(sizeId).colorResId(colorId).build();
    }
}
