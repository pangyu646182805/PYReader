package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.adapter.base.ISelect;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/6.
 */

public class BookMixAToc extends BaseResponse {
    /**
     * _id:577e528e2160421a02d7380d
     * name:优质书源
     * link:http://vip.zhuishushenqi.com/toc/577e528e2160421a02d7380d
     */
    private MixToc mixToc;

    public MixToc getMixToc() {
        return mixToc;
    }

    public void setMixToc(MixToc mixToc) {
        this.mixToc = mixToc;
    }

    public static class MixToc implements Serializable {
        @SerializedName("_id")
        private String mixTocId;
        private String book;
        private String chaptersUpdated;
        /**
         * title : 第一章 死在万花丛中
         * link : http://vip.zhuishushenqi.com/chapter/577e5290260289ff64a29213?cv=1467896464908
         * id : 577e5290260289ff64a29213
         * currency : 15
         * unreadble : false
         * isVip : false
         */
        private List<Chapters> chapters;

        public String getMixTocId() {
            return mixTocId;
        }

        public void setMixTocId(String mixTocId) {
            this.mixTocId = mixTocId;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }

        public String getChaptersUpdated() {
            return chaptersUpdated;
        }

        public void setChaptersUpdated(String chaptersUpdated) {
            this.chaptersUpdated = chaptersUpdated;
        }

        public List<Chapters> getChapters() {
            return chapters;
        }

        public void setChapters(List<Chapters> chapters) {
            this.chapters = chapters;
        }

        public static class Chapters implements Serializable, ISelect {
            private String title;
            private String link;
            private String id;
            private int currency;
            private boolean unreadble;
            private boolean isVip;
            private boolean isSelected;

            public Chapters() {
            }

            public Chapters(String title, String link) {
                this.title = title;
                this.link = link;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getCurrency() {
                return currency;
            }

            public void setCurrency(int currency) {
                this.currency = currency;
            }

            public boolean isUnreadble() {
                return unreadble;
            }

            public void setUnreadble(boolean unreadble) {
                this.unreadble = unreadble;
            }

            public boolean isVip() {
                return isVip;
            }

            public void setVip(boolean vip) {
                isVip = vip;
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
}
