package com.example.playfit.dao;

import android.util.Log;

import com.example.playfit.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements UserDAO {

    private static List<UserDTO> users = new ArrayList<>();

    public  void createUser(){

        UserDTO user = new UserDTO();
        //Add Basti
        user.setUserID("1");
        user.setUserName("Basti");
        user.setUserPoints(0);
        user.setUserEmail("sknobla2@smail.uni-koeln.de");
        user.setPassword("Basti123");
        users.add(user);

        //Add Simon
        user.setUserID("2");
        user.setUserName("Simon");
        user.setUserPoints(0);
        user.setUserEmail("suerding@smail.uni-koeln.de");
        user.setPassword("Simon123");
        users.add(user);

        //Add Fritz
        user.setUserID("9");
        user.setUserName("Fritz");
        user.setUserPoints(0);
        user.setUserEmail("fritz@smail.uni-koeln.de");
        user.setPassword("Fritz123");
        users.add(user);

    }

    @Override
    public  List<UserDTO> list() {
        return users;
    }

    //gibt die Stelle des Users in der Liste zurück
    @Override
    public  Integer getUserIdbyName(String username) {
        int id = 0;
        for(int i= 0; i<list().size(); i++){
            if(list().get(i).getUserName().equals(username)){
                id =  i;
            }
        }
        return id;
    }
}
