package com.example.customview.http;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {


    //    @DELETE("删除啊")
//    @HEAD("{a}")
//    @GET("/shouji/query")
    @HTTP(method = "method", path = "i am path", hasBody = true)
    @Headers({"Cache-Control: max-age=640000"})
    Call<String> requestApi(@Query(value = "appkey") @Field("asd") String appkey, @Query("shouji") String phone, @Query("phonenum") int phoneNumber);
}


