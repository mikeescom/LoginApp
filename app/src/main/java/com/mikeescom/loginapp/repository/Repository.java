package com.mikeescom.loginapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mikeescom.loginapp.repository.db.AppDatabase;
import com.mikeescom.loginapp.repository.db.User;

import javax.inject.Inject;

public class Repository {
    private final AppDatabase db;

    @Inject
    public Repository(AppDatabase db) {
        this.db = db;
    }

    public LiveData<User> findByUserIdAndPsw(String userId, String psw) {
        return db.userDao().findByUserIdAndPsw(userId, psw);
    }

    public LiveData<User> findByUserId(int id) {
        return db.userDao().findByUserId(id);
    }

    public LiveData<Long> insert(User user) {
        MutableLiveData<Long> liveData = new MutableLiveData<>();
        liveData.postValue(db.userDao().insert(user));
        return liveData;
    }
}
