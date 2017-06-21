package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class HotReview extends BaseResponse {
    /**
     * total : 122
     * reviews : [{"_id":"56e97c5c71a128cb13bf6f64","rating":2,"author":{"_id":"53eef534d03773b253c9ab76","avatar":"/avatar/39/56/3956c8f65638bd7ada9da76561977385","nickname":"~许我三千笔墨","activityAvatar":"","type":"normal","lv":7,"gender":"male"},"helpful":{"no":226,"total":174,"yes":400},"likeCount":49,"state":"normal","updated":"2017-06-21T03:41:18.469Z","created":"2016-03-16T15:31:40.915Z","commentCount":125,"content":"主角一旦真正牛逼的时候，就死女人！你很有想法，但是。。。越往后写越无聊，你要上天啊这是。。。。。。。。。。。。","title":"开头很好，越来越无聊死了，本来好好的现在飞鱼居然也死了"},{"_id":"57e0320aa16ba58458699d73","rating":2,"author":{"_id":"5375deae6f23a3d521000bde","avatar":"/avatar/81/9f/819ff2d16a9685da2168843dfc81a4a8","nickname":"凌","activityAvatar":"/activities/20170120/4.jpg","type":"normal","lv":9,"gender":"male"},"helpful":{"total":78,"yes":107,"no":29},"likeCount":5,"state":"normal","updated":"2017-06-21T03:55:38.232Z","created":"2016-09-19T18:44:26.915Z","commentCount":43,"content":"主角智商堪忧，全靠配角衬托，做事不看后果，外面惹事了，也不防备敌人报复身边的人，把主角写成自私，脑can了，不过胸怀真的好大！我只能佩服的五体投地，女主被看光差点被干，居然不把对方打死打残反而收来做小弟，实在无语。","title":"看不下去"}]
     */
    private int total;
    private List<ReviewsBean> reviews;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ReviewsBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewsBean> reviews) {
        this.reviews = reviews;
    }

    public static class ReviewsBean {
        /**
         * _id : 56e97c5c71a128cb13bf6f64
         * rating : 2
         * author : {"_id":"53eef534d03773b253c9ab76","avatar":"/avatar/39/56/3956c8f65638bd7ada9da76561977385","nickname":"~许我三千笔墨","activityAvatar":"","type":"normal","lv":7,"gender":"male"}
         * helpful : {"no":226,"total":174,"yes":400}
         * likeCount : 49
         * state : normal
         * updated : 2017-06-21T03:41:18.469Z
         * created : 2016-03-16T15:31:40.915Z
         * commentCount : 125
         * content : 主角一旦真正牛逼的时候，就死女人！你很有想法，但是。。。越往后写越无聊，你要上天啊这是。。。。。。。。。。。。
         * title : 开头很好，越来越无聊死了，本来好好的现在飞鱼居然也死了
         */
        @SerializedName("_id")
        private String reviewId;
        private int rating;
        private AuthorBean author;
        private HelpfulBean helpful;
        private int likeCount;
        private String state;
        private String updated;
        private String created;
        private int commentCount;
        private String content;
        private String title;

        public String getReviewId() {
            return reviewId;
        }

        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public HelpfulBean getHelpful() {
            return helpful;
        }

        public void setHelpful(HelpfulBean helpful) {
            this.helpful = helpful;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class AuthorBean {
            /**
             * _id : 53eef534d03773b253c9ab76
             * avatar : /avatar/39/56/3956c8f65638bd7ada9da76561977385
             * nickname : ~许我三千笔墨
             * activityAvatar :
             * type : normal
             * lv : 7
             * gender : male
             */

            private String _id;
            private String avatar;
            private String nickname;
            private String activityAvatar;
            private String type;
            private int lv;
            private String gender;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
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

        public static class HelpfulBean {
            /**
             * no : 226
             * total : 174
             * yes : 400
             */

            private int no;
            private int total;
            private int yes;

            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getYes() {
                return yes;
            }

            public void setYes(int yes) {
                this.yes = yes;
            }
        }
    }
}
