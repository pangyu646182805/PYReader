package com.neuroandroid.pyreader.interfaces;

import android.support.annotation.NonNull;

import com.afollestad.materialcab.MaterialCab;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public interface MaterialCabCallBack {
    @NonNull
    MaterialCab openCab(final int menuRes, final MaterialCab.Callback callback);
}
