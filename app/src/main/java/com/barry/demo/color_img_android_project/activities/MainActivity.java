package com.barry.demo.color_img_android_project.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.barry.demo.color_img_android_project.R;
import com.barry.demo.color_img_android_project.adapter.PhotoListAdapter;
import com.barry.demo.color_img_android_project.network.ColorImgModel;
import com.barry.demo.color_img_android_project.viewmodel.MainViewModel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends BaseActivity {

    private MainViewModel viewModel;

    private RecyclerView img_color_recyclerview;

    private PhotoListAdapter adapter;

    private SwipeRefreshLayout swipelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    void bindView() {
        img_color_recyclerview = findViewById(R.id.activity_main_color_img_recyclerview);
        swipelayout = findViewById(R.id.swipelayout);
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

        viewModel.getLoadStata_MutableLivedata().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    switch (integer) {
                        case 0:
                            swipelayout.setRefreshing(false);
                            break;
                        case 1:
                            swipelayout.setRefreshing(true);
                            break;
                        case 2:
                            swipelayout.setRefreshing(false);
                            break;
                        case 3:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"api timeout error, tring again",Toast.LENGTH_SHORT).show();
                                }
                            });
                            swipelayout.setRefreshing(false);
                            break;
                        default:
                            swipelayout.setRefreshing(false);
                            break;
                    }
                }
            }
        });
    }
}
