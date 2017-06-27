package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookListDetail extends BaseResponse {
    private BookListBean bookList;

    public BookListBean getBookList() {
        return bookList;
    }

    public void setBookList(BookListBean bookList) {
        this.bookList = bookList;
    }

    public static class BookListBean {
        @SerializedName("_id")
        private String bookListId;
        private String updated;
        private String title;
        /**
         * _id : 576680476fc4bac56b03e67c
         * avatar : /avatar/b0/df/b0df89144226ec91bf3a68bba32431cd
         * nickname : 书荒终结者
         * type : normal
         * lv : 6
         */
        private AuthorBean author;
        private String desc;
        private String gender;
        private String created;
        private Object stickStopTime;
        private boolean isDraft;
        private Object isDistillate;
        private int collectorCount;
        private String shareLink;
        private String id;
        private List<String> tags;
        /**
         * book : {"_id":"57a7ec3c8083239c141b5207","title":"强势夺爱：顾先生请自重","author":"金佑木木",
         * "longIntro":"\"秦阮阮\u201c分手大师\u201d的名号打得响，却一脚踩在了\u201c天王老子\u201d顾临泫的脚上。\r\n\r\n
         * 一毛钱没赚到不说，还被逼倒欠三百万！\r\n\r\n没办法，为了还债，她不得不以身犯险，这险套路太深，她防不胜防。\r\n\r\n最后逼得日日夜夜被压着\u201c
         * 还债\u201d，赔了夫人又折兵。\r\n\r\n
         * 顾临泫最擅长的事就是翻手为云覆手为雨，以前是用在商场上，后来是用在秦阮阮身上，秦阮阮是他的云，是他的雨，把她扔床上翻来覆去，不知疲惫\u2026\u2026\"\r\n",
         * "cover":"/cover/147132509933927","site":"zhuishuvip","banned":0,"latelyFollower":4392,
         * "latelyFollowerBase":0,"wordCount":146559,"minRetentionRatio":0,"retentionRatio":27.35}
         * comment : 秦阮阮“分手大师”的名号打得响，却一脚踩在了“天王老子”顾临泫的脚上。 一毛钱没赚到不说，还被逼倒欠三百万！
         * 没办法，为了还债，她不得不以身犯险，这险套路太深，她防不胜防。 最后逼得日日夜夜被压着“还债”，赔了夫人又折兵。
         * 顾临泫最擅长的事就是翻手为云覆手为雨，以前是用在商场上，后来是用在秦阮阮身上，秦阮阮是他的云，是他的雨，把她扔床上翻来覆去，不知疲惫……
         */
        private List<BooksBean> books;

        public String getBookListId() {
            return bookListId;
        }

        public void setBookListId(String bookListId) {
            this.bookListId = bookListId;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
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

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public Object getStickStopTime() {
            return stickStopTime;
        }

        public void setStickStopTime(Object stickStopTime) {
            this.stickStopTime = stickStopTime;
        }

        public boolean isIsDraft() {
            return isDraft;
        }

        public void setIsDraft(boolean isDraft) {
            this.isDraft = isDraft;
        }

        public Object getIsDistillate() {
            return isDistillate;
        }

        public void setIsDistillate(Object isDistillate) {
            this.isDistillate = isDistillate;
        }

        public int getCollectorCount() {
            return collectorCount;
        }

        public void setCollectorCount(int collectorCount) {
            this.collectorCount = collectorCount;
        }

        public String getShareLink() {
            return shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<BooksBean> getBooks() {
            return books;
        }

        public void setBooks(List<BooksBean> books) {
            this.books = books;
        }

        public static class AuthorBean {
            @SerializedName("_id")
            private String authorId;
            private String avatar;
            private String nickname;
            private String type;
            private int lv;

            public String getAuthorId() {
                return authorId;
            }

            public void setAuthorId(String authorId) {
                this.authorId = authorId;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getLv() {
                return lv;
            }

            public void setLv(int lv) {
                this.lv = lv;
            }
        }

        public static class BooksBean {
            /**
             * _id : 57a7ec3c8083239c141b5207
             * title : 强势夺爱：顾先生请自重
             * author : 金佑木木
             * longIntro : "秦阮阮“分手大师”的名号打得响，却一脚踩在了“天王老子”顾临泫的脚上。
             * <p>
             * 一毛钱没赚到不说，还被逼倒欠三百万！
             * <p>
             * 没办法，为了还债，她不得不以身犯险，这险套路太深，她防不胜防。
             * <p>
             * 最后逼得日日夜夜被压着“还债”，赔了夫人又折兵。
             * <p>
             * 顾临泫最擅长的事就是翻手为云覆手为雨，以前是用在商场上，后来是用在秦阮阮身上，秦阮阮是他的云，是他的雨，把她扔床上翻来覆去，不知疲惫……"
             * <p>
             * cover : /cover/147132509933927
             * site : zhuishuvip
             * banned : 0
             * latelyFollower : 4392
             * latelyFollowerBase : 0
             * wordCount : 146559
             * minRetentionRatio : 0
             * retentionRatio : 27.35
             */
            private BookBean book;
            private String comment;

            public BookBean getBook() {
                return book;
            }

            public void setBook(BookBean book) {
                this.book = book;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public static class BookBean {
                @SerializedName("_id")
                private String bookId;
                private String title;
                private String author;
                private String longIntro;
                private String cover;
                private String site;
                private int banned;
                private int latelyFollower;
                private int latelyFollowerBase;
                private int wordCount;
                private String minRetentionRatio;
                private double retentionRatio;

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

                public String getLongIntro() {
                    return longIntro;
                }

                public void setLongIntro(String longIntro) {
                    this.longIntro = longIntro;
                }

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getSite() {
                    return site;
                }

                public void setSite(String site) {
                    this.site = site;
                }

                public int getBanned() {
                    return banned;
                }

                public void setBanned(int banned) {
                    this.banned = banned;
                }

                public int getLatelyFollower() {
                    return latelyFollower;
                }

                public void setLatelyFollower(int latelyFollower) {
                    this.latelyFollower = latelyFollower;
                }

                public int getLatelyFollowerBase() {
                    return latelyFollowerBase;
                }

                public void setLatelyFollowerBase(int latelyFollowerBase) {
                    this.latelyFollowerBase = latelyFollowerBase;
                }

                public int getWordCount() {
                    return wordCount;
                }

                public void setWordCount(int wordCount) {
                    this.wordCount = wordCount;
                }

                public String getMinRetentionRatio() {
                    return minRetentionRatio;
                }

                public void setMinRetentionRatio(String minRetentionRatio) {
                    this.minRetentionRatio = minRetentionRatio;
                }

                public double getRetentionRatio() {
                    return retentionRatio;
                }

                public void setRetenStringtio(double retentionRatio) {
                    this.retentionRatio = retentionRatio;
                }
            }
        }
    }
}
