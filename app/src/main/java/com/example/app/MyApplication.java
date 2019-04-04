package com.example.app;

import android.app.Application;

import org.litepal.LitePal;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(getApplicationContext());
        LitePal.getDatabase();
        Bmob.initialize(this, "a38ddcce718d351410cc4a448a42a21e");
    }
}
