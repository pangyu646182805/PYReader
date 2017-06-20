package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.adapter.base.ISelect;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/20.
 */

public class Recommend extends BaseResponse {
    private List<BooksBean> books;

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean implements Serializable, ISelect {
        /**
         * _id : 54dc25f9b8817a1d416be096
         * title : 走进修仙
         * author : 吾道长不孤
         * shortIntro : 《天演图录》为何与进化论有关？飘渺无定云剑和概率云又有何关系？修真人士如何建造修真原子弹？万年前的绝世强者、今天的戒指老爷爷为何被评价为“误人子弟”“没用”？让...
         * cover : /agent/http://wap.cmread.com/r/cover_file/7815/410947815/20151012132440/cover180240.jpg
         * hasCp : true
         * latelyFollower : 71566
         * retentionRatio : 17.52
         * updated : 2017-06-19T21:42:37.496Z
         * chaptersCount : 1604
         * lastChapter : 第四百三十五章 死斗【其一】
         */
        @SerializedName("_id")
        private String bookId;
        private String title;
        private String author;
        private String shortIntro;
        private String cover;
        private boolean hasCp;
        private int latelyFollower;
        private double retentionRatio;
        private String updated;
        private int chaptersCount;
        private String lastChapter;
        private boolean isFromSD;
        private boolean isSelected;

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getShortIntro() {
            return shortIntro;
        }

        public void setShortIntro(String shortIntro) {
            this.shortIntro = shortIntro;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isHasCp() {
            return hasCp;
        }

        public void setHasCp(boolean hasCp) {
            this.hasCp = hasCp;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public double getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(double retentionRatio) {
            this.retentionRatio = retentionRatio;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public int getChaptersCount() {
            return chaptersCount;
        }

        public void setChaptersCount(int chaptersCount) {
            this.chaptersCount = chaptersCount;
        }

        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }

        @Override
        public boolean isSelected() {
            return isSelected;
        }

        @Override
        public void setSelected(boolean selected) {
            this.isSelected = selected;
        }
    }
}
