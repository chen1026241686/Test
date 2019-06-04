package com.example.customview.http;

import android.util.Log;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpUtils {

    private static HttpHelper httpHelper = HttpHelper.mHttpHelper;

    public static void requestApi(String baseUrl) {
        Api api = httpHelper.createService(Api.class, baseUrl);
        api.requestApi("5d8c19ed9817d8c1", "18811790649").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("HttpUtils", "result=" + response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("HttpUtils", "t=" + t.toString());
            }
        });
    }

}
