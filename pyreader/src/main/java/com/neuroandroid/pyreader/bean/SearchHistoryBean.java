package com.neuroandroid.pyreader.bean;

import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/26.
 */

public class SearchHistoryBean extends BaseResponse {
    private List<String> searchHistoryList;

    public List<String> getSearchHistoryList() {
        return searchHistoryList;
    }

    public void setSearchHistoryList(List<String> searchHistoryList) {
        this.searchHistoryList = searchHistoryList;
    }
}
