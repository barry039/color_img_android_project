package com.barry.demo.color_img_android_project.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    private String TAG = this.getClass().getSimpleName();

    private EndPoint endPoint;

    private static APIManager INSTANCE = null;

    private Retrofit retrofit;

    private OkHttpClient okHttpClient;

    private APIService apiService;

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
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
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
