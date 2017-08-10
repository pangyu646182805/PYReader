package com.neuroandroid.pyreader.manager;

import android.content.Context;

import com.neuroandroid.pyreader.bean.SearchHistoryBean;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.provider.PYReaderStore;
import com.neuroandroid.pyreader.utils.CacheUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.SPUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/26.
 */

public class CacheManager {
    public static void saveSearchHistory(Context context, String history) {
        String searchHistory = getSearchHistoryStr(context);
        if (!UIUtils.isEmpty(searchHistory)) {
            String subSearchHistory = searchHistory.substring(0, searchHistory.length() - 1);
            List<String> searchHistoryList = new ArrayList<>(Arrays.asList(subSearchHistory.split("-")));
            if (searchHistoryList.size() > 0) {
                //移除之前重复添加的元素
                for (int i = 0; i < searchHistoryList.size(); i++) {
                    if (history.equals(searchHistoryList.get(i))) {
                        searchHistoryList.remove(i);
                        break;
                    }
                }
                // 将新输入的文字添加集合的第0位也就是最前面
                searchHistoryList.add(0, history);
                if (searchHistoryList.size() > Constant.MAX_SAVE_SEARCH_HISTORY_COUNT) {
                    // 最多保存10条搜索记录 删除最早搜索的那一项
                    searchHistoryList.remove(searchHistoryList.size() - 1);
                }
                searchHistory = "";
                for (int i = 0; i < searchHistoryList.size(); i++) {
                    searchHistory += searchHistoryList.get(i) + "-";
                }
            }
        } else {
            searchHistory = history + "-";
        }
        SPUtils.putString(context, Constant.SEARCH_HISTORY, searchHistory);
    }

    /**
     * 获取搜索历史的集合
     */
    public static SearchHistoryBean getSearchHistoryList(Context context) {
        String searchHistoryStr = getSearchHistoryStr(context);
        if (!UIUtils.isEmpty(searchHistoryStr)) {
            searchHistoryStr = searchHistoryStr.substring(0, searchHistoryStr.length() - 1);
            SearchHistoryBean historyBean = new SearchHistoryBean();
            historyBean.setSearchHistoryList(new ArrayList<>(Arrays.asList(searchHistoryStr.split("-"))));
            return historyBean;
        } else {
            return null;
        }
    }

    /**
     * 获取搜索历史的字符串
     * 大主宰-隐身侍卫-一世富贵-
     */
    public static String getSearchHistoryStr(Context context) {
        return SPUtils.getString(context, Constant.SEARCH_HISTORY, null);
    }

    /**
     * 清除历史记录
     */
    public static boolean clearSearchHistory(Context context) {
        if (!UIUtils.isEmpty(getSearchHistoryStr(context))) {
            SPUtils.putString(context, Constant.SEARCH_HISTORY, null);
            return true;
        }
        return false;
    }

    private static String getChapterListKey(String bookId) {
        return bookId + "_chapter_list";
    }

    /**
     * 保存章节列表
     */
    public static void saveChapterList(Context context, String bookId, List<BookMixAToc.MixToc.Chapters> list) {
        CacheUtils.get(context).put(getChapterListKey(bookId), (ArrayList) list);
    }

    public static List<BookMixAToc.MixToc.Chapters> getChapterList(Context context, String bookId) {
        Object object = CacheUtils.get(context).getAsObject(getChapterListKey(bookId));
        if (object != null) {
            try {
                List<BookMixAToc.MixToc.Chapters> list = (List<BookMixAToc.MixToc.Chapters>) object;
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据书籍ID删除对应的章节列表
     */
    public static boolean deleteChapterListByBookId(Context context, String bookId) {
        return CacheUtils.get(context).remove(getChapterListKey(bookId));
    }

    private static String getReadPositionKey(String bookId) {
        return bookId + "_read_position";
    }

    private static String getReadPositionValue(int currentChapter, int currentPage) {
        return currentChapter + "-" + currentPage;
    }

    /**
     * 存储阅读位置
     *
     * @param currentChapter 当前阅读到了第几章
     * @param currentPage    当前阅读到了第几章的第几页
     */
    public static void saveReadPosition(Context context, String bookId, int currentChapter, int currentPage) {
        SPUtils.putString(context, getReadPositionKey(bookId), getReadPositionValue(currentChapter, currentPage));
    }

    /**
     * 返回阅读位置
     */
    public static int[] getReadPosition(Context context, String bookId) {
        int[] readPosition = new int[2];
        String[] split = SPUtils.getString(context, getReadPositionKey(bookId), "0-1").split("-");
        readPosition[0] = Integer.parseInt(split[0]);
        readPosition[1] = Integer.parseInt(split[1]);
        return readPosition;
    }

    /**
     * 根据书籍ID删除阅读位置
     */
    public static boolean deleteReadPositionByBookId(Context context, String bookId) {
        return SPUtils.putString(context, getReadPositionKey(bookId), "0-1");
    }

    /**
     * RecommendFragment 批量管理
     *
     * @param selectedBooks 选择的书籍
     * @param delLocalCache 是否删除本地缓存
     */
    public static void batchManage(Context context, List<Recommend.BooksBean> selectedBooks, boolean delLocalCache) {
        for (Recommend.BooksBean booksBean : selectedBooks) {
            String bookId = booksBean.getBookId();
            // 删除对应书籍的阅读位置
            deleteReadPositionByBookId(context, bookId);
            // 删除对应书籍的章节列表
            boolean deleteChapterListByBookId = deleteChapterListByBookId(context, bookId);
            L.e("删除[" + booksBean.getTitle() + "]的章节列表成功 : " + deleteChapterListByBookId);
            RecommendManager.getInstance().removeRecommend(bookId);
            if (delLocalCache) {
                // 删除书籍对应的章节内容
                int delete = PYReaderStore.getInstance(context).deleteChapterByBookId(bookId);
                L.e("删除[" + booksBean.getTitle() + "]的章节内容共" + delete + "章");
            }
        }
    }
}
