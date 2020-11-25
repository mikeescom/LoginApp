package com.mikeescom.loginapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
    public static final String LOGIN_PREFERENCES = "LoginPref" ;
    public static final String loggedUserIdKey = "loggedUserIdKey";
    private static SharedPreferenceUtils instance;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    private SharedPreferenceUtils(Context context) {
        sharedpreferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceUtils(context);
            return instance;
        }
        return instance;
    }

    public void setLoggedUserId(int id) {
        editor = sharedpreferences.edit();
        editor.putInt(loggedUserIdKey, id);
        editor.apply();
    }

    public int getLoggedUserId() {
        return sharedpreferences.getInt(loggedUserIdKey, 0);
    }
}
