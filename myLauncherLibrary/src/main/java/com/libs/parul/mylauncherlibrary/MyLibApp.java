package com.libs.parul.mylauncherlibrary;

import android.app.Application;
import android.content.Context;

public class MyLibApp extends Application {

    private static MyLibApp instance;


    public MyLibApp() {
        instance = this;
    }


    public static Context getContext() {
        return instance;
    }
}
