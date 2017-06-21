package com.neuroandroid.pyreader.model.response;

import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class RecommendBookList extends BaseResponse {
    private List<BooklistsBean> booklists;

    public List<BooklistsBean> getBooklists() {
        return booklists;
    }

    public void setBooklists(List<BooklistsBean> booklists) {
        this.booklists = booklists;
    }

    public static class BooklistsBean {
        /**
         * id : 57331505025ffaa06cb28852
         * title : ★星光书局 ★(04－20更
         * author : 人闲
         * desc : ☆准星（不好看），★一星，★★二星，★★★三星，★★★★，★★★★★五星 （持续更新中……）……………本期歌单:周慧敏《自作多情》、赵雷《已是两条路上的人》、张韶涵《寓言》、张惠妹《我最亲爱的》、张惠妹《哭砂》、张惠妹《剪爱》、张碧晨《渡红尘》、Amy Winehouse《You know I'm no good》、邓紫棋《偶尔》、邓紫棋《喜欢你》、叶倩文《曾经心疼》、叶倩文《祝福》
         * bookCount : 464
         * collectorCount : 89433
         * cover : /agent/http://image.cmfu.com/books/1659706/1659706.jpg
         */
        private String id;
        private String title;
        private String author;
        private String desc;
        private int bookCount;
        private int collectorCount;
        private String cover;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
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
    }
}
