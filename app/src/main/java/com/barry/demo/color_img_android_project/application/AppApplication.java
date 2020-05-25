package com.barry.demo.color_img_android_project.application;

import android.app.Application;

public class AppApplication extends Application {

    private static AppApplication INSTANCE = null;

    public synchronized static AppApplication getINSTANCE()
    {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // init Singleton INSTANCE
        INSTANCE = this;
    }


}
