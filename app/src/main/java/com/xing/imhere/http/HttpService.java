package com.xing.imhere.http;

import com.xing.imhere.base.LoginResult;
import com.xing.imhere.base.Message;
import com.xing.imhere.base.RegisterResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xinG on 2018/8/3 0003.
 */
public interface HttpService {
    //信息相关
    @POST("addMessage")
    Call<Object> addMessage(@Body Message msg);

    @GET("getMessages")
    Call<List<Message>> getMessages(@Query("lat")double lat,@Query("lon")double lon);

    @GET("user/{account}/msgs")
    Call<List<Message>> getMyMsgs(@Path("account")String account);

    //登录注册相关
    @GET("login")
    Call<LoginResult> login(@Query("account")String account,@Query("pass")String pass);

    @GET("register/{account}")
    Call<RegisterResult> register(@Path("account")String account);

    @GET("register/ensureCode/{account}")
    Call<RegisterResult> confirmEnsureCode(@Path("account")String account,@Query("ensureCode")String ensureCode);

    @GET("register/resetEnsureCode/{account}")
    Call<RegisterResult> resetEnsureCode(@Path("account")String account);

    @PUT("register/putPass/{account}")
    Call<RegisterResult> resetPass(@Path("account")String account,@Query("pass")String pass);


    //“喜欢”相关
    @GET("user/{account}/like")
    Call<List<Message>> getLikeMsgs(@Path("account")String account);

    @PUT("user/{account}/likeby")
    Call<Void> likeIt(@Path("account")String account,@Query("mid")Integer mid);

    @DELETE("user/{account}/likeby")
    Call<Void> cancelLikeIt(@Path("account")String account,@Query("mid")Integer mid);
}
