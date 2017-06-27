package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class DiscussionList extends BaseResponse {
    private List<PostsBean> posts;

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    public static class PostsBean {
        /**
         * _id : 5951c348e670e6761daefec1
         * author : {"_id":"52f840b982cfcc3a74031693","avatar":"/avatar/56/a9/56a96462a50ca99f9cf83440899e46f3","nickname":"追书首席打杂","activityAvatar":"/activities/20170120/5.jpg","type":"official","lv":10,"gender":"male"}
         * type : vote
         * likeCount : 37
         * block : ramble
         * haveImage : true
         * state : normal
         * updated : 2017-06-27T07:52:22.991Z
         * created : 2017-06-27T02:30:32.619Z
         * commentCount : 281
         * voteCount : 737
         * title : 【社区公告】烦人的50字取消啦！随心所欲的「短评」你喜欢吗？（iOS 2.26.23 和安卓V3.112）
         * book : {"_id":"51d11e782de6405c45000068","cover":"/agent/http://image.cmfu.com/books/2750457/2750457.jpg","title":"大主宰","latelyFollower":null,"retentionRatio":null}
         */
        @SerializedName("_id")
        private String discussionId;
        private AuthorBean author;
        private String type;
        private int likeCount;
        private String block;
        private boolean haveImage;
        private String state;
        private String updated;
        private String created;
        private int commentCount;
        private int voteCount;
        private String title;
        private BookBean book;

        public String getDiscussionId() {
            return discussionId;
        }

        public void setDiscussionId(String discussionId) {
            this.discussionId = discussionId;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public boolean isHaveImage() {
            return haveImage;
        }

        public void setHaveImage(boolean haveImage) {
            this.haveImage = haveImage;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BookBean getBook() {
            return book;
        }

        public void setBook(BookBean book) {
            this.book = book;
        }

        public static class AuthorBean {
            /**
             * _id : 52f840b982cfcc3a74031693
             * avatar : /avatar/56/a9/56a96462a50ca99f9cf83440899e46f3
             * nickname : 追书首席打杂
             * activityAvatar : /activities/20170120/5.jpg
             * type : official
             * lv : 10
             * gender : male
             */
            @SerializedName("_id")
            private String authorId;
            private String avatar;
            private String nickname;
            private String activityAvatar;
            private String type;
            private int lv;
            private String gender;

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

            public String getActivityAvatar() {
                return activityAvatar;
            }

            public void setActivityAvatar(String activityAvatar) {
                this.activityAvatar = activityAvatar;
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

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }
        }

        public static class BookBean {
            /**
             * _id : 51d11e782de6405c45000068
             * cover : /agent/http://image.cmfu.com/books/2750457/2750457.jpg
             * title : 大主宰
             * latelyFollower : null
             * retentionRatio : null
             */
            @SerializedName("_id")
            private String bookId;
            private String cover;
            private String title;
            private Object latelyFollower;
            private Object retentionRatio;

            public String getBookId() {
                return bookId;
            }

            public void setBookId(String bookId) {
                this.bookId = bookId;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getLatelyFollower() {
                return latelyFollower;
            }

            public void setLatelyFollower(Object latelyFollower) {
                this.latelyFollower = latelyFollower;
            }

            public Object getRetentionRatio() {
                return retentionRatio;
            }

            public void setRetentionRatio(Object retentionRatio) {
                this.retentionRatio = retentionRatio;
            }
        }
    }
}
