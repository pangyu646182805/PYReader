package com.neuroandroid.pyreader.manager;

import android.text.TextUtils;

import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.event.RecommendEvent;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.utils.CacheUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    public void saveRecommend(List<Recommend.BooksBean> recommendList) {
        CacheUtils.get(new File(Constant.RECOMMEND_COLLECT)).put(Constant.RECOMMEND, (Serializable) recommendList);
    }

    /**
     * 根据书籍id删除推荐书籍
     */
    public void removeRecommend(String bookId) {
        List<Recommend.BooksBean> recommendList = getRecommend();
        if (recommendList == null) return;
        for (Recommend.BooksBean book : recommendList) {
            if (TextUtils.equals(bookId, book.getBookId())) {
                recommendList.remove(book);
                saveRecommend(recommendList);
                break;
            }
        }
        EventBus.getDefault().post(new RecommendEvent());
    }

    /**
     * bookId的书籍是否在推荐列表里面
     */
    public boolean bookInRecommend(String bookId) {
        List<Recommend.BooksBean> recommendList = getRecommend();
        if (recommendList == null || recommendList.isEmpty()) return false;
        for (Recommend.BooksBean book : recommendList) {
            if (TextUtils.equals(bookId, book.getBookId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将书籍添加进入书架
     * 如果book已经在书架中了则不用重复添加
     */
    public boolean addRecommend(Recommend.BooksBean book) {
        if (bookInRecommend(book.getBookId())) return false;
        List<Recommend.BooksBean> recommendList = getRecommend();
        if (recommendList == null) recommendList = new ArrayList<>();
        recommendList.add(book);
        saveRecommend(recommendList);
        EventBus.getDefault().post(new RecommendEvent());
        return true;
    }
}
