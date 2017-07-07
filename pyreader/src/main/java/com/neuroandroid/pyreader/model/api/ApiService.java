package com.neuroandroid.pyreader.model.api;

import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.model.response.BookListTags;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.BooksByCategory;
import com.neuroandroid.pyreader.model.response.BooksByTag;
import com.neuroandroid.pyreader.model.response.CategoryList;
import com.neuroandroid.pyreader.model.response.CategoryListLv2;
import com.neuroandroid.pyreader.model.response.ChapterRead;
import com.neuroandroid.pyreader.model.response.DiscussionList;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.HotWord;
import com.neuroandroid.pyreader.model.response.RankingList;
import com.neuroandroid.pyreader.model.response.Rankings;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.model.response.RecommendBookList;
import com.neuroandroid.pyreader.model.response.SearchBooks;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ApiService {
    /**
     * 获取推荐列表
     */
    @GET("/book/recommend")
    Observable<Recommend> getRecommend(@Query("gender") String gender);

    /**
     * 获取book详情
     */
    @GET("/book/{bookId}")
    Observable<BookDetail> getBookDetail(@Path("bookId") String bookId);

    /**
     * 获取热门评论
     */
    @GET("/post/review/best-by-book")
    Observable<HotReview> getHotReview(@Query("book") String book);

    /**
     * 获取推荐书单
     */
    @GET("/book-list/{bookId}/recommend")
    Observable<RecommendBookList> getRecommendBookList(@Path("bookId") String bookId, @Query("limit") String limit);

    /**
     * 获取热搜书籍
     */
    @GET("/book/hot-word")
    Observable<HotWord> getHotWord();

    /**
     * 书籍查询
     */
    @GET("/book/fuzzy-search")
    Observable<SearchBooks> searchBooks(@Query("query") String query);

    /**
     * 根据tag获取书籍列表
     * 分页获取
     */
    @GET("/book/by-tags")
    Observable<BooksByTag> getBooksByTag(@Query("tags") String tags, @Query("start") String start, @Query("limit") String limit);

    /**
     * 获取书籍详情书评列表
     *
     * @param book bookId
     * @param sort updated(默认排序)
     *             created(最新发布)
     *             helpful(最有用的)
     *             comment-count(最多评论)
     * @return
     */
    @GET("/post/review/by-book")
    Observable<HotReview> getBookDetailReviewList(@Query("book") String book, @Query("sort") String sort,
                                                  @Query("start") String start, @Query("limit") String limit);

    /**
     * 获取书籍详情讨论列表
     *
     * @param book bookId
     * @param sort updated(默认排序)
     *             created(最新发布)
     *             comment-count(最多评论)
     * @param type "normal,vote"
     * @return
     */
    @GET("/post/by-book")
    Observable<DiscussionList> getBookDetailDiscussionList(@Query("book") String book,
                                                           @Query("sort") String sort,
                                                           @Query("type") String type,
                                                           @Query("start") String start,
                                                           @Query("limit") String limit);

    /**
     * 获取书单详情
     */
    @GET("/book-list/{bookListId}")
    Observable<BookListDetail> getBookListDetail(@Path("bookListId") String bookListId);

    /**
     * 获取分类
     */
    @GET("/cats/lv2/statistics")
    Observable<CategoryList> getCategoryList();

    /**
     * 获取二级分类
     *
     * @return
     */
    @GET("/cats/lv2")
    Observable<CategoryListLv2> getCategoryListLv2();

    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    Observable<BooksByCategory> getBooksByCategory(@Query("gender") String gender, @Query("type") String type,
                                                   @Query("major") String major, @Query("minor") String minor,
                                                   @Query("start") int start, @Query("limit") int limit);

    /**
     * 获取主题书单标签列表
     */
    @GET("/book-list/tagType")
    Observable<BookListTags> getBookListTags();

    /**
     * 获取主题书单列表
     * 本周最热：duration=last-seven-days&sort=collectorCount
     * 最新发布：duration=all&sort=created
     * 最多收藏：duration=all&sort=collectorCount
     *
     * @param tag    都市、古代、架空、重生、玄幻、网游
     * @param gender male、female
     * @param limit  20
     * @return
     */
    @GET("/book-list")
    Observable<BookList> getBookList(@Query("duration") String duration, @Query("sort") String sort,
                                     @Query("start") String start, @Query("limit") String limit,
                                     @Query("tag") String tag, @Query("gender") String gender);

    /**
     * 获取所有排行榜
     */
    @GET("/ranking/gender")
    Observable<RankingList> getRanking();

    /**
     * 获取单一排行榜
     * 周榜：rankingId->_id
     * 月榜：rankingId->monthRank
     * 总榜：rankingId->totalRank
     */
    @GET("/ranking/{rankingId}")
    Observable<Rankings> getRanking(@Path("rankingId") String rankingId);

    @GET("/mix-atoc/{bookId}")
    Observable<BookMixAToc> getBookMixAToc(@Path("bookId") String bookId, @Query("view") String view);

    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    Observable<ChapterRead> getChapterRead(@Path("url") String url);
}
