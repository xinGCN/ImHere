package com.xing.imhere.http;

import com.xing.imhere.base.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by xinG on 2018/8/3 0003.
 */
public interface HttpService {
    @POST("addMessage")
    Call<Object> addMessage(@Body Message msg);
}
