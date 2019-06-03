package com.example.playfit.dao;

import android.util.Log;

import com.example.playfit.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements UserDAO {

    private List<UserDTO> users = new ArrayList<>();

    public void createUser() {

        UserDTO user = new UserDTO();

        //Add user
        user.setUserID("1");
        user.setUserName("Basti");
        user.setUserPoints(0);
        user.setUserEmail("sknobla2@smail.uni-koeln.de");
        user.setPassword("user123");
        users.add(user);
        user = new UserDTO();


        //Add Simon
        user.setUserID("2");
        user.setUserName("Simon");
        user.setUserPoints(0);
        user.setUserEmail("suerding@smail.uni-koeln.de");
        user.setPassword("Simon123");
        users.add(user);
        user = new UserDTO();


        //Add Fritz
        user.setUserID("9");
        user.setUserName("Fritz");
        user.setUserPoints(0);
        user.setUserEmail("user@smail.uni-koeln.de");
        user.setPassword("Fritz123");
        users.add(user);
        user = new UserDTO();


    }

    @Override
    public List<UserDTO> list() {
        return this.users;
    }

    //gibt die Stelle des Users in der Liste zurück
    @Override
    public Integer getUserIdbyName(String username) {
        this.createUser();
        Log.d("ifCheck", this.list().get(0).getUserName());
        Log.d("ifCheck", this.list().get(1).getUserName());
        Log.d("ifCheck", this.list().get(2).getUserName());
        for (int i = 0; i < list().size(); i++) {
            if (username.equals(list().get(i).getUserName())) {
                return i;
            }

        }
        return 9999;
    }
    public void newUser(String username, String password){
        UserDTO nUser = new UserDTO();
        nUser.setUserName(username);
        nUser.setPassword(password);
    }
}
