package com.example.playfit.ui.login;

import android.support.annotation.Nullable;

import com.example.playfit.data.LoginDataSource;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;
    private LoginDataSource dataSource = new LoginDataSource();

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess(String username, String password) {
        if(dataSource.login(username,password) == null) {
            return null;
        } else {
            return success;
        }

    }

    @Nullable
    Integer getError() {
        return error;
    }
}
