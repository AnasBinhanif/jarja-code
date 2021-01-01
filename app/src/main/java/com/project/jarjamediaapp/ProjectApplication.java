package com.project.jarjamediaapp;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class ProjectApplication extends Application {

    static ProjectApplication instance;

   static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        instance = this;

        //added by akshay
        this.context = getApplicationContext();  // Grab the Context you want.
    }

    public static ProjectApplication getInstance() {
        return instance;
    }


    //added by akshay
    public static Context getAppContext() {
        return ProjectApplication.context;

    }
}


