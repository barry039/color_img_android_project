package com.barry.demo.color_img_android_project.network;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("photos")
    Call<List<ColorImgModel>> getPhoto(@Query("_start") String start, @Query("_limit") String size);
}
