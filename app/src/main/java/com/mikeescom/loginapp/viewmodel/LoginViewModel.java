package com.mikeescom.loginapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mikeescom.loginapp.LoginApp;
import com.mikeescom.loginapp.repository.Repository;
import com.mikeescom.loginapp.repository.db.User;

public class LoginViewModel extends ViewModel {

    private final Repository repository;

    public LoginViewModel() {
        repository = LoginApp.getAppComponent().buildRepository();
    }

    public LiveData<User> findByUserIdAndPsw(String userId, String psw) {
        return repository.findByUserIdAndPsw(userId, psw);
    }

    public void insert(User user) {
        repository.insert(user);
    }
}
