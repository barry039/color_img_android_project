package com.barry.demo.color_img_android_project.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.barry.demo.color_img_android_project.network.ColorImgModel;
import com.barry.demo.color_img_android_project.paging.PhotoRepository;

public class MainViewModel extends BaseViewModel {

    private LiveData photoPagedList;

    public LiveData<PagedList<ColorImgModel>> getUserPagedList() {
        return photoPagedList;
    }

    private PhotoRepository photoRepository;

    public MainViewModel() {
        photoRepository = new PhotoRepository();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(100).build();

        photoPagedList = new LivePagedListBuilder(photoRepository, pagedListConfig)
                .build();
    }

}
