package com.mikeescom.loginapp.repository;

import androidx.lifecycle.LiveData;

import com.mikeescom.loginapp.LoginApp;
import com.mikeescom.loginapp.repository.db.AppDatabase;
import com.mikeescom.loginapp.repository.db.User;

import javax.inject.Inject;

public class Repository {
    private AppDatabase db;

    @Inject
    public Repository(AppDatabase db) {
        this.db = db;
    }

    public LiveData<User> findByUserIdAndPsw(String userId, String psw) {
        return db.userDao().findByUserIdAndPsw(userId, psw);
    }

    public void insert(User user) {
        db.userDao().insert(user);
    }
}
