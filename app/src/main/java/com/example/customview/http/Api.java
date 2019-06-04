package com.example.customview.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/shouji/query")
    Call<String> requestApi(@Query("appkey") String appkey, @Query("shouji") String phone);
}


