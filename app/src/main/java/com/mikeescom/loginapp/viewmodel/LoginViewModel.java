package com.mikeescom.loginapp.viewmodel;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mikeescom.loginapp.LoginApp;
import com.mikeescom.loginapp.repository.Repository;
import com.mikeescom.loginapp.repository.db.User;

import java.util.regex.Matcher;

public class LoginViewModel extends ViewModel {

    private final Repository repository;
    private final MutableLiveData<Boolean> isLoginDataValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRegisterDataValid = new MutableLiveData<>();

    public LoginViewModel() {
        repository = LoginApp.getAppComponent().buildRepository();
    }

    public LiveData<User> findByUserIdAndPsw(String userId, String psw) {
        return repository.findByUserIdAndPsw(userId, psw);
    }

    public LiveData<Long> addUser(User user) {
        return repository.insert(user);
    }

    public MutableLiveData<Boolean> validateLogin(String userId, String psw) {
        isLoginDataValid.postValue(validateLoginData(userId, psw));
        return isLoginDataValid;
    }

    public MutableLiveData<Boolean> validateRegister(User user) {
        isRegisterDataValid.postValue(validateRegisterData(user));
        return isRegisterDataValid;
    }

    private boolean validateLoginData(String userId, String psw) {
        return !(TextUtils.isEmpty(userId) || TextUtils.isEmpty(psw) || userId.length() < 5 || psw.length() < 5);
    }

    private boolean validateRegisterData(User user) {
        if (user == null) {
            return false;
        }
        return validateFirstName(user.getFirstName()) &&
                validateLastName(user.getLastName()) &&
                validateEmail(user.getEmail()) &&
                validateUserId(user.getUserId()) &&
                validatePassword(user.getPassword());
    }

    private boolean validateFirstName(String firstName) {
        if (TextUtils.isEmpty(firstName)) {
            return false;
        }
        return !(TextUtils.isEmpty(firstName) || firstName.length() > 50);
    }

    private boolean validateLastName(String lastName) {
        if (TextUtils.isEmpty(lastName)) {
            return false;
        }
        return !(TextUtils.isEmpty(lastName) || lastName.length() > 50);
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.find();
    }

    private boolean validateUserId(String userId) {
        if (TextUtils.isEmpty(userId)) {
            return false;
        }
        return !(TextUtils.isEmpty(userId) || userId.length() < 10);
    }

    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return !(TextUtils.isEmpty(password) || password.length() < 12);
    }
}
