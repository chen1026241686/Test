package com.example.customview.http;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpHelper {


    public static HttpHelper mHttpHelper = new HttpHelper();


    private Map<String, Object> map;

    private HttpHelper() {
        map = new HashMap<>();
    }

    public <T> T createService(Class<T> cla, String baseUrl) {
        if (!map.containsKey(baseUrl)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            map.put(baseUrl, retrofit.create(cla));
        }
        return (T) (map.get(baseUrl));
    }
}
