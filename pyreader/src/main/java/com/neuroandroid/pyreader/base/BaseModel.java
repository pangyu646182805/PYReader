package com.neuroandroid.pyreader.base;

import com.neuroandroid.pyreader.model.api.ApiService;
import com.neuroandroid.pyreader.net.RetrofitUtils;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseModel implements IModel {
    protected ApiService mService;

    public BaseModel(String baseUrl) {
        this(baseUrl, false);
    }

    public BaseModel(String baseUrl, boolean needCache) {
        mService = RetrofitUtils.getInstance(baseUrl, needCache).create(ApiService.class);
    }

    @Override
    public void onDestroy() {
        mService = null;
    }
}
