package com.barry.demo.color_img_android_project.activities;

import androidx.annotation.NonNull;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.barry.demo.color_img_android_project.R;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start);
        super.onCreate(savedInstanceState);

    }

    @Override
    void bindView() {
        findViewById(R.id.start_btn).setOnClickListener(v -> intenttoMain());
    }

    private void intenttoMain() {
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent mainIntent = new Intent(StartActivity.this,MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        } else {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }

    @Override
    void init() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int STORAGE_REQ_CODE = 1001;
        if (requestCode == STORAGE_REQ_CODE) {
            intenttoMain();
        }
    }
}
