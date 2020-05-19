package com.barry.demo.color_img_android_project.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppApplication extends Application {

    private String TAG = this.getClass().getSimpleName();

    private static AppApplication INSTANCE = null;

    public synchronized static AppApplication getINSTANCE()
    {
        return INSTANCE;
    }

    private SharedPreferences prefs;

    private SharedPreferences.Editor prefs_editor;

    @Override
    public void onCreate() {
        super.onCreate();
        // init Singleton INSTANCE
        INSTANCE = this;

        // SP
        prefs = getSharedPreferences("SP_NAME", Context.MODE_PRIVATE);
        prefs_editor = prefs.edit();
    }


    public void setStringData(String key,String string_data)
    {
        prefs_editor.putString(key,string_data);
        prefs_editor.commit();
    }

    public String getStringData(String key)
    {
       return prefs.getString(key,"");
    }
}
