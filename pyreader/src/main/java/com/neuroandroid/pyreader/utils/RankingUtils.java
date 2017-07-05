package com.neuroandroid.pyreader.utils;

import com.neuroandroid.pyreader.model.response.RankingList;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class RankingUtils {
    public static RankingList replaceRankingWord(RankingList rankingList) {
        List<RankingList.MaleBean> male = rankingList.getMale();
        List<RankingList.MaleBean> female = rankingList.getFemale();
        male = replaceMaleRankingWord(male);
        female = replaceMaleRankingWord(female);
        rankingList.setMale(male);
        rankingList.setFemale(female);
        return rankingList;
    }

    private static List<RankingList.MaleBean> replaceMaleRankingWord(List<RankingList.MaleBean> male) {
        for (RankingList.MaleBean maleBean : male) {
            String title = maleBean.getTitle();
            if (title.contains("最热榜")) {
                maleBean.setTitle("最热榜");
            } else if (title.contains("留存")) {
                maleBean.setTitle("留存榜");
            } else if (title.contains("完结榜")) {
                maleBean.setTitle("完结榜");
            } else if (title.contains("包月")) {
                maleBean.setTitle("包月榜");
            } else if (title.contains("潜力榜")) {
                maleBean.setTitle("潜力榜");
            } else if (title.contains("圣诞")) {
                maleBean.setTitle("圣诞榜");
            } else if (title.contains("百度")) {
                maleBean.setTitle("百度榜");
            } else if (title.contains("掌阅")) {
                maleBean.setTitle("掌阅榜");
            } else if (title.contains("书旗")) {
                maleBean.setTitle("书旗榜");
            } else if (title.contains("17K")) {
                maleBean.setTitle("17K");
            } else if (title.contains("起点")) {
                maleBean.setTitle("起点榜");
            } else if (title.contains("纵横")) {
                maleBean.setTitle("纵横榜");
            } else if (title.contains("和阅读")) {
                maleBean.setTitle("和阅读");
            } else if (title.contains("逐浪")) {
                maleBean.setTitle("逐浪榜");
            } else if (title.contains("潇湘")) {
                maleBean.setTitle("潇湘榜");
            }
        }
        return male;
    }
}
