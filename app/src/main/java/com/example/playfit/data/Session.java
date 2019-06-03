/*
created by suerding, sknobla
finalized by
 */
package com.example.playfit.data;

import android.content.Intent;
import android.util.Log;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.dto.UserDTO;

public class Session {
    public Session(){

    }
    private UserDTO loggedInUser = new UserDTO();
    private UserDAOimpl users = new UserDAOimpl();

    public void create(String username){
        loggedInUser = users.list().get(users.getUserIdbyName(username));
        return;
    }

    public void close(){
        loggedInUser = null;
        // hier könnte noch das Intent korrekt beendet werden
    }

    public UserDTO getSession(){
        return loggedInUser;
    }

}
