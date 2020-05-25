package com.barry.demo.color_img_android_project.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("photos")
    Call<List<ColorImgModel>> getPhoto(@Query("_start") String start, @Query("_limit") String size);
}
