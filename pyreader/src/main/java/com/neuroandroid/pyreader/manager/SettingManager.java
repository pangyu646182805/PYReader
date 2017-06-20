package com.neuroandroid.pyreader.manager;

import android.content.Context;

import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.utils.SPUtils;

/**
 * Created by NeuroAndroid on 2017/6/20.
 */

public class SettingManager {
    /**
     * 选择性别dialog是否弹出过
     */
    public static boolean isUserChooseSex(Context context) {
        return SPUtils.contains(context, Constant.USER_CHOOSE_SEX);
    }

    /**
     * 获取用户选择的性别
     * 默认男性
     */
    public static String getChooseSex(Context context) {
        return SPUtils.getString(context, Constant.USER_CHOOSE_SEX, Constant.MALE);
    }

    /**
     * 保存用户性别
     */
    public static void saveChooseSex(Context context, String sex) {
        SPUtils.putString(context, Constant.USER_CHOOSE_SEX, sex);
    }
}
