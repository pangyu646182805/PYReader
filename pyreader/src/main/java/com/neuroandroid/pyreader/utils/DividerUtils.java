package com.neuroandroid.pyreader.utils;

import android.content.Context;

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
        return new HorizontalDividerItemDecoration.Builder(context)
                .sizeResId(R.dimen.x2).colorResId(R.color.split).build();
    }
}
