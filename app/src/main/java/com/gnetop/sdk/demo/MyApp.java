package com.gnetop.sdk.demo;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by Administrator on 2020\2\11
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
