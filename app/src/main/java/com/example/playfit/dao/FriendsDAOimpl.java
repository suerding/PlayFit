package com.example.playfit.dao;

import android.content.SharedPreferences;

import com.example.playfit.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class FriendsDAOimpl implements FriendsDAO {
    private List<UserDTO> friends = new ArrayList<>();
    private UserDAOimpl users = new UserDAOimpl();
    SharedPreferences session;

    public void createforuser(String userID){
        users.createUser();
        if (userID.equals("1")){
            friends.add(users.list().get(1));
            friends.add(users.list().get(2));
            friends.add(users.list().get(3));
            friends.add(users.list().get(4));
            friends.add(users.list().get(6));
        } else if (userID.equals("2")) {
            friends.add(users.list().get(0));
            friends.add(users.list().get(2));
            friends.add(users.list().get(3));
            friends.add(users.list().get(4));
            friends.add(users.list().get(6));
            friends.add(users.list().get(5));
        } else if (userID.equals("3")){
            friends.add(users.list().get(1));
            friends.add(users.list().get(2));
            friends.add(users.list().get(3));
            friends.add(users.list().get(4));
        } else if (userID.equals("4")){
            friends.add(users.list().get(1));
            friends.add(users.list().get(2));
            friends.add(users.list().get(3));
            friends.add(users.list().get(4));
            friends.add(users.list().get(5));
        } else if (userID.equals("5")){
            friends.add(users.list().get(1));
            friends.add(users.list().get(2));
            friends.add(users.list().get(3));
            friends.add(users.list().get(4));
            friends.add(users.list().get(6));
            friends.add(users.list().get(5));
        }
    }

    @Override
    public List<UserDTO> list() {
        return this.friends;
    }


    @Override
    public UserDTO getFriendbyID(String userID, String sessionUserID) {
        UserDTO user = new UserDTO();
        this.createforuser(sessionUserID);
        for (int i = 0; i < list().size(); i++) {
            if (userID.equals(list().get(i).getUserID())) {
                user = list().get(i);
                return user;
            }

        }
        return null;
    }


    @Override
    public void add(String sessionID, String friendID) {

    }
}
