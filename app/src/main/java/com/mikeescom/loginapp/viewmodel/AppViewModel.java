package com.mikeescom.loginapp.viewmodel;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mikeescom.loginapp.LoginApp;
import com.mikeescom.loginapp.repository.Repository;
import com.mikeescom.loginapp.repository.db.User;

import java.util.regex.Matcher;

public class AppViewModel extends ViewModel {

    private final Repository repository;
    public final MutableLiveData<Boolean> isLoginDataValid = new MediatorLiveData<>();
    public final MutableLiveData<Boolean> isLoginUserIdValid = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoginPasswordValid = new MutableLiveData<>();

    public final MutableLiveData<Boolean> isRegisterDataValid = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRegisterFirstNameValid = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRegisterLastNameValid = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRegisterEmailValid = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRegisterUserIdValid = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isRegisterPasswordValid = new MutableLiveData<>();

    public AppViewModel() {
        repository = LoginApp.getAppComponent().buildRepository();
    }

    public LiveData<User> findByUserIdAndPsw(String userId, String psw) {
        return repository.findByUserIdAndPsw(userId, psw);
    }

    public LiveData<User> findByUserId(int id) {
        return repository.findByUserId(id);
    }

    public LiveData<Long> addUser(User user) {
        return repository.insert(user);
    }

    public void validateLogin(String userId, String psw) {
        isLoginUserIdValid.postValue(validateUserId(userId));
        isLoginPasswordValid.postValue(validatePassword(psw));
        isLoginDataValid.postValue(validateUserId(userId)
                && validatePassword(psw));
    }

    public void validateRegister(User user) {
        if (user == null) {
            isRegisterDataValid.postValue(false);
        }

        isRegisterFirstNameValid.postValue(validateFirstName(user.getFirstName()));
        isRegisterLastNameValid.postValue(validateLastName(user.getLastName()));
        isRegisterEmailValid.postValue(validateEmail(user.getEmail()));
        isRegisterUserIdValid.postValue(validateUserId(user.getUserId()));
        isRegisterPasswordValid.postValue(validatePassword(user.getPassword()));

        isRegisterDataValid.postValue(validateFirstName(user.getFirstName())
                && validateLastName(user.getLastName())
                && validateEmail(user.getEmail())
                && validateUserId(user.getUserId())
                && validatePassword(user.getPassword()));
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
