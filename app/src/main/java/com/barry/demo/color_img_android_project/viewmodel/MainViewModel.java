package com.barry.demo.color_img_android_project.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.barry.demo.color_img_android_project.paging.PhotoRepository;

public class MainViewModel extends BaseViewModel {

    private final LiveData photoPagedList;

    public LiveData getUserPagedList() {
        return photoPagedList;
    }

    private final PhotoRepository photoRepository;

    public MainViewModel() {
        photoRepository = new PhotoRepository();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(200).build();
        //noinspection unchecked
        photoPagedList = new LivePagedListBuilder(photoRepository, pagedListConfig)
                .build();
    }

    public LiveData<Integer> getLoadStata_MutableLivedata() {
        return photoRepository.getLoadstate_mutablelivedata();
    }

}
