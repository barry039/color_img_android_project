package com.barry.demo.color_img_android_project.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.barry.demo.color_img_android_project.network.ColorImgModel;


public class PhotoRepository extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Long, ColorImgModel>> dataset_mutablelivedata = new MutableLiveData<>();

    public MutableLiveData<PageKeyedDataSource<Long, ColorImgModel>> getDataset_mutablelivedata() {
        return dataset_mutablelivedata;
    }

    @NonNull
    @Override
    public DataSource create() {
        PhotoDataSource photoDataSource = new PhotoDataSource();

        dataset_mutablelivedata.postValue(photoDataSource);

        return photoDataSource;
    }
}
