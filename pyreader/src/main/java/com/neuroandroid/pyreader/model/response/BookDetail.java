package com.neuroandroid.pyreader.model.response;

import com.google.gson.annotations.SerializedName;
import com.neuroandroid.pyreader.base.BaseResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class BookDetail extends BaseResponse {
    /**
     * _id : 5596121792ff8b295003547f
     * title : 隐身侍卫
     * author : 桃子卖没了
     * longIntro : 无意间触电后的张易，发现自已竟然可以隐去身体，变成一个透明人，意外的惊喜让他开启了新的人生。
     冷艳的公司老总，火爆的美女警花，调皮捣蛋的富家千金。抱歉，我是一个正直的人，我从来不会利用我的隐身技能偷窥美女，因为我只会站在美女面前大胆的看！
     你看不见我，你看不见我，你看不见我……
     保镖新群：24173796
     * cover : /cover/149697589159012
     * cat : 异术超能
     * creater : alps Coolpad 8675
     * majorCate : 都市
     * minorCate : 异术超能
     * currency : 0
     * contentType : txt
     * _le : false
     * allowMonthly : false
     * allowVoucher : true
     * allowBeanVoucher : false
     * hasCp : true
     * postCount : 1673
     * latelyFollower : 67358
     * followerCount : 0
     * wordCount : 5743119
     * serializeWordCount : 6987
     * retentionRatio : 52.33
     * updated : 2017-06-21T03:07:05.322Z
     * isSerial : true
     * chaptersCount : 2444
     * lastChapter : 第2444章 血珠九十八
     * gender : ["male"]
     * tags : ["都市","赚钱","热血","无敌文","异术超能","豪门"]
     * donate : false
     * copyright : 阅文集团正版授权
     */
    @SerializedName("_id")
    private String bookId;
    private String title;
    private String author;
    private String longIntro;
    private String cover;
    private String cat;
    private String creater;
    private String majorCate;
    private String minorCate;
    private int currency;
    private String contentType;
    private boolean _le;
    private boolean allowMonthly;
    private boolean allowVoucher;
    private boolean allowBeanVoucher;
    private boolean hasCp;
    private int postCount;
    private int latelyFollower;
    private int followerCount;
    private int wordCount;
    private int serializeWordCount;
    private String retentionRatio;
    private String updated;
    private boolean isSerial;
    private int chaptersCount;
    private String lastChapter;
    private boolean donate;
    private String copyright;
    private List<String> gender;
    private List<String> tags;

    public Recommend.BooksBean generateRecommendBook() {
        Recommend.BooksBean book = new Recommend.BooksBean();
        book.setTitle(getTitle());
        book.setBookId(getBookId());
        book.setUpdated(getUpdated());
        book.setCover(getCover());
        book.setLastChapter(getLastChapter());
        return book;
    }

    public BookDetail() {
        setNoOk(true);
    }

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

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
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

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean is_le() {
        return _le;
    }

    public void set_le(boolean _le) {
        this._le = _le;
    }

    public boolean isAllowMonthly() {
        return allowMonthly;
    }

    public void setAllowMonthly(boolean allowMonthly) {
        this.allowMonthly = allowMonthly;
    }

    public boolean isAllowVoucher() {
        return allowVoucher;
    }

    public void setAllowVoucher(boolean allowVoucher) {
        this.allowVoucher = allowVoucher;
    }

    public boolean isAllowBeanVoucher() {
        return allowBeanVoucher;
    }

    public void setAllowBeanVoucher(boolean allowBeanVoucher) {
        this.allowBeanVoucher = allowBeanVoucher;
    }

    public boolean isHasCp() {
        return hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getSerializeWordCount() {
        return serializeWordCount;
    }

    public void setSerializeWordCount(int serializeWordCount) {
        this.serializeWordCount = serializeWordCount;
    }

    public String getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(String retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isIsSerial() {
        return isSerial;
    }

    public void setIsSerial(boolean isSerial) {
        this.isSerial = isSerial;
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

    public boolean isDonate() {
        return donate;
    }

    public void setDonate(boolean donate) {
        this.donate = donate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
