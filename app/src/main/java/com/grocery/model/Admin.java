package com.grocery.model;

import android.content.Context;

import com.grocery.utils.PreferencesUtils;
import com.grocery.utils.SharedPrefKeys;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class Admin {
    private String email;
    private String password;
    private String token_firebase;

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken_firebase() {
        return token_firebase;
    }

    public void setToken_firebase(String token_firebase) {
        this.token_firebase = token_firebase;
    }

    public static void setCurrentAdmin(Context context, Admin admin) {
        PreferencesUtils.putString(context, SharedPrefKeys.ADMIN_EMAIL_KEY, admin.getEmail());
        PreferencesUtils.getString(context, SharedPrefKeys.ADMIN_PASS_KEY, admin.getPassword());
        PreferencesUtils.getString(context, SharedPrefKeys.ADMIN_PHONE_KEY, admin.getToken_firebase());
    }

    public static void clearAdmin(Context context) {
        setCurrentAdmin(context, new Admin());
    }
}
