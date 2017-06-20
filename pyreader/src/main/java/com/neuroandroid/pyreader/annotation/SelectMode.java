package com.neuroandroid.pyreader.annotation;

import android.support.annotation.IntDef;

import com.neuroandroid.pyreader.adapter.base.ISelect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Created by NeuroAndroid on 2017/3/9.
 */

@Target(PARAMETER)
@IntDef(flag = true, value = {
        ISelect.SINGLE_MODE,
        ISelect.MULTIPLE_MODE
})
@Retention(RetentionPolicy.SOURCE)
public @interface SelectMode {
}
