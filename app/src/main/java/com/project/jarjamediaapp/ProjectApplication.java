package com.project.jarjamediaapp;

import android.app.Application;

import androidx.multidex.MultiDex;

public class ProjectApplication extends Application {

    static ProjectApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        instance = this;
    }

    public static ProjectApplication getInstance()
    {
        return instance;
    }

}
