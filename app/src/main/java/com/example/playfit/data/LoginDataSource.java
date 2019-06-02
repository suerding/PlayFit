package com.example.playfit.data;

import android.util.Log;

import com.example.playfit.dao.UserDAO;
import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.model.LoggedInUser;
import com.example.playfit.dto.UserDTO;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 * hier muessen wir auch unsere simulierte Database einpfelgen!!!!
 */
public class LoginDataSource {
    private UserDAOimpl user = new UserDAOimpl();

    public Result<LoggedInUser> login(String username, String password) {

        Integer id = user.getUserIdbyName("Simon");

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser aktiveUser =
                    new LoggedInUser( user.list().get(id).getUserID(), username);
            return new Result.Success<>(aktiveUser);
        } catch (Exception e) {
            return null;
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
