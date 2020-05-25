package com.barry.demo.color_img_android_project.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.barry.demo.color_img_android_project.R;
import com.barry.demo.color_img_android_project.adapter.PhotoListAdapter;
import com.barry.demo.color_img_android_project.network.ColorImgModel;
import com.barry.demo.color_img_android_project.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

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

    @SuppressWarnings("unchecked")
    @Override
    void init() {
        // init viewmodel
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new PhotoListAdapter(this);
        Log.e(TAG,"created adapter");

        img_color_recyclerview.setLayoutManager(new GridLayoutManager(this,4));
        img_color_recyclerview.setAdapter(adapter);
        Log.e(TAG,"set adapter");

        // observer data
        // noinspection unchecked
        viewModel.getUserPagedList().observe(this, (Observer<PagedList<ColorImgModel>>) colorImgModels -> adapter.submitList(colorImgModels));

        viewModel.getLoadStata_MutableLivedata().observe(this, integer -> {
            if (integer != null) {
                switch (integer) {
                    case 1:
                        swipelayout.setRefreshing(true);
                        break;
                    case 3:
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "api timeout error, tring again", Toast.LENGTH_SHORT).show());
                        swipelayout.setRefreshing(false);
                        break;
                    case 0:
                    case 2:
                    default:
                        swipelayout.setRefreshing(false);
                        break;
                }
            }
        });
    }
}
