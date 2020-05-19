package com.barry.demo.color_img_android_project.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.barry.demo.color_img_android_project.R;
import com.barry.demo.color_img_android_project.adapter.PhotoListAdapter;
import com.barry.demo.color_img_android_project.network.ColorImgModel;
import com.barry.demo.color_img_android_project.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    private MainViewModel viewModel;

    private RecyclerView img_color_recyclerview;

    private PhotoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    void bindView() {
        img_color_recyclerview = findViewById(R.id.activity_main_color_img_recyclerview);
    }

    @Override
    void init() {
        // init viewmodel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new PhotoListAdapter(this);
        Log.e(TAG,"created adapter");

        img_color_recyclerview.setLayoutManager(new GridLayoutManager(this,4));
        img_color_recyclerview.setAdapter(adapter);
        Log.e(TAG,"set adapter");

        // observer data
        viewModel.getUserPagedList().observe(this, new Observer<PagedList<ColorImgModel>>() {
            @Override
            public void onChanged(PagedList<ColorImgModel> colorImgModels) {
                adapter.submitList(colorImgModels);
            }
        });
    }
}
