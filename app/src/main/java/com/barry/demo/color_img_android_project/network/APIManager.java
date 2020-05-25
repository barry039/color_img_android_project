package com.barry.demo.color_img_android_project.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    private final EndPoint endPoint;

    private static APIManager INSTANCE = null;

    private Retrofit retrofit;

    private OkHttpClient okHttpClient;

    private final APIService apiService;

    // singleton get func
    public synchronized static APIManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new APIManager();
        }
        return INSTANCE;
    }

    // private constructor
    private APIManager() {
        // init endpoint
        endPoint = new EndPoint();
        // init okhttp client
        initOkhttp();
        // init retrofit
        initRetrofit();
        // create APIService
        apiService = retrofit.create(APIService.class);
    }

    // create okhttp client
    private void initOkhttp() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    // create retrofit
    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(endPoint.getBaseurl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public APIService getApiService(){
        return apiService;
    }
}
