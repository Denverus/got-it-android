package com.jzson.gotit.client;

import android.app.Application;

public class AppApplication extends Application {

    private static AppApplication context;

    @Override
    public void onCreate() {
        super.onCreate();

        AppApplication.context = this;
    }

    public static AppApplication getContext() {
        return (AppApplication) AppApplication.context;
    }
}

