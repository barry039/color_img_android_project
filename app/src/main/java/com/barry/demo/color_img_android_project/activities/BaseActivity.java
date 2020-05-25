package com.barry.demo.color_img_android_project.activities;


import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public abstract class BaseActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        init();
    }

    /*
        ==================================
        固定init func設置
        ===================================
        */
    abstract void bindView();

    abstract void init();

    /*
    ==================================
    Permission func
    ===================================
    */

    boolean checkPermission(String permission)
    {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    void requestPermission(String[] permissions)
    {
        ActivityCompat.requestPermissions(this, permissions, 1001);
    }

}
