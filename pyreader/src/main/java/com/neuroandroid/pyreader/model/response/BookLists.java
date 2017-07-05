package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class BookLists extends BaseResponse {
    /**
     * _id : 57a83783c9b799011623ecff
     * title : 【追书盘点】男频类型文（六）体育类竞技文
     * author : 追书白小生
     * desc : 在体育竞技的赛场上！
     运动员们，因为一个坚定的信念，而不断前行，努力，付出。
     他们的目标只有一个：升级！
     当冠军，收小弟，在体育的大道上，走向人生的巅峰！

     本次就让我们来盘点一下，那些正值火热的体育类竞技文吧。
     【排名不分先后】
     * gender : male
     * collectorCount : 2713
     * cover : /agent/http://image.cmfu.com/books/3623405/3623405.jpg
     * bookCount : 20
     */
    private List<BookListsBean> bookLists;

    public List<BookListsBean> getBookLists() {
        return bookLists;
    }

    public void setBookLists(List<BookListsBean> bookLists) {
        this.bookLists = bookLists;
    }

    public class BookListsBean implements Serializable {
        @SerializedName("_id")
        private String bookId;
        private String title;
        private String author;
        private String desc;
        private String gender;
        private int collectorCount;
        private String cover;
        private int bookCount;

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
