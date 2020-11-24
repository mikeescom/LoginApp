package com.mikeescom.loginapp.dependency;

import com.mikeescom.loginapp.repository.Repository;
import com.mikeescom.loginapp.repository.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={DatabaseModule.class, AppModule.class})
public interface AppComponent {
    Repository buildRepository();
}
