/*
created by suerding, sknobla
finalized by
 */
package com.example.playfit.data;

import android.content.SharedPreferences;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.dto.UserDTO;

public class Session implements Sess{

    private UserDTO loggedInUser = new UserDTO();
    private UserDAOimpl users = new UserDAOimpl();
    SharedPreferences sharedUsers;

    public void create(UserDTO loggedInUser){

        this.loggedInUser = loggedInUser;

        return;
    }

    public void close(){
        loggedInUser = null;
        // hier könnte noch das Intent korrekt beendet werden
    }

    public UserDTO getSession(){
        return this.loggedInUser;
    }

}
