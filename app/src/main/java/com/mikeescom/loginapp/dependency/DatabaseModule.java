package com.mikeescom.loginapp.dependency;

import android.app.Application;

import androidx.room.Room;

import com.mikeescom.loginapp.repository.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "login-database")
                .allowMainThreadQueries()
                .build();
    }
}
