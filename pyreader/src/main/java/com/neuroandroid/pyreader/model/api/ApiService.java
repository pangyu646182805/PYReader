package com.neuroandroid.pyreader.model.api;

import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.model.response.BooksByTag;
import com.neuroandroid.pyreader.model.response.CategoryList;
import com.neuroandroid.pyreader.model.response.DiscussionList;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.HotWord;
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
}
