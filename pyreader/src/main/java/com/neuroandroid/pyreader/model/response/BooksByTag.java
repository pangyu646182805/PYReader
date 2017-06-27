package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BooksByTag extends BaseResponse {
    private List<BooksBean> books;

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean {
        /**
         * _id : 557e0b2bdcfc794e1a1cd8b2
         * title : 雪鹰领主
         * author : 我吃西红柿
         * shortIntro : 深渊恶魔降临…… 异世界来客潜伏人间…… 神灵们在窥伺这座世界…… 然而，这是夏族统治的世界！夏族的强者们征战四方，巡守天地海洋，灭杀一切威胁！ 这群强者有一个...
         * cover : /cover/147521131159596
         * cat : 玄幻
         * majorCate : 玄幻
         * minorCate : 异界大陆
         * latelyFollower : 181924
         * retentionRatio : 75.35
         * lastChapter : 第1349章 归来
         * tags : ["玄幻","异界大陆","勇猛"]
         */
        @SerializedName("_id")
        private String bookId;
        private String title;
        private String author;
        private String shortIntro;
        private String cover;
        private String cat;
        private String majorCate;
        private String minorCate;
        private int latelyFollower;
        private double retentionRatio;
        private String lastChapter;
        private List<String> tags;

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

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public String getMajorCate() {
            return majorCate;
        }

        public void setMajorCate(String majorCate) {
            this.majorCate = majorCate;
        }

        public String getMinorCate() {
            return minorCate;
        }

        public void setMinorCate(String minorCate) {
            this.minorCate = minorCate;
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

        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
