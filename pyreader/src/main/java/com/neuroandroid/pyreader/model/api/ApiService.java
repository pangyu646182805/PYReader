package com.neuroandroid.pyreader.model.api;

import com.neuroandroid.pyreader.model.response.Recommend;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ApiService {
    /**
     * 登录
     */
    /*@GET("login/{param}")
    Observable<BaseResponse<User>> login(@Path("param") String param,
                                         @Query("password") String password,
                                         @Query("userType") int userType,
                                         @Query("visitIp") String ip);*/

    /**
     * 获取推荐列表
     */
    @GET("/book/recommend")
    Observable<Recommend> getRecommend(@Query("gender") String gender);
}
