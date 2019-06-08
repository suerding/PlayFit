/*
created by sknobla
finalized by suerding
 */
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
        user.setUserName("f");
        user.setUserPoints(0);
        user.setUserEmail("fhaedric@smail.uni-koeln.de");
        user.setPassword("f1");
        users.add(user);
        user = new UserDTO();


    }

    @Override
    public List<UserDTO> list() {
        return this.users;
    }

    //created by suerding & sknobla
    @Override
    public Integer getUserIdbyName(String username) {
        this.createUser();
        for (int i = 0; i < list().size(); i++) {
            if (username.equals(list().get(i).getUserName())) {
                return i;
            }

        }
        return 9999;
    }
    public UserDAOimpl newUser(String username, String password, UserDAOimpl userlist){
        UserDTO nUser = new UserDTO();
        nUser.setUserName(username);
        nUser.setPassword(password);
        userlist.list().add(nUser);
        Log.d("newUser", users.get(3).getUserName());
        return userlist;
    }
}
