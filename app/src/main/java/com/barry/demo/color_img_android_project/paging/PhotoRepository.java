package com.barry.demo.color_img_android_project.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.barry.demo.color_img_android_project.network.ColorImgModel;


public class PhotoRepository extends DataSource.Factory {
    private final PhotoDataSource photoDataSource;
    public PhotoRepository() {
        photoDataSource = new PhotoDataSource();
        MutableLiveData<PageKeyedDataSource<Long, ColorImgModel>> dataset_mutablelivedata = new MutableLiveData<>();
        dataset_mutablelivedata.postValue(photoDataSource);
        loadstate_mutablelivedata = photoDataSource.getLoadstata_mutablelivedata();
    }

    private final LiveData<Integer> loadstate_mutablelivedata;

    public LiveData<Integer> getLoadstate_mutablelivedata() {
        return loadstate_mutablelivedata;
    }

    @NonNull
    @Override
    public DataSource create() {
        return photoDataSource;
    }
}
