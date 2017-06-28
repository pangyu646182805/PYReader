package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class BookList extends BaseResponse {
    private List<BookListsBean> bookLists;

    public List<BookListsBean> getBookLists() {
        return bookLists;
    }

    public void setBookLists(List<BookListsBean> bookLists) {
        this.bookLists = bookLists;
    }

    public static class BookListsBean implements Serializable {
        @SerializedName("_id")
        private String bookListId;
        private String title;
        private String author;
        private String desc;
        private String gender;
        private int collectorCount;
        private String cover;
        private int bookCount;

        public static BookListsBean generateBookListsBean(RecommendBookList.BooklistsBean item) {
            BookList.BookListsBean bookListsBean = new BookList.BookListsBean();
            bookListsBean.setBookListId(item.getId());
            bookListsBean.setCover(item.getCover());
            bookListsBean.setTitle(item.getTitle());
            bookListsBean.setAuthor(item.getAuthor());
            bookListsBean.setBookCount(item.getBookCount());
            bookListsBean.setCollectorCount(item.getCollectorCount());
            bookListsBean.setDesc(item.getDesc());
            return bookListsBean;
        }

        public String getBookListId() {
            return bookListId;
        }

        public void setBookListId(String bookListId) {
            this.bookListId = bookListId;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getCollectorCount() {
            return collectorCount;
        }

        public void setCollectorCount(int collectorCount) {
            this.collectorCount = collectorCount;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }
    }
}
