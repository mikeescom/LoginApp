package com.mikeescom.loginapp;

import android.app.Application;

import com.mikeescom.loginapp.dependency.AppComponent;
import com.mikeescom.loginapp.dependency.AppModule;
import com.mikeescom.loginapp.dependency.DaggerAppComponent;
import com.mikeescom.loginapp.dependency.DatabaseModule;

public class LoginApp extends Application {

    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
