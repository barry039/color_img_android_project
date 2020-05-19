package com.barry.demo.color_img_android_project.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    protected String TAG = this.getClass().getSimpleName();

    public BaseViewModel() {
    }
}
