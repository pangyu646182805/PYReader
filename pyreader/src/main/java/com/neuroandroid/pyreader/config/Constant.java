package com.neuroandroid.pyreader.config;

import com.neuroandroid.pyreader.utils.FileUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class Constant {
    public static final String API_BASE_URL = "http://api.zhuishushenqi.com";

    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";

    public static final String PACKAGE_NAME_PREFERENCES = "config";

    public static final String USER_CHOOSE_SEX = "user_choose_sex";

    public static final String MALE = "male";

    public static final String FEMALE = "female";

    public static final String RECOMMEND = "recommend";

    public static final String SEARCH_HISTORY = "search_history";

    public static final int MAX_SAVE_SEARCH_HISTORY_COUNT = 10;

    public static final String INTENT_BOOK_ID = "intent_book_id";

    public static String RECOMMEND_COLLECT = FileUtils.createRootPath(UIUtils.getContext()) + "/recommend";
}
