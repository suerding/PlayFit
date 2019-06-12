package com.example.playfit.dao;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.playfit.dto.FriendsDTO;
import com.example.playfit.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class FriendsDAOimpl implements FriendsDAO {
    private List<UserDTO> friends = new ArrayList<>();
    private UserDAOimpl users = new UserDAOimpl();
    SharedPreferences session;

    public void createforuser(String userID, UserDAOimpl users){

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
    public UserDTO getFriendbyID(String userID, String sessionUserID, UserDAOimpl users) {
        UserDTO user = new UserDTO();
        this.createforuser(sessionUserID, users);
        for (int i = 0; i < list().size(); i++) {
            if (userID.equals(list().get(i).getUserID())) {
                user = list().get(i);
                return user;
            }

        }
        return null;
    }

    public void readFriendsXML(String[] xmlFriends)
    {
        FriendsDTO friends = new FriendsDTO();
        friends.setUserID(xmlFriends[0]);
        friends.setFriendsID(xmlFriends[1]);

    }

    public UserDAOimpl getForUser(UserDTO user){
        UserDAOimpl users = new UserDAOimpl();
        for (int i =0; i<list().size(); i++){
                if(user.equals(friends.get(i))){
                    users.add(user);
            }

        }
        return users;
    }


    @Override
    public void add(String sessionID, String friendID) {

    }
}
