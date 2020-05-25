package com.barry.demo.color_img_android_project.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.barry.demo.color_img_android_project.network.APIManager;
import com.barry.demo.color_img_android_project.network.ColorImgModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;


class PhotoDataSource extends PageKeyedDataSource<Long, ColorImgModel> {

    private final int page_per_size = 400;

    private final MutableLiveData<Integer> loadstata_mutablelivedata = new MutableLiveData<>();

    public MutableLiveData<Integer> getLoadstata_mutablelivedata() {
        return loadstata_mutablelivedata;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, ColorImgModel> callback) {
        Log.e("data","call");
        loadstata_mutablelivedata.postValue(1);
        long since = 0;
        APIManager.getINSTANCE()
                .getApiService()
                .getPhoto(String.valueOf(since),String.valueOf(page_per_size))
                .enqueue(new Callback<List<ColorImgModel>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<ColorImgModel>> call, @NotNull retrofit2.Response<List<ColorImgModel>> response) {
                        try {
                            Log.e("size", Objects.requireNonNull(response.body()).size() + "");
                            callback.onResult(response.body(), null, (long) response.body().get(response.body().size() - 1).getId());
                            loadstata_mutablelivedata.postValue(2);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("datasize","error");
                        }
                        loadInitial(params,callback);
                        loadstata_mutablelivedata.postValue(3);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<ColorImgModel>> call, @NotNull Throwable t) {
                        loadInitial(params,callback);
                        loadstata_mutablelivedata.postValue(3);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, ColorImgModel> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull final LoadCallback<Long, ColorImgModel> callback) {
        Log.e("key",String.valueOf(params.key));
        loadstata_mutablelivedata.postValue(1);
        APIManager.getINSTANCE()
                .getApiService()
                .getPhoto(String.valueOf(params.key),String.valueOf(page_per_size))
                .enqueue(new Callback<List<ColorImgModel>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<ColorImgModel>> call, @NotNull retrofit2.Response<List<ColorImgModel>> response) {
                        try {
                            if (Objects.requireNonNull(response.body()).size() == 0) {
                                callback.onResult(response.body(), null);
                                loadstata_mutablelivedata.postValue(2);
                                return;
                            }
                            callback.onResult(response.body(), (long) response.body().get(response.body().size() - 1).getId());
                            loadstata_mutablelivedata.postValue(2);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        loadAfter(params,callback);
                        loadstata_mutablelivedata.postValue(3);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<ColorImgModel>> call, @NotNull Throwable t) {
                        loadAfter(params,callback);
                        loadstata_mutablelivedata.postValue(3);
                    }
                });
    }
}
