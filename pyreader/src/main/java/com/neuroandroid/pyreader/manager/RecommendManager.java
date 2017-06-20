package com.neuroandroid.pyreader.manager;

import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.utils.CacheUtils;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import static com.neuroandroid.pyreader.utils.CacheUtils.get;

/**
 * Created by NeuroAndroid on 2017/6/20.
 */

public class RecommendManager {
    private volatile static RecommendManager sManager;

    private RecommendManager() {

    }

    public static RecommendManager getInstance() {
        if (sManager == null) {
            synchronized (RecommendManager.class) {
                if (sManager == null) {
                    sManager = new RecommendManager();
                }
            }
        }
        return sManager;
    }

    /**
     * 获取缓存的推荐列表
     */
    public List<Recommend.BooksBean> getRecommend() {
        List<Recommend.BooksBean> recommend = (List<Recommend.BooksBean>) CacheUtils.get(new File(Constant.RECOMMEND_COLLECT)).getAsObject(Constant.RECOMMEND);
        return recommend == null ? null : recommend;
    }

    /**
     * 保存推荐列表
     */
    public void saveRecommend(List<Recommend.BooksBean> recommend) {
        get(new File(Constant.RECOMMEND_COLLECT)).put(Constant.RECOMMEND, (Serializable) recommend);
    }
}
