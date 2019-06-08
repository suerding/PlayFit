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

        //Add Basti - 0
        user.setUserID("1");
        user.setUserName("Basti");
        user.setName("Basti Knoblauch");
        user.setUserPoints(5);
        user.setUserEmail("sknobla2@smail.uni-koeln.de");
        user.setPassword("user123");
        users.add(user);
        user = new UserDTO();


        //Add SimonUE - 1
        user.setUserID("2");
        user.setUserName("SU");
        user.setName("Simon Uerdingen");
        user.setUserPoints(15);
        user.setUserEmail("suerding@smail.uni-koeln.de");
        user.setPassword("Simon123");
        users.add(user);
        user = new UserDTO();


        //Add Fritz - 2
        user.setUserID("3");
        user.setUserName("f");
        user.setName("Fritz Haedric");
        user.setUserPoints(15);
        user.setUserEmail("fhaedric@smail.uni-koeln.de");
        user.setPassword("f1");
        users.add(user);
        user = new UserDTO();

        //Add SimonFM - 3
        user.setUserID("4");
        user.setUserName("s");
        user.setName("Simon FM");
        user.setUserPoints(25);
        user.setUserEmail("simonfm@smail.uni-koeln.de");
        user.setPassword("s1");
        users.add(user);
        user = new UserDTO();

        //Add Imke - 4
        user.setUserID("5");
        user.setUserName("Imke");
        user.setName("Imke");
        user.setUserPoints(20);
        user.setUserEmail("imke@smail.uni-koeln.de");
        user.setPassword("i1");
        users.add(user);
        user = new UserDTO();

        //Add FriendsTest_1 - 5
        user.setUserID("8");
        user.setUserName("Tester_1");
        user.setName("Tester_1");
        user.setUserPoints(44);
        user.setUserEmail("tester1@smail.uni-koeln.de");
        user.setPassword("f1");
        users.add(user);
        user = new UserDTO();

        //Add FriendsTest_2 - 6
        user.setUserID("9");
        user.setUserName("Tester_2");
        user.setName("Tester_2");
        user.setUserPoints(33);
        user.setUserEmail("tester2@smail.uni-koeln.de");
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

    public UserDTO getUserbyID(String userID) {
        UserDTO user = new UserDTO();
        this.createUser();
        for (int i = 0; i < list().size(); i++) {
            if (userID.equals(list().get(i).getUserID())) {
                user = list().get(i);
                return user;
            }

        }
        return null;
    }
}
