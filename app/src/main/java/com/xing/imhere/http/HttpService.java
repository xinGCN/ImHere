package com.xing.imhere.http;

import com.xing.imhere.base.LoginResult;
import com.xing.imhere.base.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xinG on 2018/8/3 0003.
 */
public interface HttpService {
    @POST("addMessage")
    Call<Object> addMessage(@Body Message msg);

    @GET("getMessages")
    Call<List<Message>> getMessages(@Query("lat")double lat,@Query("lon")double lon);

    @GET("login")
    Call<LoginResult> login(@Query("account")String account,@Query("pass")String pass);
}
