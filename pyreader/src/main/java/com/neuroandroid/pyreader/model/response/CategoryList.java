package com.neuroandroid.pyreader.model.response;

import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class CategoryList extends BaseResponse {
    private List<MaleBean> male;
    /**
     * name : 古代言情
     * bookCount : 125103
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
        private String name;
        private int bookCount = -1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }
    }
}
