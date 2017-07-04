package com.neuroandroid.pyreader.model.response;

import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/4.
 */

public class CategoryListLv2 extends BaseResponse {
    /**
     * major : 玄幻
     * mins : ["东方玄幻","异界大陆","异界争霸","远古神话"]
     */
    private List<MaleBean> male;
    /**
     * major : 古代言情
     * mins : ["穿越时空","古代历史","古典架空","宫闱宅斗","经商种田"]
     */
    private List<MaleBean> female;

    public List<MaleBean> getMale() {
        return male;
    }

    public void setMale(List<MaleBean> male) {
        this.male = male;
    }

    public List<MaleBean> getFemale() {
        return female;
    }

    public void setFemale(List<MaleBean> female) {
        this.female = female;
    }

    public static class MaleBean {
        private String major;
        private List<String> mins;

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public List<String> getMins() {
            return mins;
        }

        public void setMins(List<String> mins) {
            this.mins = mins;
        }
    }
}
