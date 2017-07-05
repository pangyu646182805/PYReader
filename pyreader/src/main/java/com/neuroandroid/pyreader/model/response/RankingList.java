package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.adapter.base.ISelect;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class RankingList extends BaseResponse {
    /**
     * female : [{"_id":"54d43437d47d13ff21cad58b","title":"追书最热榜 Top100",
     * "cover":"/ranking-cover/142319314350435","collapse":false,
     * "monthRank":"564d853484665f97662d0810","totalRank":"564d85b6dd2bd1ec660ea8e2"}]
     * ok : true
     */
    private List<MaleBean> female;
    /**
     * _id : 54d42d92321052167dfb75e3
     * title : 追书最热榜 Top100
     * cover : /ranking-cover/142319144267827
     * collapse : false
     * monthRank : 564d820bc319238a644fb408
     * totalRank : 564d8494fe996c25652644d2
     */
    private List<MaleBean> male;

    public List<MaleBean> getFemale() {
        return female;
    }

    public void setFemale(List<MaleBean> female) {
        this.female = female;
    }

    public List<MaleBean> getMale() {
        return male;
    }

    public void setMale(List<MaleBean> male) {
        this.male = male;
    }

    public static class MaleBean implements ISelect {
        @SerializedName("_id")
        private String rankingId;
        private String title;
        private String cover;
        private boolean collapse;
        private String monthRank;
        private String totalRank;

        public MaleBean() {
        }

        public MaleBean(String title) {
            this.title = title;
        }

        private boolean isSelected;

        @Override
        public boolean isSelected() {
            return isSelected;
        }

        @Override
        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getRankingId() {
            return rankingId;
        }

        public void setRankingId(String rankingId) {
            this.rankingId = rankingId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isCollapse() {
            return collapse;
        }

        public void setCollapse(boolean collapse) {
            this.collapse = collapse;
        }

        public String getMonthRank() {
            return monthRank;
        }

        public void setMonthRank(String monthRank) {
            this.monthRank = monthRank;
        }

        public String getTotalRank() {
            return totalRank;
        }

        public void setTotalRank(String totalRank) {
            this.totalRank = totalRank;
        }
    }
}
